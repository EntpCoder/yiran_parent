package com.yiran.client.cart;

import com.yiran.client.cart.fallback.CartClientFallback;
import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.ReceiveCoupon;
import com.yiran.model.vo.CartVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 根据购物车id来结算订单--批量删除
     * @param cartIds 购物车id
     * @return R
     */
    @PostMapping ("/cart/deleteAddCart")
    R<Boolean> deleteAddCart(@RequestParam String[] cartIds);

}
