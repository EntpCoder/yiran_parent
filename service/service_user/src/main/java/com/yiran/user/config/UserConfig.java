package com.yiran.user.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author weiyuwen
 *
 */
@Configuration
@ComponentScan(basePackages = "com.yiran")
@EnableFeignClients(basePackages = "com.yiran")
public class UserConfig {

}
