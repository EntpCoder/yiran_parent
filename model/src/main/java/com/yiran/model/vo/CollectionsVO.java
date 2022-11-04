package com.yiran.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author 小番茄
 * @Date 2022/10/17 15:09
 */
@Data
public class CollectionsVO {
    /**
     *搜藏中需要得字段--图片、价格、折扣、标题
     */
    private String proId;
    /**
     * 商品主图
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
     *
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品描述
     */
    @TableField("`describe`")
    private String describe;

}
