<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加任务</title>
</head>
<body style="background-color: #fff;">
<form action="<%=path %>/quartz" method="get">
<table>

	<tr><td>任务分组： </td><td><input  type="text" name="groupName" value="${jobInfo.job_group}"/></td></tr>
	<tr><td>任务名称： </td><td><input  type="text" name="jobName" value="${jobInfo.job_name}"/></td></tr>
	<tr><td>任务类型： </td><td><input type="radio">有状态的任务 <input type="radio">无状态的任务</td></tr>
	<tr><td>优 先 级：</td><td><input type="text" name="triggerPriority" value="${jobInfo.PRIORITY}"/></td></tr>
	<tr><td>Cron表达</td><td><input type="text" name="cronExpression" value="${jobInfo.job_name}"/></td></tr>
	<tr><td>请求方式： </td><td><input  type="radio" name="method">get <input type="radio" name="method">post</td></tr>
	<tr><td>请求地址： </td><td><input  type="text" name="url" value="${jobInfo.job_name}"/></td></tr>
	<tr><td style="vertical-align: top;">备&nbsp;&nbsp;&nbsp;&nbsp;注：   </td><td><textarea cols="25" rows="5" value="${jobInfo.job_name}"></textarea></td></tr>
	<tr><td><input type="hidden" name="flag" value="7"/></td></tr>
	<tr>
		<td>
			<input type="submit" value="提交"/>&nbsp;
			<input type="button" class="btn btn-default" onclick="javascript:history.go(-1);" value="取消" />
		</td>
	</tr>
	</table>
</form>
</body>
</html>