package com.yiran.pay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Yang Song
 * @date 2022/9/25 22:13
 */
@Configuration
@EnableFeignClients(basePackages = "com.yiran")
@ComponentScan("com.yiran")
public class BootConfig {
    private final AlipayProperties alipayProperties;

    public BootConfig(AlipayProperties alipayProperties) {
        this.alipayProperties = alipayProperties;
    }

    /**
     * @return DefaultAlipayClient方便后续调用Alipay接口
     */
    @Bean
    public DefaultAlipayClient getAliPayClient() throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(alipayProperties.getUrl());
        alipayConfig.setAppId(alipayProperties.getAppId());
        alipayConfig.setPrivateKey(alipayProperties.getRsaPrivateKey());
        alipayConfig.setFormat(alipayProperties.getFormat());
        alipayConfig.setCharset(alipayProperties.getCharset());
        alipayConfig.setAlipayPublicKey(alipayProperties.getAlipayPublicKey());
        alipayConfig.setSignType(alipayProperties.getSignType());
        return new DefaultAlipayClient(alipayConfig);
    }
}
