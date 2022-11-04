package com.yiran.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.client.cart.CartClient;
import com.yiran.client.cart.CouponClient;
import com.yiran.common.result.R;
import com.yiran.model.entity.OrderDetails;
import com.yiran.model.entity.Orders;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.model.vo.CartVO;
import com.yiran.order.mapper.OrderDetailsMapper;
import com.yiran.order.mapper.OrdersMapper;
import com.yiran.order.service.IOrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    private final OrdersMapper ordersMapper;
    private final CartClient cartClient;
    private final CouponClient couponClient;
    private final OrderDetailsMapper orderDetailsMapper;
    public OrdersServiceImpl(OrdersMapper ordersMapper, CartClient cartClient, CouponClient couponClient, OrderDetailsMapper orderDetailsMapper) {
        this.ordersMapper = ordersMapper;
        this.cartClient = cartClient;
        this.couponClient = couponClient;
        this.orderDetailsMapper = orderDetailsMapper;
    }

    /**
     * 生成订单id 需要参数
     * @param userId 用户id
     * @param receiveId 地址id
     * @param receiveCouponId 优惠券id
     * @param cartIds 购物车集合
     * @return 订单
     */
    @Override
    public String createOrder(String userId, String receiveId, String receiveCouponId, String[] cartIds) {
        // 远程调用查询优惠金额
        BigDecimal discountAmount = new BigDecimal(0);
        if(receiveCouponId != null){
            R<BigDecimal> discountAmountResult = couponClient.getDiscountAmount(receiveCouponId);
            discountAmount = discountAmountResult.getData().get("discountAmount");
        }
        //先使用远程调用查到购物车里面的数据
        R<List<CartVO>> listR = cartClient.userCart(userId, cartIds);
        List<CartVO> cartList = listR.getData().get("cartList");
        BigDecimal sum = BigDecimal.valueOf(0);
        Integer proNum = 0;
        //迭代遍历金额
        for (CartVO c:cartList
             ) {
            System.out.println(c.getSellingPrice()+"*"+c.getNums());
            BigDecimal a1 = new BigDecimal(String.valueOf(c.getSellingPrice()));
            BigDecimal a2 = new BigDecimal(String.valueOf(c.getNums()));
            sum=sum.add(a1.multiply(a2));
            proNum += c.getNums();
        }
        cartList.forEach(System.out::println);
        //后使用查到的数据来实现订单的生成
        //创建一个新的对象
        Orders orders = new Orders();
        //自动生成的uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
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
        //在生成订单的同时生成商品详情
        for(CartVO c:cartList){
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(orders.getOrderId());
            orderDetails.setProPrice(c.getSellingPrice());
            orderDetails.setProNum(c.getNums());
            BeanUtils.copyProperties(c,orderDetails);
            orderDetailsMapper.insert(orderDetails);
        }
        //远程调用生成订单删除购物车
        cartClient.deleteAddCart(cartIds);
        return orders.getOrderId();
    }
    @Override
    public Orders queryOrder(String orderId){
        return ordersMapper.selectById(orderId);
    }

    @Override
    public Orders getOrderAndDetails(String orderId) {
        // 查询订单和订单详情
        Orders orders = ordersMapper.selectById(orderId);
        QueryWrapper<OrderDetails> orderDetailsQueryWrapper = new QueryWrapper<>();
        orderDetailsQueryWrapper.eq("order_id",orderId);
        List<OrderDetails> orderDetails = orderDetailsMapper.selectList(orderDetailsQueryWrapper);
        orders.setOrderDetails(orderDetails);
        return orders;
    }
}
