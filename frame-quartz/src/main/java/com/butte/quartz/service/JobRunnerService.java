package com.butte.quartz.service;

/**
 * 任务执行接口
 * @author 公众号:知了一笑
 * @since 2021-08-07 21:08
 */
public interface JobRunnerService {

    void run(String params);

}
