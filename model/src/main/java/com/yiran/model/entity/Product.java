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
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId
    private String proId;

    /**
     * 商品编号
     */
    private String proNum;

    /**
     * 商品名称
     */
    private String proName;

    /**
     * 商品品类
     */
    private String kind;

    /**
     * 商品主图地址
     */
    private String proMainImageAddress;

    /**
     * 售价
     */
    private BigDecimal sellingPrice;

    /**
     * 原价
     */
    private BigDecimal proPrice;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 商品描述
     */
    @TableField("`describe`")
    private String describe;

    /**
     * 满意度
     */
    private BigDecimal degreeOfSatisfaction;
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
