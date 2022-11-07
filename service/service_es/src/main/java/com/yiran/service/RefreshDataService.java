package com.yiran.service;

import com.yiran.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/7 9:08
 */
@Service
@Repository
public interface RefreshDataService extends ElasticsearchRepository<Product,Integer> {
    /**
     * 更新数据库到es中的数据所需要的参数
     */
    List<Product>findByproId(String proId);
    List<Product> findByproName(String proName);
    List<Product> findByproMainImageAddress(String proMainImageAddress);
    List<Product> findBysellingPrice(String sellingPrice);
    List<Product> findByproPrice(String proPrice);
    List<Product> findBydiscount(String discount);
}
