package com.yiran.client.cart;

import com.yiran.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Yang Song
 * @date 2022/10/1 18:22
 */
@FeignClient(name = "service-cart")
@Component
public interface CartClient {
    @GetMapping("/cart/test")
    public R<String> testCart();
}