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
      <a class="navbar-brand" >接口测试页面</a>
    </div>
  </div>
</nav>

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-2 lk-sidebar">
      <ul class="nav nav-sidebar nav-menus">
		<li url="test_compar.action"><a>三汇比对接口</a></li>
        <li url="test_datadoor.action"><a>品恩数据门接口</a></li>
      </ul>
    </div>
    <div class="col-sm-10 col-sm-offset-2 main">
      <h1 class="page-header" id="page-header">三汇比对接口</h1>
      <div id="content-main" style="margin: 0;padding: 0;">
	    <iframe src="test_compar.action" id="I3" name="I3" scrolling="auto" width="100%" height="800px;" frameborder="0"></iframe>
	  </div>
    </div>
  </div>
</div>
</body>
</html>
