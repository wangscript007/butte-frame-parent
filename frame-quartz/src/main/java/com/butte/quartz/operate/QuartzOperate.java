package com.butte.quartz.operate;

import com.butte.quartz.entity.JobState;
import com.butte.quartz.entity.QuartzJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 任务操作基础封装
 * @author 公众号:知了一笑
 * @since 2021-08-07 21:07
 */
@Component
public class QuartzOperate {

    private static final String SCHEDULE_NAME = "BUTTE_JOB_" ;

    @Resource
    private Scheduler scheduler ;

    /**
     * 触发器 KEY
     */
    public TriggerKey getTriggerKey(Integer jobId){
        return TriggerKey.triggerKey(SCHEDULE_NAME+jobId) ;
    }

    /**
     * 定时器 Key
     */
    public JobKey getJobKey (Integer jobId){
        return JobKey.jobKey(SCHEDULE_NAME+jobId) ;
    }

    /**
     * 表达式触发器
     */
    public CronTrigger getCronTrigger (Integer jobId){
        try {
            return (CronTrigger) this.scheduler.getTrigger(getTriggerKey(jobId)) ;
        } catch (SchedulerException e){
            throw new RuntimeException("getCronTrigger Fail",e) ;
        }
    }

    /**
     * 创建定时器
     */
    public void createJob (QuartzJob quartzJob){
        try {
            // 构建定时器
            JobDetail jobDetail = JobBuilder.newJob(QuartzRecord.class).withIdentity(getJobKey(quartzJob.getId())).build() ;
            // 调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                                                .cronSchedule(quartzJob.getCronExpres())
                                                .withMisfireHandlingInstructionDoNothing() ;
            // 任务触发器
            CronTrigger trigger = TriggerBuilder.newTrigger()
                                                .withIdentity(getTriggerKey(quartzJob.getId()))
                                                .withSchedule(scheduleBuilder).build() ;
            jobDetail.getJobDataMap().put(QuartzJob.JOB_PARAM_KEY,quartzJob);
            scheduler.scheduleJob(jobDetail,trigger) ;
            // 状态校验
            checkStop(quartzJob) ;
        } catch (SchedulerException e){
            throw new RuntimeException("createJob Fail",e) ;
        }
    }

    /**
     * 更新定时任务
     */
    public void updateJob(QuartzJob quartzJob) {
        try {
            // 构建定时器
            TriggerKey triggerKey = getTriggerKey(quartzJob.getId());
            // 调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                                                .cronSchedule(quartzJob.getCronExpres())
                                                .withMisfireHandlingInstructionDoNothing();
            // 任务触发器
            CronTrigger trigger = getCronTrigger(quartzJob.getId())
                                .getTriggerBuilder().withIdentity(triggerKey)
                                .withSchedule(scheduleBuilder).build();
            trigger.getJobDataMap().put(QuartzJob.JOB_PARAM_KEY, quartzJob);
            scheduler.rescheduleJob(triggerKey, trigger);
            // 状态校验
            checkStop(quartzJob) ;
        } catch (SchedulerException e) {
            throw new RuntimeException("updateJob Fail",e) ;
        }
    }

    /**
     * 恢复定时器
     */
    public void resumeJob (Integer jobId){
        try {
            this.scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e){
            throw new RuntimeException("resumeJob Fail",e) ;
        }
    }

    /**
     * 删除定时器
     */
    public void deleteJob (Integer jobId){
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e){
            throw new RuntimeException("deleteJob Fail",e) ;
        }
    }

    /**
     * 执行定时器
     */
    public void run (QuartzJob quartzJob){
        try {
            JobDataMap dataMap = new JobDataMap() ;
            dataMap.put(QuartzJob.JOB_PARAM_KEY,quartzJob);
            this.scheduler.triggerJob(getJobKey(quartzJob.getId()),dataMap);
        } catch (SchedulerException e){
            throw new RuntimeException("run Fail",e) ;
        }
    }

    /**
     * 校验停止定时器
     */
    public void checkStop (QuartzJob quartzJob){
        try {
            if(quartzJob.getState() != JobState.JOB_RUN.getStatus()){
                this.scheduler.pauseJob(getJobKey(quartzJob.getId()));
            }
        } catch (SchedulerException e){
            throw new RuntimeException("pauseJob Fail",e) ;
        }
    }

}
