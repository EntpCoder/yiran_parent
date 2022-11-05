package com.yiran.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yang Song
 * @date 2022/10/1 18:44
 */
@Configuration
@ComponentScan(basePackages = "com.yiran")
@EnableFeignClients(basePackages = "com.yiran.client")
public class EsBootConfig {
}
