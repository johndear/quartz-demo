package com.ls.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import com.ls.quartz.support.JobManager;
import com.ls.quartz.util.HttpUtil;

public class HttpJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("[quartz任务调度系统] 执行" + context.getJobDetail().getKey().getName() + "任务, Time at " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		JobDataMap jobData = context.getTrigger().getJobDataMap();
		
		// 手动立即执行时jobData为空
		if(StringUtils.isBlank(jobData.getString("url"))){
			try {
				TriggerKey triggerKey = TriggerKey.triggerKey(JobManager.TRIGGER_NAME_PERFIX+context.getJobDetail().getKey().getName(), context.getJobDetail().getKey().getGroup());
				Trigger trigger = JobManager.schedulerFactory.getScheduler().getTrigger(triggerKey);
				jobData = trigger.getJobDataMap();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		
		// 异步调用
		String method = jobData.getString("method");
		String url = jobData.getString("url");
		if("get".equalsIgnoreCase(method)){
			HttpUtil.AsyncGet(url);
		} else {
			HttpUtil.AsyncPost(url, null);
		}
	}

	

}
