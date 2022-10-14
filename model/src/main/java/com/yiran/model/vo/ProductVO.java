package com.yiran.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 庆瑞瑞
 * @date 2022/10/13 18:48
 */
@Data
public class ProductVO {
    private String proName;
    private String proMainImageAddress;
    private BigDecimal sellingPrice;
    private BigDecimal proPrice;
    private BigDecimal discount;
    private String sizeType;
    private String colorName;

}
