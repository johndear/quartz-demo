//package com.ls.quartz.dao;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.ObjectInputStream;
//import java.sql.Blob;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.ls.quartz.util.JdbcUtil;
//
//public class JdbcJobStoreAccessor {
//
//	private static String query_sql = "select a.job_name,a.job_group,b.job_data,b.DESCRIPTION,b.TRIGGER_NAME,b.TRIGGER_GROUP,b.NEXT_FIRE_TIME,b.PREV_FIRE_TIME,b.PRIORITY,b.TRIGGER_STATE,b.TRIGGER_TYPE,b.START_TIME,b.END_TIME,c.CRON_EXPRESSION from qrtz_job_details a left join qrtz_triggers b on a.JOB_NAME=b.JOB_NAME and a.JOB_GROUP=b.JOB_GROUP left join qrtz_cron_triggers c on b.trigger_name=c.trigger_name and b.trigger_group=c.trigger_group and b.sched_name=c.sched_name where 1=1 ";
//	
//	private static String update_trigger_cron = "UPDATE qrtz_cron_triggers t SET t.CRON_EXPRESSION=? WHERE t.SCHED_NAME=? AND t.TRIGGER_GROUP=? AND t.TRIGGER_NAME=?";
//
//	public static int queryTotalCount(String jobName, String groupName) throws Exception {
//		String sql = query_sql;
//		List<Object> params = new ArrayList<Object>();
//		if(StringUtils.isNotBlank(jobName) && StringUtils.isNotBlank(groupName)){
//			sql += " and a.job_name = ? and a.job_group = ?";
//			params.add(jobName);
//			params.add(groupName);
//		} else if(StringUtils.isNotBlank(jobName)){
//			sql += " and a.job_name = ?";
//			params.add(jobName);
//		} else if(StringUtils.isNotBlank(groupName)){
//			sql += " and a.job_group = ?";
//			params.add(groupName);
//		}
//		String excuteSql = "select count(*) cnt from (" + sql + ") t";
//		Connection conn = JdbcUtil.getConnection();
//		ResultSet rs = JdbcUtil.query(JdbcUtil.getConnection(), excuteSql, params.toArray());
//		if(rs!=null && rs.next()){
//			int total = rs.getInt("cnt");
//			return total;
//		}
//		rs.close();
//		conn.close();
//		return 0;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static List<Object> query(int pageSize, int pageNumber, String jobName, String groupName) throws Exception {
//		String sql = query_sql;
//		List<Object> params = new ArrayList<Object>();
//		if(StringUtils.isNotBlank(jobName) && StringUtils.isNotBlank(groupName)){
//			sql += " and a.job_name = ? and a.job_group = ?";
//			params.add(jobName);
//			params.add(groupName);
//		} else if(StringUtils.isNotBlank(jobName)){
//			sql += " and a.job_name = ?";
//			params.add(jobName);
//		} else if(StringUtils.isNotBlank(groupName)){
//			sql += " and a.job_group = ?";
//			params.add(groupName);
//		}
//		params.add(pageSize * (pageNumber -1));
//		params.add(pageSize);
//		String excuteSql = sql + "  limit ?, ?";
//		Connection conn = JdbcUtil.getConnection();
//		ResultSet rs = JdbcUtil.query(JdbcUtil.getConnection(), excuteSql, params.toArray());
//
//		List<Object> list = new ArrayList<Object>();
//		if(rs!=null){
//			while (rs.next()) {
//				String job_name = rs.getString("job_name");
//				String job_group = rs.getString("job_group");
//				Map<String,String> jobData = (Map<String,String>) getObjectFromBlob(rs, "job_data");
//				String job_data = JSON.toJSONString(jobData);
//				String description = rs.getString("description");
//				String cron_expression = rs.getString("CRON_EXPRESSION");
//				String trigger_name = rs.getString("TRIGGER_NAME");
//				String trigger_group = rs.getString("TRIGGER_GROUP");
//				String next_fire_time = rs.getString("NEXT_FIRE_TIME");
//				String prev_fire_time = rs.getString("PREV_FIRE_TIME");
//				String priority = rs.getString("PRIORITY");
//				String trigger_state = rs.getString("TRIGGER_STATE");
//				String trigger_type = rs.getString("TRIGGER_TYPE");
//				String start_time = rs.getString("START_TIME");
//				String end_time = rs.getString("END_TIME");
//				
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("id", "1");
//				map.put("job_name", job_name);
//				map.put("job_group", job_group);
//				map.put("job_data", job_data);
//				map.put("description", description);
//				map.put("cron_expression", cron_expression);
//				map.put("trigger_name", trigger_name);
//				map.put("trigger_group", trigger_group);
//				map.put("next_fire_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(next_fire_time))));
//				map.put("prev_fire_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(prev_fire_time))));
//				map.put("priority", priority);
//				map.put("trigger_state", trigger_state);
//				map.put("trigger_type", trigger_type);
//				map.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(start_time))));
//				map.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(end_time))));
//				list.add(map);
//			}
//			rs.close();
//		}
//		conn.close();
//
//		return list;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static List<Object> queryJobInfoByTrigger(String triggerGroup, String tiggerName) throws Exception {
//		Connection conn = JdbcUtil.getConnection();
//		String sql = query_sql;
//		sql +=" and b.TRIGGER_GROUP=? AND b.TRIGGER_NAME=? ";
//		ResultSet rs = JdbcUtil.query(JdbcUtil.getConnection(), sql, triggerGroup, tiggerName);
//
//		List<Object> list = new ArrayList<Object>();
//		while (rs.next()) {
//			String job_name = rs.getString("job_name");
//			String job_group = rs.getString("job_group");
//			String cron_expression = rs.getString("CRON_EXPRESSION");
//			Map<String,String> jobData = (Map<String,String>) getObjectFromBlob(rs, "job_data");
//			String job_data = JSON.toJSONString(jobData);
//			String description = rs.getString("description");
//			String trigger_name = rs.getString("TRIGGER_NAME");
//			String trigger_group = rs.getString("TRIGGER_GROUP");
//			String next_fire_time = rs.getString("NEXT_FIRE_TIME");
//			String prev_fire_time = rs.getString("PREV_FIRE_TIME");
//			String priority = rs.getString("PRIORITY");
//			String trigger_state = rs.getString("TRIGGER_STATE");
//			String trigger_type = rs.getString("TRIGGER_TYPE");
//			String start_time = rs.getString("START_TIME");
//			String end_time = rs.getString("END_TIME");
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("job_name", job_name);
//			map.put("job_group", job_group);
//			map.put("cron_expression", cron_expression);
//			map.put("job_data", JSON.parse(job_data));
//			map.put("description", description);
//			map.put("trigger_name", trigger_name);
//			map.put("trigger_group", trigger_group);
//			map.put("next_fire_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(next_fire_time))));
//			map.put("prev_fire_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(prev_fire_time))));
//			map.put("priority", priority);
//			map.put("trigger_state", trigger_state);
//			map.put("trigger_type", trigger_type);
//			map.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(start_time))));
//			map.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(end_time))));
//			list.add(map);
//		}
//
//		rs.close();
//		conn.close();
//
//		return list;
//	}
//	
//	public static void updateTriggerCronExpression(String schedulerName, String triggerGroup, String tiggerName, String cronExpression) throws Exception {
//		Object[] params = new Object[]{cronExpression, schedulerName, triggerGroup, tiggerName};
//		JdbcUtil.update(update_trigger_cron, params);
//	}
//	
//	protected static Object getObjectFromBlob(ResultSet rs, String colName)
//	        throws ClassNotFoundException, IOException, SQLException {
//	        Object obj = null;
//
//	        Blob blobLocator = rs.getBlob(colName);
//	        if (blobLocator != null && blobLocator.length() != 0) {
//	            InputStream binaryInput = blobLocator.getBinaryStream();
//
//	            if (null != binaryInput) {
//	                if (binaryInput instanceof ByteArrayInputStream
//	                    && ((ByteArrayInputStream) binaryInput).available() == 0 ) {
//	                    //do nothing
//	                } else {
//	                    ObjectInputStream in = new ObjectInputStream(binaryInput);
//	                    try {
//	                        obj = in.readObject();
//	                    } finally {
//	                        in.close();
//	                    }
//	                }
//	            }
//
//	        }
//	        return obj;
//	    }
//}
