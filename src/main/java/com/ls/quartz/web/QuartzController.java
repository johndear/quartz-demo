package com.ls.quartz.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ls.quartz.dao.JobAndTriggerMapper;
import com.ls.quartz.entity.JobInfo;
import com.ls.quartz.support.JobManager;

@Controller
@RequestMapping(value = "/quartz")
public class QuartzController {
	
	@Autowired
	JobAndTriggerMapper jobMapper;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return "quartz/list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody(required = false) String reqBody) throws IOException {
		JSONObject json = JSONObject.parseObject(reqBody);
		String jobName = json.getString("jobName");
		String groupName = json.getString("groupName");
		Integer pageNumber = json.getInteger("pageNumber") == null ? 1 : json.getInteger("pageNumber");
		Integer pageSize = json.getInteger("pageSize") == null ? 10 : json.getInteger("pageSize");

		List<JobInfo> list = null;
		int total = 0;
		try {
			JobInfo jobInfo = new JobInfo();
			jobInfo.setJobName(jobName);
			jobInfo.setJobGroup(groupName);
			list = jobMapper.selectByJobNameAndGroupName(jobName, groupName, pageSize * (pageNumber -1), pageSize);
			total = jobMapper.selectTotalCountByJobNameAndGroupName(jobName, groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addJob() {
		return "quartz/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addJob(String description,
			@RequestParam(value = "method", required = false, defaultValue = "get") String method,
			@RequestParam(value = "url", required = true) String url,
			@RequestParam(value = "groupName", required = true) String groupName,
			@RequestParam(value = "jobName", required = true) String jobName,
			@RequestParam(value = "cronExpression", required = true) String cronExpression,
			@RequestParam(value = "triggerPriority", required = false, defaultValue = "5") Integer triggerPriority) {
		Map<String, String> jobDataMap = new HashMap<String, String>();
		jobDataMap.put("url", url);
		jobDataMap.put("method", method);
		
		if (JobManager.checkExists(jobName, groupName)) {
			JobManager.updateJob(jobName, groupName, cronExpression, triggerPriority, description, jobDataMap);
		} else {
			JobManager.addJob(jobName, groupName, cronExpression, triggerPriority, description, jobDataMap);
		}

		return "quartz/list";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateJob(
			@RequestParam(required = true, value = "groupName") String groupName,
			@RequestParam(required = true, value = "jobName") String jobName) throws Exception {
		List<JobInfo> list = jobMapper.selectByJobNameAndGroupName(jobName, groupName, 0, Integer.MAX_VALUE);
		JobInfo jobInfo = list.get(0);
		JSONObject json = (JSONObject) JSON.parse(jobInfo.getJobData());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("jobInfo", jobInfo);
		resultMap.put("method", json.getString("method"));
		resultMap.put("url", json.getString("url"));
		return new ModelAndView("quartz/add", resultMap);
	}

	@RequestMapping(value = "/pause", method = RequestMethod.GET)
	@ResponseBody
	public String pauseJob(String groupName, String jobName) {
		JobManager.pauseJob(jobName, groupName);
		return "success";
	}

	@RequestMapping(value = "/resume", method = RequestMethod.GET)
	@ResponseBody
	public String resumeJob(String groupName, String jobName) {
		JobManager.resumeJob(jobName, groupName);
		return "success";
	}

	@RequestMapping(value = "/unschedule", method = RequestMethod.GET)
	public String unscheduleJob(String groupName, String jobName) {
		JobManager.deleteJob(jobName, groupName);
		return "quartz/list";
	}

	@RequestMapping(value = "/execute", method = RequestMethod.GET)
	@ResponseBody
	public String executeJob(String groupName, String jobName) {
		JobManager.executeJob(jobName, groupName);
		return "done!";
	}

}
