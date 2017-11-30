$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
	
    //2.初始化Button的点击事件
    //var oButtonInit = new ButtonInit();
    //oButtonInit.Init();
    
    // 优化样式
    $('.dropdown-toggle').find('.caret').remove();
});

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#aa').bootstrapTable({
        	height: getHeight(),               //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            url: 'list',         //请求后台的URL（*）
            method: 'post',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParamsType: '',
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false
            //是否显示父子表
        });
    };
    
    // 得到查询的参数
    oTableInit.queryParams = function (pageReqeust) {
    	var form = document.forms.namedItem("queryForm");
    	var string = $(form).serialize();
    	var jsonArr = $(form).serializeArray();
    	for(var i in jsonArr){
    		pageReqeust[jsonArr[i].name] = jsonArr[i].value;
    	}
    	console.log(pageReqeust);
        return pageReqeust;
    };
    
    oTableInit.query = function(){
    	$('#aa').bootstrapTable('refresh', {});
    }
    
    function getHeight() {
        return $(window).height() - $('h1').outerHeight(true);
    }
    
    return oTableInit;
};

function query(){
	var oTable = new TableInit();
	oTable.query();
}

function actionFormatter(value,row,index){
	var action = '';
	var jobName = row.jobName;
	var triggerGroup = row.triggerGroup;
	if(row.triggerState=='ACQUIRED' || row.triggerState=='WAITING'){
		action += "<input type='button' value='停止' class='btn btn-warning btn-xs' onclick=\"btn_click(this,'"+ jobName +"','"+ triggerGroup +"','pause')\"/>";
	} else {
		action += "<input type='button' value='启用' class='btn btn-success btn-xs' onclick=\"btn_click(this,'"+ jobName +"','"+ triggerGroup +"','resume')\"/>";
		action += "<input type='button' value='删除' class='btn btn-danger btn-xs' style='margin-left:2px;' onclick=\"forward(this,'"+ jobName +"','"+ triggerGroup +"','unschedule')\"/>";
		action += "<input type='button' value='修改' class='btn btn-primary btn-xs' style='margin-left:2px;' onclick=\"forward(this,'"+ jobName +"','"+ triggerGroup +"','update')\"/>";
	}
	action += "<input type='button' value='立即执行' class='btn btn-default btn-xs' style='margin-left:2px;' onclick=\"btn_click(this,'" + jobName +"','"+ triggerGroup +"','execute',1)\"/>";
	
	return action;
}

function noFormatter(value,row,index){
	 return index+1;
}

function runningStatusFormatter(value,row,index){
	if(row.triggerState=='ACQUIRED' || row.triggerState=='WAITING'){
		return '<image src="../resources/images/run.png" width="30" height="30" >';
	} else {
		return '<image src="../resources/images/stop.png" width="30" height="30" >';
	}
}

function urlFormatter(value,row,index){
	console.log(row.jobData);
	if(row.jobData && row.jobData!='null'){
		return JSON.parse(row.jobData).url;
	}
	return '';
}

function methodFormatter(value,row,index){
	if(row.jobData && row.jobData!='null'){
		return JSON.parse(row.jobData).method;
	}
	return '';
}

function btn_click(self,trigger,group,methodName,isDisabled){
	if(!isDisabled){
		$(self).attr('disabled',true);
		if("暂停"==$(self).val()){
			$(self).next().attr('disabled',false);
		}else if("恢复"==$(self).val()){
			$(self).prev().attr('disabled',false);
		}
	}
	$.get(methodName + "?flag=1&jobName="+trigger+"&groupName="+group,{rnd:Math.random()},function(data){
		if(data == 'success'){
			query();
		}
	});
}

function forward(self,trigger,group,methodName){
//	$(self).attr('disabled',true);
//	if("暂停"==$(self).val()){
//		$(self).next().attr('disabled',false);
//	}else if("恢复"==$(self).val()){
//		$(self).prev().attr('disabled',false);
//	}
	
//	location.href = 'http://www.baidu.com';
	location.href = methodName+'?flag=1&jobName='+trigger+'&groupName='+group;
}



var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};