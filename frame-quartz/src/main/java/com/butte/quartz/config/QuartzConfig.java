package com.butte.quartz.config;

import com.butte.quartz.operate.QuartzOperate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置
 * @author 公众号:知了一笑
 * @since 2021-08-07 21:04
 */
@Configuration
public class QuartzConfig {

    @Bean
    @ConditionalOnMissingBean
    public QuartzOperate quartzOperate (QuartzOperate quartzOperate){
        return quartzOperate ;
    }
}
