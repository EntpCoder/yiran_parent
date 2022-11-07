package com.yiran.service;

import com.yiran.pojo.Product;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/4 19:09
 */
public interface ElasticsearchService{

    /**
     * 高亮查询--根据商品名字分词查询
     * @param kw 封装的名字
     * @return 返回商品集合
     */
    List<Product> getHighProduct(String kw);
    /**
     * 更新数据库数据到es中
     * @return 返回成功
     */
    List<Product> getAllBy();
}
