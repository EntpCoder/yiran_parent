package com.yiran.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yiran.model.entity.Color;
import com.yiran.model.entity.ProImage;
import com.yiran.model.entity.ReceiveAddress;
import com.yiran.model.entity.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author 庆瑞瑞
 * @date 2022/10/24 9:38
 */
@Data
public class ProductDetailVO {
    /**
     * 商品Id
     */
    private String proId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品名称
     */
    private String proName;

    /**
     * 商品描述
     */
    @TableField("`describe`")
    private String describe;
    /**
     * 商品编号
     */
    private String proNum;

    /**
     * 尺码列表
     */
    private List<Size> sizeList;
    /**
     * 颜色列表
     */
    private List<Color> colorList;
    /**
     * 主图
     */
    private String proMainImageAddress;
    /**
     * 商品缩略图
     */
    private List<String> proImageList;
    /**
     * 售价
     */
    private BigDecimal sellingPrice;

    /**
     * 原价
     */
    private BigDecimal proPrice;

    private BigDecimal discount;
}
