package com.yiran.product.controller;

import com.yiran.common.result.R;
import com.yiran.model.vo.ProductVO;
import com.yiran.product.service.IProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Song
 * @date 2022/10/1 14:46
 */
@RestController
@RequestMapping("/product")
public class TestController {
    private final IProductService productService;
    public TestController(IProductService productService) {
        this.productService = productService;
    }


    @GetMapping("getByBrandName/{brandId}")
    public R<List<ProductVO>> getByBrandName(@PathVariable("brandId") String brandId){
        List<ProductVO> productVOList = productService.getByBrandId(brandId);
        return productVOList.size() > 0 ? R.ok("result",productVOList) : R.fail();
    }

    @GetMapping("xx")
    public R<String> getByBrandKindSizeC(String[] kinds){
        System.out.println(Arrays.toString(kinds));
        return R.ok("1","11");
    }

}
