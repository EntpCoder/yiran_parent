package com.yiran.order.service.impl;

import com.yiran.model.entity.Orders;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.order.mapper.OrdersMapper;
import com.yiran.order.service.IOrdersService;
import org.springframework.stereotype.Service;

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

}
