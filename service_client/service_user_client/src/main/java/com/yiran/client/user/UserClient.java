package com.yiran.client.user;

import com.yiran.common.result.R;
import com.yiran.model.entity.ReceiveAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 小番茄
 * @Date 2022/11/8 20:03
 */
@FeignClient(name = "service-user")
@Component
public interface UserClient {
    @RequestMapping("/receiveAddress/getAddress")
    R<ReceiveAddress> getAddress(String receiveId);
}
