package com.yiran.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Objects;

/**
 * @author Yang Song
 * @date 2022/11/2 17:43
 */
@Configuration
@EnableCaching
public class RedisConfig {
    private final RedisConnectionFactory redisConnectionFactory;
    public RedisConfig(RedisConnectionFactory redisConnectionFactory){
        this.redisConnectionFactory = redisConnectionFactory;
    }
    @Bean
    @Primary
    public RedisTemplate<String, ?> functionDomainRedisTemplate() {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 配置redisTemplate 信息
     * @param redisTemplate RedisTemplate
     * @param factory RedisConnectionFactory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, ?> redisTemplate, RedisConnectionFactory factory) {
        // 序列化策略
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }
    @Bean
    public CacheManager cacheManager(RedisTemplate<String, ?> template) {
        // 基本配置
        RedisCacheConfiguration defaultCacheConfiguration =
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        // 不缓存null
                        .disableCachingNullValues()
                        // 缓存数据保存1小时
                        .entryTtl(Duration.ofHours(24));
        // 返回一个redis缓存管理器
        return RedisCacheManager.RedisCacheManagerBuilder
                // Redis 连接工厂
                .fromConnectionFactory(Objects.requireNonNull(template.getConnectionFactory()))
                // 缓存配置
                .cacheDefaults(defaultCacheConfiguration)
                // 配置同步修改或删除 put/evict
                .transactionAware()
                .build();
    }
}
