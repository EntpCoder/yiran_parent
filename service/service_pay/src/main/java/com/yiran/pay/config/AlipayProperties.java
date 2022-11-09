package com.yiran.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yang Song
 * @date 2022/9/23 11:08
 */
@Component
@Data
@ConfigurationProperties(
        prefix = "aliyun.pay"
)
public class AlipayProperties {
    /**
     * 商户appid
     */
    public String appId;
    /**
     * 应用私钥
     */
    public String rsaPrivateKey;
    /**
     * 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public String notifyUrl;
    /**
     * 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数， 商户可以自定义同步跳转地址
     */
    public String returnUrl;
    /**
     * 控制层重定向前端的地址
     */
    public String redirectUrl;
    /**
     * 沙盒请求网关地址
     */
    public String url;
    /**
     * 编码
     */
    public String charset;
    /**
     * 格式
     */
    public String format;
    /**
     * 支付宝公钥
     */
    public String alipayPublicKey;
    /**
     * RSA2
     */
    public String signType;
}
