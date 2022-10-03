package com.yiran.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class Flow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流水id
     */
    @TableId
    private String flowId;

    /**
     * 支付宝返回的流水号
     */
    private String flowNum;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 支付方式  1：支付宝 2.微信
     */
    private Boolean paidMethod;

    /**
     * 支付时间
     */
    private LocalDateTime paidTime;

    /**
     * 实际支付金额
     */
    private BigDecimal paidAmount;

    /**
     * 状态：1--支付成功
     */
    private Boolean status;

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
