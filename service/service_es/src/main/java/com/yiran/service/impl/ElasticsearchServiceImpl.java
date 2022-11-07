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
    private final ElasticsearchRestTemplate restTemplate;
    private final ProductClient  productClient;
    public ElasticsearchServiceImpl(ElasticsearchRestTemplate restTemplate, ProductClient productClient) {
        this.restTemplate = restTemplate;
        this.productClient = productClient;

    }

    /**
     * 高亮查询--根据商品名字分词查询
     * @param kw 封装的名字
     * @return 返回商品集合
     */
    @Override
    public List<Product> getHighProduct(String kw) {
        //根据一个值查询多个字段，并高亮显示，这里的查询是取并集，以及多个字段只要一个字段满足即可
        //需要查询的字段
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("proName", kw));
        //构建高亮查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withHighlightFields(
                        new HighlightBuilder.Field("proName"))
                .withHighlightBuilder(new HighlightBuilder()
                        .preTags("<span style='color:yellow'>")
                        .postTags("</span>"))
                .build();
        //查询
        SearchHits<Product> search = restTemplate.search(searchQuery,Product.class);
        //得到查询返回的内容
        List<SearchHit<Product>> searchHits = search.getSearchHits();
        //设置一个最后需要返回实体类集合
        List<Product> productList =new ArrayList<>();
        for (SearchHit<Product> searchHit: searchHits){
            //高亮内容
            Map<String,List<String>> higliig =searchHit.getHighlightFields();
            //将高亮内容填充到Content中
            String proName = higliig.get("proName") == null? searchHit.getContent().getProName():higliig.get("proName").get(0);
           // String describe = higliig.get("describe") == null? searchHit.getContent().getProName():higliig.get("describe").get(0);
            searchHit.getContent().setProName(proName);
            //放入到实体类中
            productList.add(searchHit.getContent());
        }
        return productList;
    }
    /**
     * 更新数据库数据到es中
     * @return 返回成功
     */
    @Override
    public List<Product> getAllBy(){
        List<ProductVO> allProductVo = productClient.getAllProductVo();
        List<Product> products = new ArrayList<>();
        for (ProductVO p :
             allProductVo) {
            Product  product =new Product(p.getProId(),p.getProName());
            product.setId(Integer.valueOf((p.getProId())));
            product.setProName(p.getProName());
            product.setProMainImageAddress(p.getProMainImageAddress());
            product.setProPrice(p.getProPrice());
            product.setSellingPrice(p.getSellingPrice());
            product.setDiscount(p.getDiscount());
            products.add(product);
            System.out.println(products);
        }
        return products;

    }
}
