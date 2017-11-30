package com.ls.quartz.support;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.ls.quartz.job.HttpJob;

public class JobManager {
	
	public static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    public static final String TRIGGER_NAME_PERFIX = "trigger_";
	
	private JobManager(){
	}
	
	// 添加job
	public static void addJob(String jobName, String groupName, String cronExpression, int triggerPriority, String jobDescription, Map<String, String> paramsMap){
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobDetail jobDetail = newJob(HttpJob.class).withIdentity(jobName, groupName).build();
			Trigger trigger = newTrigger().withIdentity(TRIGGER_NAME_PERFIX + jobName, groupName)
							.withSchedule(cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing()) // 启动后不马上执行，而是到下个执行时间点再执行
							.withPriority(triggerPriority)
							.withDescription(jobDescription)
							.usingJobData(new JobDataMap(paramsMap)).build();
			scheduler.scheduleJob(jobDetail, trigger);
			// start the scheduler
			if (!scheduler.isStarted()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	// 修改job
	public static void updateJob(String jobName, String groupName, String cronExpression, int triggerPriority, String jobDescription, Map<String, String> jobData){
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_NAME_PERFIX + jobName, groupName);
            // 修改jobData
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger.getJobDataMap().putAll(jobData);
            // 按新的cronExpression表达式重新构建trigger
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
            		.withSchedule(scheduleBuilder)
            		.withPriority(triggerPriority)
					.withDescription(jobDescription).build();
            
            if(scheduler.getTriggerState(triggerKey) == TriggerState.PAUSED){
            	scheduler.rescheduleJob(triggerKey, trigger);
            	// 保持之前的状态
            	scheduler.pauseJob(JobKey.jobKey(jobName, groupName));
            	return;
            }
            
        	// 按新的trigger重新设置job执行
        	scheduler.rescheduleJob(triggerKey, trigger);
            
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败"+e);
        }
	}
	
	// 停止job
	public static void pauseJob(String jobName, String groupName){
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.pauseJob(JobKey.jobKey(jobName, groupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
	}
	
	// 启用job
	public static void resumeJob(String jobName, String groupName){
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.resumeJob(JobKey.jobKey(jobName, groupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
	}
	
	// 删除job
	public static void deleteJob(String jobName, String groupName){
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.deleteJob(JobKey.jobKey(jobName, groupName)); // 删除任务
		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
	}
	
	// 立即执行job, 会自动创建一个simpleTrigger执行
	public static void executeJob(String jobName, String groupName){
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.triggerJob(JobKey.jobKey(jobName, groupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
	}
	
	// 检查是否存在指定job
	public static boolean checkExists(String jobName, String groupName){
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			return scheduler.checkExists(JobKey.jobKey(jobName, groupName));
		} catch (SchedulerException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	
	
	
}
