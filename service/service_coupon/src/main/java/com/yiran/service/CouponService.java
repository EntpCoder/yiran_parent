package com.yiran.service;


import com.yiran.model.entity.Coupon;
import com.yiran.model.entity.ReceiveCoupon;
import com.yiran.model.vo.ReceiveCouponVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author by LvJunLong
 * @date 2022/11/3
 */
public interface CouponService {
    /**
     * 商家生成优惠券
     * @param coupon 优惠券对象
     * @return  true成功 false失败
     */
    Boolean createCoupon(Coupon coupon);



    /**
     * 通过用户优惠券id 消费优惠券
     * @param receiveId 优惠券id
     * @return 优惠金额
     */
    BigDecimal consumeCoupon(String receiveId);

    /**
     * 根据订单id  改优惠券状态
     * @param orderId 订单id
     * @return 优惠券状态
     */
    Boolean updateCouponStatus(String orderId);

    /**
     * 查询可领取优惠券
     * @return 可领取优惠券列表
     */
    List<Coupon> getCouponList();

    /**
     * 用户抢优惠券
     * @param couponId 优惠券id
     * @param userId 用户id
     * @return 抢到的优惠券
     */
    ReceiveCoupon getReceiveCoupon(String couponId,String userId);

    /**
     * 通过用户优惠券id 查询优惠金额
     * @param receiveId 用户优惠券id
     * @return 优惠金额
     */
    BigDecimal getDiscountAmount(String receiveId);

    /**
     * 用户查询已失效优惠券
     * @param usrId 用户Id
     * @return 优惠券集合
     */
    List<ReceiveCouponVO> getFailureCoupon(String usrId);

    /**
     * 用户查询可用优惠券
     * @param usrId 用户id
     * @return 可用优惠券
     */
    List<ReceiveCouponVO> getUsableCoupon(String usrId);

    /**
     * 用户查询已使用优惠券
     * @param usrId 用户id
     * @return 已使用优惠券
     */
    List<ReceiveCouponVO> getUsedCoupon(String usrId);
}
