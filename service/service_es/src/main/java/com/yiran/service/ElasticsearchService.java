package com.yiran.service;

import com.yiran.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/4 19:09
 */
public interface ElasticsearchService{

    List<Product> getHighProduct(String kw);

    String getAllBy();
}
