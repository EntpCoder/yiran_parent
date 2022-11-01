package com.yiran.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Yang Song
 * @date 2022/10/12 18:25
 */
@Data
public class CartVO {
    private String cartId;
    private String proAttributeInfoId;
    private String proMainImageAddress;
    private String proName;
    private String sizeType;
    private String colorName;
    private BigDecimal sellingPrice;
    private Integer nums;
}
