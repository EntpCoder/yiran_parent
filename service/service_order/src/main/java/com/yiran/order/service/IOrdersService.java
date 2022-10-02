package com.yiran.order.service;

import com.yiran.model.entity.Order;

import java.util.List;

/**
 * @author Yang Song
 * @date 2022/10/2 22:58
 */
public interface IOrdersService {
    /**
     * 返回所有订单
     * @return 所有订单
     */
    List<Order> getAllOrders();
}
