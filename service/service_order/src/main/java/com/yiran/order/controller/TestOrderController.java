package com.yiran.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.yiran.client.cart.CartClient;
import com.yiran.common.result.R;
import com.yiran.model.entity.Orders;
import com.yiran.order.service.IOrdersService;
import com.yiran.serviceutil.BlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yang Song
 * @date 2022/10/1 20:04
 */
@RestController
@RequestMapping("/order")
public class TestOrderController {
    private final IOrdersService ordersService;
    private final CartClient cartClient;

    public TestOrderController(IOrdersService ordersService, CartClient cartClient) {
        this.ordersService = ordersService;
        this.cartClient = cartClient;
    }

    @GetMapping("/getAllOrders")
    public R<List<Orders>> getAll(){
        List<Orders> allOrders = ordersService.list();
        System.out.println("获取所有订单");
        return allOrders == null ? R.fail():R.ok("myOrders",allOrders);
    }
    @GetMapping("/testOpenFein")
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
    @GetMapping("/testBlow")
    public R<String> testBlow(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.ok("testBlow","testBlowtestBlow");
    }
    @GetMapping("/testHot/{userId}/{proId}")
    @SentinelResource(value = "testHot",
                        blockHandlerClass = BlockHandler.class,
                        blockHandler = "soHot")
    public R<String> testHot(@PathVariable("userId") String userId,
                             @PathVariable("proId") String proId){
        System.out.println(userId+":"+proId);
        return R.ok("testHot","假装我是热点数据");
    }
}