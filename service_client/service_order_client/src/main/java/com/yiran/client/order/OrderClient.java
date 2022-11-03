package com.yiran.client.order;

import com.yiran.common.result.R;
import com.yiran.model.entity.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Yang Song
 * @date 2022/11/3 11:25
 */
@FeignClient(name = "service-order")
@Component
public interface OrderClient {
    /**
     * 远程调用 order 查询订单信息
     * @param orderId 订单id
     * @return 订单
     */
    @GetMapping("/order/queryOrder/{orderId}")
    R<Orders> queryOrder(@PathVariable("orderId") String orderId);
}
