package com.yiran.order.service;

import com.yiran.common.result.R;
import com.yiran.model.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
public interface IOrdersService extends IService<Orders> {
    /**
     * 生成订单id 需要参数
     * @param userId 用户id
     * @param receiveId 地址id
     * @param receiveCouponId 优惠券id
     * @param cartIds 购物车集合
     * @return 订单
     */
    String createOrder(String userId, String receiveId,String receiveCouponId, String[] cartIds);

    /**
     * 根据订单id查询订单
     * @param orderId 订单
     * @return 订单
     */
    Orders queryOrder(String orderId);
}
