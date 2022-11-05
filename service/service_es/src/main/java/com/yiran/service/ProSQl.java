package com.yiran.service;

import com.yiran.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/5 10:35
 */
@Service
@Repository
public interface ProSQl extends ElasticsearchRepository<Product ,Integer> {
    List<Product>findByproId(String proId);
    List<Product> findByproName(String proName);
    List<Product> findBykind(String kind);
    List<Product> findByproMainImageAddress(String proMainImageAddress);
    List<Product> findBysellingPrice(String sellingPrice);
    List<Product> findByproPrice(String proPrice);
    List<Product> findBydiscount(String discount);
    List<Product> findBybrandId(String brandId);
    List<Product> findBydescribe(String describe);

}
