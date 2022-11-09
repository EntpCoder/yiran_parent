package com.yiran.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yiran.client.cart.CartClient;
import com.yiran.client.cart.CouponClient;
import com.yiran.common.result.R;
import com.yiran.model.entity.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.model.vo.AlipayVo;
import com.yiran.model.vo.CartVO;
import com.yiran.model.vo.OrderDetailsVo;
import com.yiran.model.vo.OrdersVO;
import com.yiran.order.mapper.*;
import com.yiran.order.service.IOrdersService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    private final OrdersMapper ordersMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final FlowMapper flowMapper;
    private final InventoryMapper inventoryMapper;
    private final CartClient cartClient;
    private final CouponClient couponClient;
    private final RabbitTemplate rabbitTemplate;

    public OrdersServiceImpl(OrdersMapper ordersMapper,
                             OrderDetailsMapper orderDetailsMapper,
                             FlowMapper flowMapper,
                             CartClient cartClient,
                             CouponClient couponClient,
                             InventoryMapper inventoryMapper,
                             RabbitTemplate rabbitTemplate) {
        this.ordersMapper = ordersMapper;
        this.orderDetailsMapper = orderDetailsMapper;
        this.flowMapper = flowMapper;
        this.inventoryMapper = inventoryMapper;
        this.cartClient = cartClient;
        this.couponClient = couponClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 生成订单id 需要参数
     *
     * @param userId          用户id
     * @param receiveId       地址id
     * @param receiveCouponId 优惠券id
     * @param cartIds         购物车集合
     * @return 订单
     */
    @Override
    public String createOrder(String userId, String receiveId, String receiveCouponId, String[] cartIds) {
        // 远程调用查询优惠金额
        BigDecimal discountAmount = new BigDecimal(0);
        if (receiveCouponId != null) {
            R<BigDecimal> discountAmountResult = couponClient.getDiscountAmount(receiveCouponId);
            discountAmount = discountAmountResult.getData().get("discountAmount");
        }
        //先使用远程调用查到购物车里面的数据
        R<List<CartVO>> listR = cartClient.userCart(userId, cartIds);
        List<CartVO> cartList = listR.getData().get("cartList");
        BigDecimal sum = BigDecimal.valueOf(0);
        Integer proNum = 0;
        //迭代遍历金额
        for (CartVO c : cartList
        ) {
            System.out.println(c.getSellingPrice() + "*" + c.getNums());
            BigDecimal a1 = new BigDecimal(String.valueOf(c.getSellingPrice()));
            BigDecimal a2 = new BigDecimal(String.valueOf(c.getNums()));
            sum = sum.add(a1.multiply(a2));
            proNum += c.getNums();
        }
        cartList.forEach(System.out::println);
        //后使用查到的数据来实现订单的生成
        //创建一个新的对象
        Orders orders = new Orders();
        //自动生成的uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        orders.setNumbers(uuid);
        //前端传来的地址收件地址id----查
        orders.setReceiveId(receiveId);
        //前端传来的用户id--查
        orders.setUsersId(userId);
        //订单标题---自己拼接
        orders.setSubject("依然服装");
        //前端传来的优惠券id---查
        orders.setReceiveCouponId(receiveCouponId);
        //优惠金额
        orders.setDiscountAmount(discountAmount);
        //订单金额算出来的---算
        orders.setOrderAmount(sum.subtract(discountAmount));
        //订单状态--未支付
        orders.setOrderState(new Byte("0"));
        //订单时间
        orders.setPlaceTime(LocalDateTime.now());
        //商品的数量
        orders.setProNum(proNum);
        ordersMapper.insert(orders);
        //在生成订单的同时生成订单详情
        for (CartVO c : cartList) {
            // 生成订单详情
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(orders.getOrderId());
            orderDetails.setProPrice(c.getSellingPrice());
            orderDetails.setProNum(c.getNums());
            BeanUtils.copyProperties(c, orderDetails);
            orderDetailsMapper.insert(orderDetails);
            // 扣减库存
            inventoryMapper.deductInventory(c.getProAttributeInfoId(), c.getNums());
        }
        //远程调用生成订单删除购物车
        cartClient.deleteAddCart(cartIds);
        // 发送延时消息
        rabbitTemplate.convertAndSend("yiran.delayed.exchange",
                "order.create",orders.getOrderId(),message -> {
            message.getMessageProperties().setDelay(2*60*1000);
            return message;
        });
        return orders.getOrderId();
    }

    @Override
    public Orders queryOrder(String orderId) {
        return ordersMapper.selectById(orderId);
    }

    @Override
    public Orders getOrderAndDetails(String orderId) {
        // 查询订单和订单详情
        Orders orders = ordersMapper.selectById(orderId);
        QueryWrapper<OrderDetails> orderDetailsQueryWrapper = new QueryWrapper<>();
        orderDetailsQueryWrapper.eq("order_id", orderId);
        List<OrderDetails> orderDetails = orderDetailsMapper.selectList(orderDetailsQueryWrapper);
        orders.setOrderDetails(orderDetails);
        return orders;
    }
    /**
     * 修改订单状态
     * 修改库存
     * @param alipayVo 支付宝返回的信息
     */
    @RabbitListener(queues = "#{orderPayStatusQueue.name}")
    public void orderPayStatus(AlipayVo alipayVo) {
        // 修改订单状态
        Orders orders = new Orders();
        orders.setOrderId(alipayVo.getOut_trade_no());
        orders.setOrderState(new Byte("1"));
        ordersMapper.updateById(orders);
    }

    /**
     * 创建流水
     *
     * @param alipayVo 支付宝返回的信息
     */
    @RabbitListener(queues = "#{flowCreateQueue.name}")
    public void flowCreate(AlipayVo alipayVo) {
        System.out.println("收到消息：创建流水---" + alipayVo);
        Flow flow = new Flow();
        flow.setFlowNum(alipayVo.getTrade_no());
        flow.setOrderId(alipayVo.getOut_trade_no());
        flow.setPaidAmount(new BigDecimal(alipayVo.getTotal_amount()));
        flow.setPaidTime(LocalDateTime.now());
        flowMapper.insert(flow);
    }

    /**
     *
     * @param orderId 订单id
     *
     */
    @RabbitListener(queues = "#{delayedOrderQueue.name}")
    public void delayedOrder(String orderId) {
        System.out.println("订单:" + orderId);
        Orders orders = ordersMapper.selectById(orderId);
        if(orders.getOrderState() == 0){
            // 修改订单状态
            System.out.println("未支付-订单超时:"+orderId);
            orders.setOrderState(new Byte("2"));
            ordersMapper.updateById(orders);
            // 恢复库存
            QueryWrapper<OrderDetails> orderDetailsQueryWrapper = new QueryWrapper<>();
            orderDetailsQueryWrapper.eq("order_id",orderId);
            List<OrderDetails> details = orderDetailsMapper.selectList(orderDetailsQueryWrapper);
            for (OrderDetails od:
                 details) {
                inventoryMapper.restoreInventory(od.getProAttributeInfoId(),od.getProNum());
            }
        }
    }

    /**
     * 根据用户id来查询用户的全部订单
     * @param usersId 用户id
     * @return 订单集合
     */
    @Override
    public List<OrdersVO> getAllOrders(String usersId){
        //查询订单
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("users_id",usersId);
        ordersQueryWrapper.last("order by place_time desc");
        List<Orders> orders = ordersMapper.selectList(ordersQueryWrapper);
        return getPackagingList(orders);
    }
    /**
     * 根据用户id以及状态编码来查询用户状态订单
     * @param usersId 用户id
     * @param orderState 状态编码-订单状态：0未支付 1已支付 2已取消  3待收货 4已完成
     * @return 状态集合
     */
    @Override
    public List<OrdersVO> getOrdersByStatus(String usersId, Byte orderState){
        QueryWrapper<Orders> ordersQueryWrapper =new QueryWrapper<>();
        ordersQueryWrapper.eq("users_id",usersId);
        ordersQueryWrapper.eq("order_state",orderState);
        ordersQueryWrapper.last("order by place_time desc");
        List<Orders> orders = ordersMapper.selectList(ordersQueryWrapper);
        return getPackagingList(orders);
    }

    /**
     * 根据订单id来查询一条订单的详情
     * @param orderId 订单id
     * @return 一条订单的集合
     */
    @Override
    public OrdersVO getOrderDetailsByOrderId(String orderId){
        QueryWrapper<Orders> ordersQueryWrapper =new QueryWrapper<>();
        ordersQueryWrapper.eq("order_id",orderId);
        ordersQueryWrapper.last("order by place_time desc");
        Orders order = ordersMapper.selectById(orderId);
        ArrayList<Orders> orders = new ArrayList<>();
        orders.add(order);
        return getPackagingList(orders).get(0);
    }

    /**
     *根据订单id改收获状态
     * @param orderId 订单id
     * @return Boolean
     */
    @Override
    public Boolean updateOrderStatusByOrderId(String orderId){
        //先根据id查出来
        Orders orders = ordersMapper.selectById(orderId);
        orders.setOrderState((byte) 4);
        ordersMapper.updateById(orders);
        return false;
    }
    /**
     * 封装复用方法
     * @param orders 输入orders类型的参数
     * @return 返回一个VO集合
     */
    @Override
    public List<OrdersVO> getPackagingList(List<Orders> orders){
        //创建一个空集合
        List<OrdersVO> ordersList = new ArrayList<>();
        for (Orders o:
                orders) {
            OrdersVO vo = new OrdersVO();
            BeanUtils.copyProperties(o,vo);
            QueryWrapper<OrderDetails> orderDetailsQueryWrapper = new QueryWrapper<>();
            orderDetailsQueryWrapper.eq("order_id",o.getOrderId());
            List<OrderDetails> orderDetails = orderDetailsMapper.selectList(orderDetailsQueryWrapper);
            List<OrderDetailsVo> orderDetailsVoList = new ArrayList<>();
            for(OrderDetails od:orderDetails){
                OrderDetailsVo detailsVo = new OrderDetailsVo();
                BeanUtils.copyProperties(od,detailsVo);
                orderDetailsVoList.add(detailsVo);
            }
            vo.setOrderDetails(orderDetailsVoList);
            ordersList.add(vo);
        }
      return ordersList;
    }

    @Override
    public Boolean updateOrderStatusByOrderNumWith3(String orderNum) {
        UpdateWrapper<Orders> ordersUpdateWrapper = new UpdateWrapper<>();
        ordersUpdateWrapper.eq("numbers",orderNum);
        Orders orders = new Orders();
        orders.setOrderState(new Byte("3"));
        return ordersMapper.update(orders,ordersUpdateWrapper) > 0;
    }

}