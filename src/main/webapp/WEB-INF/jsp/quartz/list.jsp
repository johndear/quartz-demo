<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
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
<title>任务列表</title>
<style>
	.datalist{
		font-size:14px;
	}
	table{ 
		font-size:12px;
	}
	
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<!-- 最新版本的 Bootstrap -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<!-- 最新版本的 bootstrap-table -->
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/bootstrap-table.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>

<script type="text/javascript" src="<%=basePath%>js/list.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>js/comet4j.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.2.6.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.messager.js"></script>
 --%>
<script>
	<!-- comet4j javascript -->
	//对应comet4j channel的名称
	//$(function(){
	//    JS.Engine.on({
	//        hello: function(text){  
	//	        $('#bgsound').html("<bgsound src=\"resources/musics/nglmy.mp3\" loop=\"2\" />");
	//    		$('#content').html(text);
	//		    $.messager.show('<font color=red>消息提醒</font>', '<font color=green style="font-size:14px;font-weight:bold;">' + text + '</font>');
	//		}
	//    });
	// 	JS.Engine.start('conn');
	// });

	
	
	</script>
</head>
<body>
<div style="padding-left:2px;padding-right:2px;">
	<div id="toolbar" class="btn-group">
           <form name="queryForm" class="form-inline" style="padding-left:10px;">
			<div class="form-group">
				<label for="exampleInputPassword1">任务组：</label>
				<input type="text" name="groupName" class="form-control" placeholder="支持模糊匹配" aria-describedby="basic-addon1">
			</div>&nbsp;&nbsp;&nbsp;&nbsp;
			<div class="form-group">
				<label for="exampleInputPassword1">任务名称:</label>
				<input type="text" name="jobName" class="form-control" placeholder="支持模糊匹配" aria-describedby="basic-addon1">
			</div>&nbsp;&nbsp;&nbsp;&nbsp;
			<div class="form-group">
				<button type="button" class="btn btn-success" onclick="query()">查询</button>
			</div>
		</form>
    </div>
       
	<table id="aa">
		<thead style="background-color:#F8E9DB;">
			<tr>
				<th data-field="id" data-formatter="noFormatter">序号</th>
				<th data-field="triggerState" data-formatter="runningStatusFormatter">状态</th>
				<th data-field="jobName">任务名</th>
				<th data-field="jobGroup">任务所在组</th>
				<th data-field="triggerType" data-visible="false">触发器类型</th>
				<th data-field="cronExpression">cron表达式</th>
				<th data-field="triggerGroup" data-visible="false">触发器组</th>
				<th data-field="triggerName" data-visible="false">触发器名</th>
				<th data-field="jobData" data-formatter="urlFormatter">请求url</th>
				<th data-field="jobData" data-formatter="methodFormatter">请求方式</th>
				<th data-field="priority">优先级</th>
				<th data-field="prevFireTime">上次执行时间</th>
				<th data-field="nextFireTime">下次执行时间</th>
				<th data-field="startTime">开始时间</th>
				<th data-field="endTime">结束时间</th>
				<th data-field="description">描述</th>
				<th data-field="id" data-formatter="actionFormatter">操作</th>
			</tr>
		</thead>
	</table>
	
</div>
	
</body>
</html>