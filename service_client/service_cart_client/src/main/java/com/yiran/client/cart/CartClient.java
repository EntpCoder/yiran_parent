package com.yiran.client.cart;

import com.yiran.client.cart.fallback.CartClientFallback;
import com.yiran.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Yang Song
 * @date 2022/10/1 18:22
 */
@FeignClient(name = "service-cart",fallback = CartClientFallback.class)
@Component
public interface CartClient {
    /**
     * 测试远程调用
     * @return 统一结果集
     */
    @GetMapping("/cart/test")
    R<String> testCart();
}
