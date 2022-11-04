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
import java.util.List;

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
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 订单id
     */
    @TableId
    private String orderId;

    /**
     * 订单编号
     */
    private String numbers;

    /**
     * 收件地址id
     */
    private String receiveId;

    /**
     * 外键用户id
     */
    private String usersId;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 领取优惠券id
     */
    private String receiveCouponId;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态：1未支付2已支付3已取消4待发货5待收货6已完成
     */
    private Byte orderState;

    /**
     * 下单时间
     */
    private LocalDateTime placeTime;

    /**
     * 商品数量
     */
    private Integer proNum;

    /**
     * 是否来自秒杀：1是，2不是
     */
    private Byte isSeckill;
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
    @TableField(exist = false)
    private List<OrderDetails> orderDetails;
}
