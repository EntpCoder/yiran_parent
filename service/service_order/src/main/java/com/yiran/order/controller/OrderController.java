package com.yiran.order.controller;

import com.yiran.common.result.R;
import com.yiran.model.entity.Order;
import com.yiran.order.service.IOrdersService;
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
    public OrderController(IOrdersService iOrdersService){
        this.ordersService = iOrdersService;
    }
    @GetMapping("/getAllOrders")
    public R<List<Order>> getAll(){
        List<Order> allOrders = ordersService.getAllOrders();
        return allOrders == null ? R.fail():R.ok("myOrders",allOrders);
    }
}
