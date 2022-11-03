package com.yiran.order.controller;

import com.yiran.client.cart.CartClient;
import com.yiran.common.result.R;
import com.yiran.model.entity.Orders;
import com.yiran.order.service.IOrdersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/3 8:56
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final CartClient cartClient;
    private final IOrdersService ordersService;

    public OrderController(CartClient cartClient, IOrdersService ordersService) {
        this.cartClient = cartClient;
        this.ordersService = ordersService;
    }

    @GetMapping("/createOrder")
    public R<Orders> createOrder(@PathVariable String userId, String receiveId, String couponId){

        return R.ok();
    }
    @GetMapping("/queryCartOpenFein")
    public R queryCart(){
        System.out.println(cartClient.userCart("101",new String[]{"1102","1101"}));
        return R.ok();
    }
    @GetMapping("/queryOrder/{orderId}")
    public R<Orders> queryOrder(@PathVariable("orderId") String orderId){
        return R.ok("order",ordersService.queryOrder(orderId));
    }
}
