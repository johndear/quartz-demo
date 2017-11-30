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
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body class="center-block">
<form class="form-horizontal" action="<%=path %>/quartz/add" method="POST" enctype="application/x-www-form-urlencoded" style="width:80%;">
  <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">任务分组 <font color="red">*</font>：</label>(系统或者模块名)
    <div class="col-sm-5">
      <input type="text" class="form-control" name="groupName" placeholder="分组code" value="${jobInfo.jobGroup}" ${jobInfo.jobGroup!=null? "readonly":""} required>
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">任务名称 <font color="red">*</font>：</label>(方法名，<font color="red">注：任务分组+任务名称必须唯一</font>）
    <div class="col-sm-5">
      <input type="text" class="form-control" name="jobName" placeholder="名称code" value="${jobInfo.jobName}" ${jobInfo.jobName!=null? "readonly":""} required>
    </div>
  </div>
  <div class="form-group" style="display:none;">
    <label for="inputPassword3" class="col-sm-2 control-label">任务类型：</label>(控制是否允许2个trigger同时执行同一个job)
    <div class="col-sm-5">
    	<input type="radio" name="status" value="1">有状态的任务 <input type="radio" name="status" value="0" checked>无状态的任务
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">优 先 级：</label>(同时执行时有效，数值越大优先级越高，默认是5)
    <div class="col-sm-5">
      <input type="text" class="form-control" name="triggerPriority" placeholder="优 先 级" value="${jobInfo.priority == null ? 5 : jobInfo.priority}">
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Cron表达：</label>(例如：0/30 * * * * ?)
    <div class="col-sm-5">
      <input type="text" class="form-control" name="cronExpression" placeholder="Cron表达" value="${jobInfo.cronExpression==null ? '0/30 * * * * ?' : jobInfo.cronExpression}">
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">请求方式：</label>
    <div class="col-sm-5">
      <input  type="radio" name="method" value="get" ${(method=='get' || jobInfo==null) ? 'checked':''}>get <input type="radio" name="method" value="post" ${method=='post' ? 'checked':''}>post
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">请求地址<font color="red">*</font>： </label>
    <div class="col-sm-5">
      <input type="text" class="form-control" name="url" placeholder="请求地址" value="${url}" required>
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">备&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
    <div class="col-sm-5">
    	<textarea name="description" cols="65" rows="5">${jobInfo.description}</textarea>
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <input type="hidden" name="flag" value="1"/>
      <button type="submit" class="btn btn-success">提交</button>
      <input type="button" class="btn btn-default" onclick="javascript:history.go(-1);" value="取消" />
    </div>
  </div>
</form>

</body>
</html>