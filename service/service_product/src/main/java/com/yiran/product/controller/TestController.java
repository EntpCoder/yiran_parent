package com.yiran.product.controller;

import com.yiran.client.cart.CartClient;
import com.yiran.common.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Song
 * @date 2022/10/1 14:46
 */
@RestController
public class TestController {
    private final CartClient cartClient;
    private TestController(CartClient cartClient){
        this.cartClient = cartClient;
    }
    @GetMapping("/test")
    public R<String> test(){
        R<String> r = cartClient.testCart();
        System.out.println(r);
        String data = r.getData().get("testData");
        return R.ok("111",data);
    }
}
