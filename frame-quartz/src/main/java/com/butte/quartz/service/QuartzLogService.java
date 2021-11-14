package com.butte.quartz.service;

import com.butte.quartz.entity.QuartzLog;

/**
 * 任务日志记录
 * @author 公众号:知了一笑
 * @since 2021-08-07 21:08
 */
public interface QuartzLogService {

    Integer insert (QuartzLog quartzLog) ;

}
