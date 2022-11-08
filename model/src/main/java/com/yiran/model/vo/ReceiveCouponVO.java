package com.yiran.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author by LvJunLong
 * @date 2022/11/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveCouponVO {
    private String receiveId;
    private String userId;
    private String couponId;
    private LocalDateTime drawTime;
    private LocalDateTime startTime;
    private LocalDateTime expirationTime;
    private Byte status;
    private String subject;
    private BigDecimal discountAmount;
    private BigDecimal fullMoney;
    private String numbers;
    private LocalDateTime updateTime;
}
