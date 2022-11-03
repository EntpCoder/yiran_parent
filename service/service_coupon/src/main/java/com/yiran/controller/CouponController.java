package com.yiran.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.ReceiveCoupon;
import com.yiran.service.CouponService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author by LvJunLong
 * @date 2022/11/3
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }
    @PostMapping("/createCoupon")
    public R<Boolean> createCoupon(String subject, String discountAmount, String fullMoney,
                                   LocalDateTime grantStartTime, LocalDateTime grandEndTime,
                                   LocalDateTime usageStartTime, LocalDateTime usageEndTime,
                                   Long timelimit, Byte timeType, Integer quota){
        return couponService.createCoupon(subject,discountAmount,fullMoney, grantStartTime,grandEndTime,
                usageStartTime,usageEndTime,timelimit,timeType,quota)? R.ok("创建成功",true):R.fail(ResultCodeEnum.FAIL);
    }
    @GetMapping("/getByUserId")
    public R<List<ReceiveCoupon>> getByUserId(@RequestHeader("userId") String userId){
        List<ReceiveCoupon> couponList = couponService.getByUserId(userId);
        return couponList.size()> 0 ? R.ok("CouponList",couponList):R.fail(ResultCodeEnum.FAIL);
    }

    @GetMapping("/getDiscountAmount/{couponId}")
    public R<BigDecimal> getDiscountAmount(@PathVariable("couponId") String couponId){
        BigDecimal discountAmount = couponService.getDiscountAmount(couponId);
        return discountAmount !=null ? R.ok("discountAmount",discountAmount):R.fail(ResultCodeEnum.FAIL);
    }
    @GetMapping("/updateCouponStatus/{orderId}")
    public R<Boolean> updateCouponStatus(@PathVariable("orderId") String orderId){
        return couponService.updateCouponStatus(orderId) ? R.ok("isUpdate",true):R.fail(ResultCodeEnum.FAIL);
    }

}
