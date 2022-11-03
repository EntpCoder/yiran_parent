package com.yiran.order.service.impl;

import com.yiran.client.cart.CartClient;
import com.yiran.common.result.R;
import com.yiran.model.entity.Orders;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.order.mapper.OrdersMapper;
import com.yiran.order.service.IOrdersService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public OrdersServiceImpl(OrdersMapper ordersMapper, CartClient cartClient) {
        this.ordersMapper = ordersMapper;
        this.cartClient = cartClient;
    }

    @Override
    public R<Orders> createOrder(String userId, String receiveId, String[] cartIds) {
        //先使用远程调用查到购物车里面的数据
        cartClient.userCart(userId,cartIds);
        //后使用查到的数据来实现订单的生成
        return null;
    }
    @Override
    public Orders queryOrder(String orderId){
        return ordersMapper.selectById(orderId);
    }

}
