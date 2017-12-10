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
<body >
<div class="alert alert-danger" >
服务器错误！如果您在使用本系统时，出现此错误，请与管理员联系！
</div>
</BODY>
