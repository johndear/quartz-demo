package com.ls.quartz.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JobInfo {

	private String jobName;
	private String jobGroup;
	private byte[] origJobData;
	private String jobData;
	private String description;
	private String triggerName;
	private String triggerGroup;
	private String nextFireTime;
	private String prevFireTime;
	private String priority;
	private String triggerState;
	private String triggerType;
	private String startTime;
	private String endTime;
	private String cronExpression;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public byte[] getOrigJobData() {
		return origJobData;
	}
	public void setOrigJobData(byte[] origJobData) {
		this.origJobData = origJobData;
	}
	
	@SuppressWarnings("unchecked")
	public String getJobData() {
		Map<String, String> _jobData = new HashMap<String, String>();
		try {
			_jobData = (Map<String,String>) getObjectFromBlob(this.origJobData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jobData = JSON.toJSONString(_jobData);
		return jobData;
	}
	public void setJobData(String jobData) {
		this.jobData = jobData;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	public String getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public String getPrevFireTime() {
		return prevFireTime;
	}
	public void setPrevFireTime(String prevFireTime) {
		this.prevFireTime = prevFireTime;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getTriggerState() {
		return triggerState;
	}
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	protected static Object getObjectFromBlob(byte[] blobLocator)
	        throws ClassNotFoundException, IOException, SQLException {
	        Object obj = null;

	        if (blobLocator != null) {
	            InputStream binaryInput = new ByteArrayInputStream(blobLocator); // blobLocator.getBinaryStream();

	            if (null != binaryInput) {
	                if (binaryInput instanceof ByteArrayInputStream
	                    && ((ByteArrayInputStream) binaryInput).available() == 0 ) {
	                    //do nothing
	                } else {
	                    ObjectInputStream in = new ObjectInputStream(binaryInput);
	                    try {
	                        obj = in.readObject();
	                    } finally {
	                        in.close();
	                    }
	                }
	            }

	        }
	        return obj;
	    }
	
	

}
