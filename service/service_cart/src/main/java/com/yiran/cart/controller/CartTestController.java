package com.yiran.cart.controller;

import com.yiran.common.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Yang Song
 * @date 2022/10/1 18:07
 */
@RestController
@RequestMapping("/cart")
public class CartTestController {
    @GetMapping("/test")
    public R<String> testCart(){
        return R.ok("testData","测试数据 --宋洋");
    }
}
