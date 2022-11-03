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
    private final ReceiveCouponMapper receiveCoupon;
    private final OrdersMapper ordersMapper;

    public CouponServiceImpl(CouponMapper couponMapper, ReceiveCouponMapper receiveCoupon, OrdersMapper ordersMapper) {
        this.couponMapper = couponMapper;
        this.receiveCoupon = receiveCoupon;
        this.ordersMapper = ordersMapper;
    }

    @Override
    public Boolean createCoupon(String subject, String discountAmount, String fullMoney, LocalDateTime grantStartTime, LocalDateTime grandEndTime,LocalDateTime usageStartTime,LocalDateTime usageEndTime,Long timelimit,Byte timeType, Integer quota) {
        Coupon coupon = new Coupon();
        coupon.setCouponId("1");
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
        return receiveCoupon.selectList(queryWrapper);
    }

    @Override
    public BigDecimal getDiscountAmount(String couponId) {
        Coupon coupon = couponMapper.selectById(couponId);
        return coupon.getDiscountAmount();
    }

    @Override
    public Boolean updateCouponStatus(String orderId) {
        Orders order = ordersMapper.selectById(orderId);
        ReceiveCoupon reCoupon = receiveCoupon.selectById(order.getReceiveCouponId());
        if (reCoupon.getStatus()==(byte) 0){
            reCoupon.setStatus((byte) 1);
            return receiveCoupon.updateById(reCoupon) > 0;
        } else{
            return false;
        }
    }
}
