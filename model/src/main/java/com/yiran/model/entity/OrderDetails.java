package com.yiran.model.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("order_details")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单详情id
     */
    @TableId
    private String orderDetailsId;

    /**
     * 外键订单id
     */
    private String orderId;

    /**
     * 商品属性id
     */
    private String proAttributeInfoId;

    /**
     * 商品数量
     */
    private Integer proNum;

    /**
     * 商品名称
     */
    private String proName;

    /**
     * 商品价格
     */
    private BigDecimal proPrice;
    /**
     * 商品尺寸
     */
    private String sizeType;
    /**
     * 商品颜色
     */
    private String colorName;

    /**
     * 商品主图片
     */
    private String proMainImageAddress;
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
