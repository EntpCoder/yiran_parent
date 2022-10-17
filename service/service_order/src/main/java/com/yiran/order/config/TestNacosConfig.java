package com.yiran.order.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author Yang Song
 * @date 2022/10/17 14:52
 */
@Component
@Data
@RefreshScope
public class TestNacosConfig {
    @Value("${test.name}")
    private String name;
}
