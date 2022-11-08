package com.yiran.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.Coupon;
import com.yiran.model.entity.ReceiveCoupon;
import com.yiran.model.vo.ReceiveCouponVO;
import com.yiran.service.CouponService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
     * @param coupon 优惠券对象
     * @return  true成功 false失败
     */
    @PostMapping("/createCoupon")
    public R<Boolean> createCoupon(Coupon coupon){
        return couponService.createCoupon(coupon)? R.ok("创建成功",true):R.fail(ResultCodeEnum.FAIL);
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

    /**
     * 用户抢优惠券
     * @param couponId 优惠券id
     * @param userId 用户id
     * @return 抢到的优惠券
     */
    @GetMapping("/getReceiveCoupon")
    public R<ReceiveCoupon> getReceiveCoupon(String couponId,@RequestHeader("userId") String userId){
        ReceiveCoupon receiveCoupon = couponService.getReceiveCoupon(couponId,userId);
        return receiveCoupon.getReceiveId() !=null ? R.ok("receiveCoupon",receiveCoupon):R.fail(ResultCodeEnum.FAIL);
    }
    /**
     * 通过用户优惠券id 查询优惠金额
     * @param receiveId 用户优惠券id
     * @return 优惠金额
     */
    @GetMapping("/getDiscountAmount/{receiveId}")
    public R<BigDecimal> getAmount(@PathVariable("receiveId") String receiveId){
        BigDecimal discountAmount = couponService.getDiscountAmount(receiveId);
        return discountAmount !=null ? R.ok("discountAmount",discountAmount):R.fail(ResultCodeEnum.FAIL);
    }
    /**
     * 用户查询可用优惠券
     * @param userId 用户id
     * @return 可用优惠券
     */
    @GetMapping("/getUsableCoupon")
    public R<List<ReceiveCouponVO>> getUsableCoupon(@RequestHeader("userId") String userId){
        List<ReceiveCouponVO> receiveCouponVoS = couponService.getUsableCoupon(userId);
        return receiveCouponVoS.size()> 0 ? R.ok("usableCouponList",receiveCouponVoS):R.fail(ResultCodeEnum.FAIL);
    }
    /**
     * 用户查询已失效优惠券
     * @param userId 用户Id
     * @return 优惠券集合
     */
    @GetMapping("/getFailureCoupon")
    public R<List<ReceiveCouponVO>> getByUserId(@RequestHeader("userId") String userId){
        List<ReceiveCouponVO> couponList = couponService.getFailureCoupon(userId);
        return couponList.size()> 0 ? R.ok("failureCouponList",couponList):R.fail(ResultCodeEnum.FAIL);
    }
    /**
     * 用户查询已使用优惠券
     * @param userId 用户id
     * @return 已使用优惠券
     */
    @GetMapping("/getUsedCoupon")
    public R<List<ReceiveCouponVO>> getUsedCoupon(@RequestHeader("userId") String userId){
        List<ReceiveCouponVO> couponList = couponService.getUsedCoupon(userId);
        return couponList.size()> 0 ? R.ok("usedCouponList",couponList):R.fail(ResultCodeEnum.FAIL);
    }
}
