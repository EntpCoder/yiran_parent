package com.yiran.order.controller;

import com.yiran.client.cart.CartClient;
import com.yiran.common.result.R;
import com.yiran.model.entity.Orders;
import com.yiran.order.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yang Song
 * @date 2022/10/1 20:04
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final IOrdersService ordersService;
    @Autowired
    private CartClient cartClient;
    public OrderController(IOrdersService iOrdersService){
        this.ordersService = iOrdersService;
    }
    @GetMapping("/getAllOrders")
    public R<List<Orders>> getAll(){
        List<Orders> allOrders = ordersService.list();
        return allOrders == null ? R.fail():R.ok("myOrders",allOrders);
    }
    @GetMapping("/test")
    public R<String> test(){
        return R.ok("1",cartClient.testCart().getData().get("test"));
    }
    @GetMapping("/testA")
    public R<String> testA(){
        return R.ok("A","aaaa");
    }
    @GetMapping("/testB")
    public R<String> testB(){
        return R.ok("B","bbbbb");
    }
}
