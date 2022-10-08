package com.yiran.product.controller;

import com.yiran.client.cart.CartClient;
import com.yiran.common.result.R;
import com.yiran.model.entity.ProAttributeInfo;
import com.yiran.model.entity.Product;
import com.yiran.product.service.IProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yang Song
 * @date 2022/10/1 14:46
 */
@RestController("product")
public class TestController {
    private IProductService productService;

    public TestController(IProductService productService) {
        this.productService = productService;
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @GetMapping("getById/{id}")
    public R<Product> getById(@PathVariable("id") String id){
        Product product = productService.getById(id);
        return product != null ? R.ok("result",product) : R.fail();
    }
    @GetMapping("getByBrandId/{id}")
    public R<List<Product>> getByBrandId(@PathVariable("id") String BrandId){
        List<Product> products = productService.getByBrandId(BrandId);
        return products.size() > 0 ? R.ok("result",products) : R.fail();
    }
    @GetMapping("getByBrandKindSizeColor/{id}/{kind}/{sizeId}/{colorId}")
    public R<List<ProAttributeInfo>> getByBrandKindSizeColor(@PathVariable("id")  String brandId, @PathVariable("kind") String kind, @PathVariable("sizeId") String sizeId, @PathVariable("colorId") String colorId){
        List<ProAttributeInfo> products = productService.getByBrandKindSizeColor(brandId, kind, sizeId, colorId);
        return products.size() > 0 ? R.ok("result",products) : R.fail();
    }

}
