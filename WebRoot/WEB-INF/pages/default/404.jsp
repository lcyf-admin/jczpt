<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>"/>
  <%@ include file="/js/include/common.inc" %>
</head>
<script>
	window.location.href = "<%=basePath%>"
</script>
<body >
<div class="alert alert-danger">
访问错误，未找到页面！
</div>
</BODY>
