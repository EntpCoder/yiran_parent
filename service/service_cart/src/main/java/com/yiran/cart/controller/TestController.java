package com.yiran.cart.controller;

import com.yiran.cart.service.ICartService;
import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.vo.CartVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yang Song
 * @date 2022/10/12 18:23
 */
@RestController
@RequestMapping("/cart")
public class TestController {
    private final ICartService cartService;
    public TestController(ICartService cartService){
        this.cartService = cartService;
    }
    @GetMapping("/userCart/{userId}")
    public R<List<CartVO>> userCart(@PathVariable("userId") String userId){
        List<CartVO> cartList = cartService.getUserCart(userId);
        return cartList.isEmpty() ? R.fail(ResultCodeEnum.DATA_EMPTY) : R.ok("cartList",cartList);
    }
    @GetMapping("/test")
    public R<String> testCart(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testCart");
        return R.ok("test","cart333 服务的数据");
    }

}
