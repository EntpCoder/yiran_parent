package com.yiran.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

/**
 * @Author 小番茄
 * @Date 2022/11/4 19:18
 */
@Data
@Document(indexName = "yiran")
@AllArgsConstructor
public class Product {
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String proId;
    @Field(type =  FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_max_word")
    private String proName;
    @Field(type = FieldType.Auto)
    private String proMainImageAddress;
    @Field(type = FieldType.Auto)
    private BigDecimal sellingPrice;
    @Field(type = FieldType.Auto)
    private BigDecimal proPrice;
    @Field(type = FieldType.Auto)
    private BigDecimal discount;
    public Product(){}

    public Product(String proId, String proName) {
        this.proId = proId;
        this.proName = proName;

    }

}
