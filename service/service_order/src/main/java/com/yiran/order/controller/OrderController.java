package com.yiran.order.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.Orders;
import com.yiran.model.vo.OrdersVO;
import com.yiran.order.service.IOrdersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author 小番茄
 * @Date 2022/11/3 8:56
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final IOrdersService ordersService;

    public OrderController(IOrdersService ordersService) {
        this.ordersService = ordersService;
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
    @GetMapping("/createOrder")
    public R<String> createOrder(@RequestHeader("userId") String userId,
                                 String receiveId,
                                 String receiveCouponId, String[] cartIds) {
        return R.ok("orderId", ordersService.createOrder(userId, receiveId, receiveCouponId, cartIds));
    }

    /**
     * 更具订单id来查询
     *
     * @param orderId 订单id
     * @return 返回订单
     */
    @GetMapping("/queryOrder/{orderId}")
    public R<Orders> queryOrder(@PathVariable("orderId") String orderId) {
        return R.ok("order", ordersService.queryOrder(orderId));
    }

    /**
     * 根据订单号获取订单详情
     * @return 订单详情
     */
    @GetMapping("/getOrderAndDetail/{orderId}")
    public R<Orders> getOrderDetails(@PathVariable("orderId") String orderId) {
        Orders orders = ordersService.getOrderAndDetails(orderId);
        return orders == null ? R.fail(ResultCodeEnum.DATA_EMPTY) : R.ok("order", orders);
    }
    /**
     * 根据用户id来查询用户的全部订单
     * @param usersId 用户id
     * @return 订单集合
     */
    @GetMapping("/getAllOrders")
    public R<List<OrdersVO>> getAllOrders(@RequestHeader("userId") String usersId){
        return R.ok("ordersList", ordersService.getAllOrders(usersId));
    }

    /**
     * 根据用户id以及状态编码来查询用户状态订单
     * @param usersId 用户id
     * @param orderState 状态编码-订单状态：0未支付 1已支付 2已取消  3待收货 4已完成
     * @return 状态集合
     */
    @GetMapping("/getOrdersByStatus")
    public R<List<OrdersVO>> getOrdersByStatus(@RequestHeader("userId") String usersId,Byte orderState){
        return R.ok("QueryStatusList", ordersService.getOrdersByStatus(usersId,orderState));
    }
}
