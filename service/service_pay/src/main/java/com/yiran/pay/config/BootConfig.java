package com.yiran.pay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
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
    /**
     * @return DefaultAlipayClient方便后续调用Alipay接口
     */
    @Bean
    public DefaultAlipayClient getAliPayClient() throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(AlipayProperties.URL);
        alipayConfig.setAppId(AlipayProperties.APPID);
        alipayConfig.setPrivateKey(AlipayProperties.RSA_PRIVATE_KEY);
        alipayConfig.setFormat(AlipayProperties.FORMAT);
        alipayConfig.setCharset(AlipayProperties.CHARSET);
        alipayConfig.setAlipayPublicKey(AlipayProperties.ALIPAY_PUBLIC_KEY);
        alipayConfig.setSignType(AlipayProperties.SIGN_TYPE);
        return new DefaultAlipayClient(alipayConfig);
    }
}
