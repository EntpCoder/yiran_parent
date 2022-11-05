package com.yiran.product.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.Product;
import com.yiran.model.vo.FiltrateVO;
import com.yiran.model.vo.ProductDetailVO;
import com.yiran.model.vo.ProductInfoNumVO;
import com.yiran.model.vo.ProductVO;
import com.yiran.product.service.IProductService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public R<List<ProductVO>> getByBrandName(@PathVariable("brandId") String brandId) {
        List<ProductVO> productVOList = productService.getByBrandId(brandId);
        return productVOList.size() > 0 ? R.ok("result", productVOList) : R.fail();
    }

    @GetMapping("getByKindId/{kindId}")
    public R<List<ProductVO>> getByKindId(@PathVariable("kindId") String kindId) {
        List<ProductVO> productVOList = productService.getByKindId(kindId);
        return productVOList.size() > 0 ? R.ok("result", productVOList) : R.fail();
    }

    @GetMapping("getByBrandKindSizeColor")
    public R<List<ProductVO>> getByBrandKindSizeColor(String[] brandIdArr, String[] kindIdArr, String[] sizeIdArr, String[] colorIdArr) {
        List<ProductVO> productVOList = productService.getByBrandKindSizeColor(brandIdArr, kindIdArr, sizeIdArr, colorIdArr);
        return productVOList.size() > 0 ? R.ok("result", productVOList) : R.fail();
    }

    @GetMapping("getByProId/{proId}")
    public R<ProductDetailVO> getByProId(@PathVariable("proId") String proId) {
        ProductDetailVO productDetailVO = productService.getByProId(proId);
        return productDetailVO != null ? R.ok("result", productDetailVO) : R.fail();

    }

    @GetMapping("getFiltrate")
    public R<FiltrateVO> getFiltrate(String brandId, String kindId) {
        FiltrateVO filtrateVO = productService.getFiltrate(brandId, kindId);
        return filtrateVO != null ? R.ok("result", filtrateVO) : R.fail();
    }

    @GetMapping("getProInfoId/{proId}/{colorId}/{sizeId}")
    public R<ProductInfoNumVO> getProInfoId(@PathVariable("proId") String proId,
                                            @PathVariable("colorId") String colorId,
                                            @PathVariable("sizeId") String sizeId) {
        ProductInfoNumVO productInfoNumVO = productService.getProInfoId(proId, sizeId, colorId);
        return productInfoNumVO != null ? R.ok("result", productInfoNumVO) : R.fail();
    }
    /**
     * ES根据传入的商品名字或商品描述搜索商品
     * @return 商品集合
     */
    @GetMapping("/getAllProByES")
    public List<ProductVO> getAllProductVo(){
        return productService.getAllProductVo();
    }
}
