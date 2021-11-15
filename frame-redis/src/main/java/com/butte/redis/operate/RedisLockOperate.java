package com.butte.redis.operate;

import cn.hutool.core.util.BooleanUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * RedisLock工具
 * @author 公众号:知了一笑
 * @since 2021-09-12 13:13
 */
@Component
public class RedisLockOperate {

    @Resource
    protected RedisLockRegistry redisLockRegistry;

    /**
     * 等待锁的时间
     */
    @Value("${redis.lock.time:5000}")
    private Long time ;

    /**
     * 默认重试3次
     */
    @Value(("${redis.lock.retryNum:3}"))
    private Integer retryNum;

    /**
     * 尝试一次加锁，采用默认时间
     * @param lockKey 加锁Key
     * @return java.lang.Boolean
     * @since 2021-09-12 13:14
     */
    @SneakyThrows
    public <T> Boolean tryLock(T lockKey) {
        return redisLockRegistry.obtain(lockKey).tryLock(time, TimeUnit.MILLISECONDS);
    }

    /**
     * 尝试一次加锁
     * @param lockKey 加锁Key
     * @param time 有效时长
     * @return java.lang.Boolean
     * @since 2021-09-12 13:14
     */
    @SneakyThrows
    public <T> Boolean tryLock(T lockKey, Long time) {
        return redisLockRegistry.obtain(lockKey).tryLock(time, TimeUnit.MILLISECONDS);
    }

    /**
     * 重试机制多次加锁
     * @param lockKey 加锁Key
     * @param time 有效时长
     * @return java.lang.Boolean
     * @since 2021-09-12 13:22
     */
    @SneakyThrows
    public <T> Boolean reTryLock(T lockKey, Long time) {
        Boolean lockFlag = tryLock(lockKey, time);
        if (BooleanUtil.isTrue(lockFlag)) {
            return lockFlag ;
        }
        for (int i = 0; i < retryNum; i++) {
            if (BooleanUtil.isTrue(tryLock(lockKey, time))){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 解锁
     * @param lockKey 解锁Key
     * @since 2021-09-12 13:32
     */
    public <T> void unlock(T lockKey) {
        redisLockRegistry.obtain(lockKey).unlock();
    }

}
