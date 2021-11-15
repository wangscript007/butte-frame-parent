package com.butte.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * Redis基础配置
 * @author 公众号:知了一笑
 * @since 2021-08-07 21:10
 */
@Configuration
public class RedisConfig {

    /**
     * RedisTemplate模板
     * @since 2021-08-07 21:10
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    /**
     * StringRedisTemplate模板
     * @since 2021-08-07 21:10
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }

    /**
     * RedisLockRegistry锁机制
     * @since 2021-09-12 16:13
     */
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory factory){
        return new RedisLockRegistry(factory, "REDIS-LOCK");
    }

}
