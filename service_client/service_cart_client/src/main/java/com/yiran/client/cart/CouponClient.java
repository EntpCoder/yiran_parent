package com.yiran.client.cart;

import com.yiran.common.result.R;
import com.yiran.model.entity.ReceiveCoupon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/3 19:44
 */
@FeignClient(name = "service-coupon")
@Component
public interface CouponClient {
    /**
     * 远程调用 消费优惠券
     * @param receiveId 领取优惠券id
     * @return 优惠金额
     */
    @GetMapping("/coupon/consumeCoupon/{receiveId}")
    R<BigDecimal> getDiscountAmount(@PathVariable("receiveId") String receiveId);
}
