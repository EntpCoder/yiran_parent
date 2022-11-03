package com.yiran.client.cart;

import com.yiran.client.cart.fallback.CartClientFallback;
import com.yiran.common.result.R;
import com.yiran.model.vo.CartVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Yang Song
 * @date 2022/10/1 18:22
 */
@FeignClient(name = "service-cart")
@Component
public interface CartClient {
    /**
     * 测试远程调用
     * @return 统一结果集
     */
    @GetMapping("/cart/test")
    R<String> testCart();
    @GetMapping("/cart/userCart")
    R<List<CartVO>> userCart(@RequestHeader("userId") String userId, @RequestParam("cartIds") String[] cartIds);
}
