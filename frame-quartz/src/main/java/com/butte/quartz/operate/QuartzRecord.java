package com.butte.quartz.operate;


import com.butte.quartz.entity.LogState;
import com.butte.quartz.entity.QuartzJob;
import com.butte.quartz.entity.QuartzLog;
import com.butte.quartz.service.QuartzLogService;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 任务记录
 * @author 公众号:知了一笑
 * @since 2021-08-07 21:07
 */
public class QuartzRecord extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        QuartzJob quartzJob = (QuartzJob)context.getMergedJobDataMap().get(QuartzJob.JOB_PARAM_KEY) ;
        QuartzLogService quartzLogService = (QuartzLogService)SpringContextUtil.getBean("quartzLogService") ;
        // 定时器日志记录
        QuartzLog quartzLog = new QuartzLog () ;
        quartzLog.setJobId(quartzJob.getId());
        quartzLog.setBeanName(quartzJob.getBeanName());
        quartzLog.setParams(quartzJob.getParams());
        quartzLog.setCreateTime(new Date());
        long beginTime = System.currentTimeMillis() ;
        try {
            // 加载并执行
            Object target = SpringContextUtil.getBean(quartzJob.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, quartzJob.getParams());
            long executeTime = System.currentTimeMillis() - beginTime;
            quartzLog.setTimes((int)executeTime);
            quartzLog.setState(LogState.LOG_SUS.getStatus());
        } catch (Exception e){
            // 异常信息
            long executeTime = System.currentTimeMillis() - beginTime;
            quartzLog.setTimes((int)executeTime);
            quartzLog.setState(LogState.LOG_FAIL.getStatus());
            quartzLog.setError(e.getMessage());
        } finally {
            quartzLogService.insert(quartzLog) ;
        }
    }
}
