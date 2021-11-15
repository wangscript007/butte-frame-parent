package com.butte.redis.operate;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Key-Value缓存基础操作
 * @author 公众号:知了一笑
 * @since 2021-08-07 21:12
 */
@Component
public class RedisKvOperate {

    @Resource
    private StringRedisTemplate stringRedisTemplate ;

    /**
     * 创建缓存，必须带缓存时长
     * @param key 缓存Key
     * @param value 缓存Value
     * @param expire 单位秒
     * @return boolean
     * @since 2021-08-07 21:12
     */
    public boolean set (String key, String value, long expire) {
        try {
            stringRedisTemplate.opsForValue().set(key,value,expire, TimeUnit.SECONDS);
        } catch (Exception e){
            e.printStackTrace();
            return Boolean.FALSE ;
        }
        return Boolean.TRUE ;
    }

    /**
     * 获取指定Key的缓存
     * @since 2021-08-07 21:12
     */
    public String get (String key) {
        String value = null ;
        try {
            value = String.valueOf(stringRedisTemplate.opsForValue().get(key)) ;
        } catch (Exception e){
            e.printStackTrace();
        }
        return value ;
    }
}
