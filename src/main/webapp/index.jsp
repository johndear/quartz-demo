<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon" href="resources/images/favicon.ico?v=2.9.51" />
<title>定时任务调度平台</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<!-- 最新版本的 Bootstrap -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>


<style>
	body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,button,textarea,blockquote,p {marin:0;padding:0;border:none;}

</style>
</head>
<body>
<header class="navbar navbar-inverse navbar-static-top bs-docs-nav">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <div style="vertical-align: middle;height:100%;width:50px;float:left;"><img src="resources/images/clock.png"/></div> 
      <a class="navbar-brand" href="#">定时任务调度平台</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
      	<li><a href="quartz/list" target="right">任务列表</a></li>
      	<li><a href="quartz/add" target="right">添加任务</a></li>
        <!-- <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">任务管理 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="quartz/list" target="right">任务列表</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="quartz/add" target="right">添加任务</a></li>
          </ul>
        </li> -->
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</header>

	<iframe id="right" name="right" width="100%" height="100%" src="quartz/list" frameborder="0"></iframe>
</body>

<script>
var headerHeight = document.getElementById("bs-example-navbar-collapse-1").clientHeight;
var bodyHeight = document.documentElement.clientHeight - headerHeight - 25;
document.getElementById("right").style.height = bodyHeight + 'px';

</script>
</html>