package com.yiran.service.impl;

import com.yiran.model.vo.ProductVO;
import com.yiran.pojo.Product;
import com.yiran.client.product.ProductClient;
import com.yiran.service.ElasticsearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author 小番茄
 * @Date 2022/11/4 19:09
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    //模糊查询使用
    private final ElasticsearchRestTemplate restTemplate;
    private final ProductClient  productClient;
    public ElasticsearchServiceImpl(ElasticsearchRestTemplate restTemplate, ProductClient productClient) {
        this.restTemplate = restTemplate;
        this.productClient = productClient;
    }

    @Override
    public List<Product> getHighProduct(String kw) {
        //根据一个值查询多个字段，并高亮显示，这里的查询是取并集，以及多个字段只要一个字段满足即可
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                //商品名字
                .should(QueryBuilders.matchQuery("proName", kw))
                //商品描述
                .should(QueryBuilders.matchQuery("describe", kw));
        //构建高亮查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withHighlightFields(
                        new HighlightBuilder.Field("proName"),
                        new HighlightBuilder.Field("describe"))
                .withHighlightBuilder(new HighlightBuilder()
                        .preTags("<span style='color:yellow'>")
                        .preTags("</span>"))
                .build();
        //发送请求结果集
        SearchHits<Product> searchHits = restTemplate.search(searchQuery, Product.class);
        //命中的对象集合
        List<SearchHit<Product>> hits = searchHits.getSearchHits();
        //设置一个最后需要返回实体类集合
        List<Product> productList = new ArrayList<>();
        for (SearchHit<Product> searchHit : hits) {
            //高亮内容
            Map<String, List<String>> highlight = searchHit.getHighlightFields();
            //将高亮内容填充到Content中
            String proName = highlight.get("proName") == null ? searchHit.getContent().getProName() : highlight.get("proName").get(0);
            String describe = highlight.get("describe") == null ? searchHit.getContent().getProName() : highlight.get("describe").get(0);
            searchHit.getContent().setProName(proName);
            searchHit.getContent().setDescribe(describe);
            //放入到实体类中
            productList.add(searchHit.getContent());
        }
        return productList;
    }
    @Override
    public String getAllBy(){
        List<ProductVO> productVo = productClient.getAllProductVo();
        productVo.forEach(System.out::println);
        return null;
    }

}
