package com.yiran.client.cart.fallback;

import com.yiran.client.cart.CartClient;
import com.yiran.common.result.R;
import org.springframework.stereotype.Component;

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
}
