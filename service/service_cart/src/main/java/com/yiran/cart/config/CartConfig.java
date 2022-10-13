package com.yiran.cart.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 小番茄
 * @date 2022/10/520:58
 */
@Configuration
@ComponentScan(basePackages = "com.yiran")
public class CartConfig {
}
