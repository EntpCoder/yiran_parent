package com.yiran.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

/**
 * @author Yang Song
 * @date 2022/11/1 14:54
 */
@Data
@Component
@ConfigurationProperties(
        prefix = "yiran.auth"
)
public class AuthUrl {
    private LinkedHashSet<String> shouldSkipUrls;
}
