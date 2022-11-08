package com.yiran.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author 小番茄
 * @Date 2022/11/8 15:25
 */
@Data
public class OrderDetailsVo implements Serializable
{
    /**
     * 订单详情id
     */
    private String orderDetailsId;
    /**
     * 商品属性id
     */
    private String proAttributeInfoId;
    /**
     * 商品主图片
     */
    private String proMainImageAddress;
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
     * 商品数量
     */
    private Integer proNum;
}
