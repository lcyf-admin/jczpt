<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>"/>
  <%@ include file = "/js/include/common.inc"%>
</head>
<script>
$(function(){
	var timer;//通过轮询来设置主页面iframe的显示高度
    $("#I3").load(function(){
        if(timer){
            clearInterval(timer);
        }
        //pre_height用于记录上次检查时body的高度
        //mainheight用于获取本次检查时body的高度，并赋予iframe的高度
        var mainheight,pre_height;
        var frame_content = $(this);
        timer = setInterval(function(){
            mainheight = frame_content.contents().find("body").height() + 30;
            if (mainheight != pre_height){
                pre_height = mainheight;
                frame_content.height(Math.max(mainheight,700));
             }
        },300);//每0.3秒检查一次
    });
	    
	
	window.open("home","I3");
	
	$("body").on("click",".nav-menus>li",function(e){
		e.stopPropagation();
		if($("#page-header").text() ==  $(this).text()) return;
		$(".nav>li").removeClass("active");
    	$(this).addClass("active");
    	$("#page-header").text($(this).text());
		var dataUrl = $(this).attr("url");
    	if (dataUrl == undefined || $.trim(dataUrl).length == 0) {
        	return false;
    	}
    	window.open(dataUrl,"I3");
	});
});
</script>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" >公安边检站综合管理平台<small>&nbsp;&nbsp;&nbsp;后台管理</small></a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav navbar-right">
      	<li><a href="javascript: index();">返回前台</a></li>
      	<li><a>修改密码</a></li>
        <li><a href="javascript: logout();">注销登录</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right lk-unhidden nav-menus">
      	<li url="pages/home/home.jsp"><a>首页 </a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-2 lk-sidebar">
      <ul class="nav nav-sidebar nav-menus">
        <li url="home" class="active"><a>首页 </a></li>
        <li url="orgzon_list" ><a>组织机构</a></li>
        <li url="user_list"><a>用户管理</a></li>
        <li url="role_list"><a>权限管理</a></li>
        <li url="dict_list"><a>数据字典</a></li>
        <li url="hall_list"><a>安检厅管理</a></li>
        <li url="lane_list"><a>车道管理</a></li>
        <li url="equipment_listHall"><a>安检厅设备</a></li>
        <li url="equipment_listLane"><a>车道设备</a></li>
      <!-- 
        <li url="deployPerson_list"><a>布控人员管理</a></li>
        <li url="deployVehicle_list"><a>布控车辆管理</a></li>
        <li url="whiteList_list"><a>白名单车辆管理</a></li> 
      -->
      </ul>
    </div>
    <div class="col-sm-10 col-sm-offset-2 main">
      <h1 class="page-header" id="page-header">首页</h1>
      <div id="content-main" style="margin: 0;padding: 0;">
	    <iframe id="I3" name="I3" scrolling="auto" width="100%" height="100%" frameborder="0" ></iframe>
	  </div>
    </div>
  </div>
</div>
</body>
</html>
