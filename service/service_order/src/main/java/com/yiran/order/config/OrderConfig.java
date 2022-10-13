package com.yiran.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yang Song
 * @date 2022/10/2 23:19
 */
@Configuration
@ComponentScan("com.yiran")
@MapperScan("com.yiran.order.mapper")
@EnableFeignClients(basePackages = "com.yiran")
public class OrderConfig {
}
