package com.yiran.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.mapper.CouponMapper;
import com.yiran.mapper.OrdersMapper;
import com.yiran.mapper.ReceiveCouponMapper;
import com.yiran.model.entity.Coupon;
import com.yiran.model.entity.Orders;
import com.yiran.model.entity.ReceiveCoupon;
import com.yiran.service.CouponService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author by LvJunLong
 * @date 2022/11/3
 */
@Service
public class CouponServiceImpl implements CouponService {
    private final CouponMapper couponMapper;
    private final ReceiveCouponMapper receiveCouponMapper;
    private final OrdersMapper ordersMapper;

    public CouponServiceImpl(CouponMapper couponMapper, ReceiveCouponMapper receiveCouponMapper, OrdersMapper ordersMapper) {
        this.couponMapper = couponMapper;
        this.receiveCouponMapper = receiveCouponMapper;
        this.ordersMapper = ordersMapper;
    }

    @Override
    public Boolean createCoupon(String subject, String discountAmount, String fullMoney, LocalDateTime grantStartTime, LocalDateTime grandEndTime,LocalDateTime usageStartTime,LocalDateTime usageEndTime,Long timelimit,Byte timeType, Integer quota) {
        Coupon coupon = new Coupon();
        coupon.setSubject(subject);
        BigDecimal disAccount = new BigDecimal(discountAmount);
        BigDecimal fullAccount = new BigDecimal(fullMoney);
        coupon.setDiscountAmount(disAccount);
        coupon.setFullMoney(fullAccount);
        coupon.setGrantStartTime(grantStartTime);
        coupon.setGrandEndTime(grandEndTime);
        coupon.setTimeType(timeType);
        coupon.setQuota(quota);
        coupon.setIsDelete(false);
        if (timeType == 0){
            coupon.setUsageStartTime(usageStartTime);
            coupon.setUsageEndTime(usageEndTime);
        }
        if (timeType == 1){
            coupon.setTimelimit(timelimit);
        }
        return couponMapper.insert(coupon) >0;
    }

    @Override
    public List<ReceiveCoupon> getByUserId(String usrId) {
        QueryWrapper<ReceiveCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",usrId);
        return receiveCouponMapper.selectList(queryWrapper);
    }

    @Override
    public BigDecimal consumeCoupon(String receiveId) {
        ReceiveCoupon reCoupon = receiveCouponMapper.selectById(receiveId);
        if (reCoupon.getStatus()==(byte) 0){
            reCoupon.setStatus((byte) 1);
            Coupon coupon = couponMapper.selectById(reCoupon.getCouponId());
            return coupon.getDiscountAmount();
        } else{
            return new BigDecimal(0);
        }
    }

    @Override
    public Boolean updateCouponStatus(String orderId) {
        Orders order = ordersMapper.selectById(orderId);
        ReceiveCoupon reCoupon = receiveCouponMapper.selectById(order.getReceiveCouponId());
        if (reCoupon.getStatus()==(byte) 0){
            reCoupon.setStatus((byte) 1);
            return receiveCouponMapper.updateById(reCoupon) > 0;
        } else{
            return false;
        }
    }

    @Override
    public List<Coupon> getCouponList() {
        //加载所有有效的优惠券
        LocalDateTime currentTime = LocalDateTime.now();
        return couponMapper.selectList(new QueryWrapper<Coupon>().le("grant_start_time",currentTime).ge("grand_end_time",currentTime));
    }
}
