package com.yiran.client.product;


import com.yiran.model.entity.Product;
import com.yiran.model.vo.ProductVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/5 11:21
 */
@FeignClient(name = "service-product")
@Component
public interface ProductClient {
    @GetMapping("/product/getAllProByES")
    List<ProductVO> getAllProductVo();
}
