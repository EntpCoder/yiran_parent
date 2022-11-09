package com.yiran.controller;

import com.yiran.common.result.R;
import com.yiran.pojo.Product;
import com.yiran.service.ElasticsearchService;
import com.yiran.service.RefreshDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/4 19:09
 */
@RestController
@RequestMapping("/es")
public class ElasticsearchController {
    private final ElasticsearchService elasticsearchService;
    private final RefreshDataService refreshDataService;
    public ElasticsearchController(ElasticsearchService elasticsearchService, RefreshDataService refreshDataService) {
        this.elasticsearchService = elasticsearchService;
        this.refreshDataService = refreshDataService;
    }
    /**
     * 高亮查询--根据商品名字分词查询
     * @param kw 封装的名字
     * @return 返回商品集合
     */
    @GetMapping("/getHighProduct")
    public R<List<Product>> getHighProduct(@RequestParam("kw") String kw){
        List<Product> highProduct = elasticsearchService.getHighProduct(kw);
        return R.ok("highProduct",highProduct);
    }
    /**
     * 更新数据库数据到es中
     * @return 返回成功
     */
    @GetMapping("refreshDataService")
    public R<String> refreshData(){
        List<Product> allBy = elasticsearchService.getAllBy();
        refreshDataService.saveAll(allBy);
        System.out.println("================");
        return R.ok();
    }
}
