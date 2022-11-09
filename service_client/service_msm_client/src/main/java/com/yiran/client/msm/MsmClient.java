package com.yiran.client.msm;

import com.yiran.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 庆瑞瑞
 * @date 2022/11/8 11:16
 */
@FeignClient(name = "service-msm")
@Component
public interface MsmClient {
    @GetMapping("/msm/getMsg")
    R<Boolean> getMsg(@RequestParam("phoneNum") String phoneNum, @RequestParam("code")String code);
}
