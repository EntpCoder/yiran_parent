package com.yiran.controller;

import com.yiran.common.result.R;
import com.yiran.pojo.Product;
import com.yiran.service.ElasticsearchService;
import com.yiran.service.ProSQl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/4 19:09
 */
@RestController
@RequestMapping("/es")
public class ElasticsearchController {
    private final ProSQl proSQl;
    private final ElasticsearchService elasticsearchService;
    public ElasticsearchController(ProSQl proSQl, ElasticsearchService elasticsearchService) {
        this.proSQl = proSQl;
        this.elasticsearchService = elasticsearchService;
    }

    @GetMapping("/getHighProduct")
    public R<List<Product>> getHighProduct(@RequestParam("kw") String kw){
        List<Product> highProduct = elasticsearchService.getHighProduct(kw);
        return R.ok("",highProduct);
    }
    @GetMapping("saveUser")
    public String saveUser(String proName){
        List<Product> users = new ArrayList<>();
        elasticsearchService.getAllBy();
        return "save successfully";
    }

}
