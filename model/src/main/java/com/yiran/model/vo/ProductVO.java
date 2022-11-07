package com.yiran.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

/**
 * @author 庆瑞瑞
 * @date 2022/10/13 18:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "yiran")
public class ProductVO {
    @Field(type = FieldType.Keyword)
    private String proId;
    @Field(type =  FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_max_word")
    private String proName;
    @Field(type = FieldType.Keyword)
    private String proMainImageAddress;
    @Field(type = FieldType.Auto)
    private BigDecimal sellingPrice;
    @Field(type = FieldType.Auto)
    private BigDecimal proPrice;
    @Field(type = FieldType.Auto)
    private BigDecimal discount;
    @Field(type = FieldType.Integer)
    private Integer proCount;
}
