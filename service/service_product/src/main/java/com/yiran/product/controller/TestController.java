package com.yiran.product.controller;

import com.yiran.common.result.R;
import com.yiran.model.vo.FiltrateVO;
import com.yiran.model.vo.ProductDetailVO;
import com.yiran.model.vo.ProductVO;
import com.yiran.product.service.IFiltrateService;
import com.yiran.product.service.IProductService;
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
    private final IFiltrateService filtrateService;
    public TestController(IProductService productService, IFiltrateService filtrateService) {
        this.productService = productService;
        this.filtrateService = filtrateService;
    }


    @GetMapping("getByBrandName/{brandId}")
    public R<List<ProductVO>> getByBrandName(@PathVariable("brandId") String brandId){
        List<ProductVO> productVOList = productService.getByBrandId(brandId);
        return productVOList.size() > 0 ? R.ok("result",productVOList) : R.fail();
    }
    @GetMapping("getByKindId/{kindId}")
    public R<List<ProductVO>> getByKindId(@PathVariable("kindId") String kindId){
        List<ProductVO> productVOList = productService.getByKindId(kindId);
        return productVOList.size() > 0 ? R.ok("result",productVOList) : R.fail();
    }
    @GetMapping("getByBrandKindSizeColor")
    public R<List<ProductVO>> getByBrandKindSizeColor(String brandId, String[] kindIdArr, String[] sizeIdArr, String[] colorIdArr){
        List<ProductVO> productVOList = productService.getByBrandKindSizeColor(brandId, kindIdArr, sizeIdArr, colorIdArr);
        return productVOList.size() > 0 ? R.ok("result",productVOList) : R.fail();
    }
    @GetMapping("getByProId/{proId}/{userId}")
    public R<ProductDetailVO> getByProId(@PathVariable("proId") String proId,@PathVariable("userId")String userId){
        ProductDetailVO productDetailVO = productService.getByProId(proId, userId);
        return productDetailVO != null ? R.ok("result",productDetailVO) : R.fail();

    }
    @GetMapping("getFiltrate")
    public R<FiltrateVO> getFiltrate(String brandId,String kindId){
        FiltrateVO filtrateVO = filtrateService.getFiltrate(brandId,kindId);
        return filtrateVO != null ? R.ok("result",filtrateVO) : R.fail();
    }

}
