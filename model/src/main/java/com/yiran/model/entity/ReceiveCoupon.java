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
     * 使用状态0未使用,1已使用,2已失效
     */
    private Byte status;
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
