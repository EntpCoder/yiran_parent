package com.yiran.client.cart.fallback;

import com.yiran.client.cart.CartClient;
import com.yiran.common.result.R;
import com.yiran.model.vo.CartVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Yang Song
 * @date 2022/10/14 15:02
 */
@Component
public class CartClientFallback implements CartClient {
    @Override
    public R<String> testCart() {
        return R.ok("test","兜底数据");
    }

    @Override
    public R<List<CartVO>> userCart(String userId, String[] cartIds) {
        System.out.println("回调");
        return null;
    }

    @Override
    public R<Boolean> deleteAddCart(String[] cartIds) {
        return null;
    }
}
