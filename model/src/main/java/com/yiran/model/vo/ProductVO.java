package com.yiran.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 庆瑞瑞
 * @date 2022/10/13 18:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private String proId;
    private String proName;
    private String proMainImageAddress;
    private BigDecimal sellingPrice;
    private BigDecimal proPrice;
    private BigDecimal discount;
}
