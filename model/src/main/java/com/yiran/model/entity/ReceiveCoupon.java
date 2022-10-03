package com.yiran.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("receive_coupon")
public class ReceiveCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 已领取的优惠券
     */
    @TableId
    private String receiveId;

    /**
     * 外键用户id
     */
    private String userId;

    /**
     * 外键优惠券id
     */
    private String couponId;

    /**
     * 领取时间
     */
    private LocalDateTime drawTime;

    /**
     * 有效开始时间
     */
    private LocalDateTime startTime;

    /**
     * 失效时间
     */
    private LocalDateTime expirationTime;

    /**
     * 状态
     */
    private Byte condition;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDelete;

    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)

    private LocalDateTime updateTime;

    private String other1;

    private String other2;
}