package com.yiran.order.service.impl;

import com.yiran.model.entity.Order;
import com.yiran.order.service.IOrdersService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yang Song
 * @date 2022/10/2 22:58
 */
@Service
public class OrderServiceImpl implements IOrdersService {
    @Override
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        Order order1 = new Order("1","订单1");
        Order order2 = new Order("2","订单2");
        Order order3 = new Order("3","订单3");
        Order order4  = new Order("4","订单4");
        Collections.addAll(list,order1,order2,order3,order4);
        return list;
    }
}
