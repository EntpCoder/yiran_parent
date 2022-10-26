package com.yiran.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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
     * 用户id
     */
    private String userId;
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
     * 是否被收藏
     */
    private Boolean isCollection;
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
     * 收件地址列表
     */
    private List<String> addressesList;
    /**
     * 尺码列表
     */
    private Set<String> sizeTypeList;
    /**
     * 颜色列表
     */
    private Set<String> colorNameList;
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
