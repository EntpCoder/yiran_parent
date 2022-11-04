package com.yiran.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.Coupon;
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

    /**
     *
     * 商家生成优惠券
     * @param subject 优惠券标题
     * @param discountAmount 优惠金额
     * @param fullMoney 满减金额
     * @param grantStartTime 发放开始时间
     * @param grandEndTime  发放结束时间
     * @param usageStartTime 使用时间
     * @param usageEndTime 使用结束时间
     * @param timelimit 优惠券时限
     * @param timeType  时效类型
     * @param quota 配额
     * @return  true成功 false失败
     */
    @PostMapping("/createCoupon")
    public R<Boolean> createCoupon(String subject, String discountAmount, String fullMoney,
                                   LocalDateTime grantStartTime, LocalDateTime grandEndTime,
                                   LocalDateTime usageStartTime, LocalDateTime usageEndTime,
                                   Long timelimit, Byte timeType, Integer quota){
        return couponService.createCoupon(subject,discountAmount,fullMoney, grantStartTime,grandEndTime,
                usageStartTime,usageEndTime,timelimit,timeType,quota)? R.ok("创建成功",true):R.fail(ResultCodeEnum.FAIL);
    }
    /**
     * 用户查询已领取优惠券
     * @param userId 用户Id
     * @return 优惠券集合
     */
    @GetMapping("/getByUserId")
    public R<List<ReceiveCoupon>> getByUserId(@RequestHeader("userId") String userId){
        List<ReceiveCoupon> couponList = couponService.getByUserId(userId);
        return couponList.size()> 0 ? R.ok("CouponList",couponList):R.fail(ResultCodeEnum.FAIL);
    }
    /**
     * 消费优惠券
     * @param receiveId 优惠券id
     * @return 优惠金额
     */
    @GetMapping("/consumeCoupon/{receiveId}")
    public R<BigDecimal> getDiscountAmount(@PathVariable("receiveId") String receiveId){
        BigDecimal discountAmount = couponService.consumeCoupon(receiveId);
        return discountAmount !=null ? R.ok("discountAmount",discountAmount):R.fail(ResultCodeEnum.FAIL);
    }
    /**
     * 根据订单id  改优惠券状态
     * @param orderId 订单id
     * @return 优惠券状态
     */
    @GetMapping("/updateCouponStatus/{orderId}")
    public R<Boolean> updateCouponStatus(@PathVariable("orderId") String orderId){
        return couponService.updateCouponStatus(orderId) ? R.ok("isUpdate",true):R.fail(ResultCodeEnum.FAIL);
    }

    /**
     * 查询可领取优惠券
     * @return 可领取优惠券列表
     */
    @GetMapping("/getCouponList")
    public R<List<Coupon>> getCouponList(){
        List<Coupon> couponList = couponService.getCouponList();
        return couponList.size() > 0 ? R.ok("couponList",couponList):R.fail(ResultCodeEnum.FAIL);
    }
}
