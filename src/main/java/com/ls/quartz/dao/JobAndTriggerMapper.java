package com.ls.quartz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ls.quartz.entity.JobInfo;

public interface JobAndTriggerMapper {

	public List<JobInfo> selectAll(Integer id);
	
	public List<JobInfo> selectByJobNameAndGroupName(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup, @Param("start") int start, @Param("offset") int offset);
	
	public int selectTotalCountByJobNameAndGroupName(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup);
}
