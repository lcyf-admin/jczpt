<%@page import="cn.lhkj.commons.base.BaseDataCode"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<html>
<head>
  <base href="<%=basePath%>"/>
  <%@ include file = "/js/include/common.inc"%>
</head>
<script>
$(document).ready(function(){//
	
});
</script>
<style>
.viewTable{ width: 100%;border-collapse:collapse;}
.viewTable th{ height: 40px;text-align: center;font-size: 1.5em;}
.viewTable td{ height: 50px;text-align: center;font-size: 1.2em;}

</style>
<body style="overflow: hidden; min-height: 700px;">
<h2 class="sub-header" >欢迎使用公安边检站综合管理系统！</h2>
<fieldset style="margin-top: 20px;">
<legend>系统参数配置</legend>
<table class="viewTable bg-info" >
  <tr><th>大数据比对接口地址</th><th>特检通道比对地址</th><tr>
  <tr><td><%=BaseDataCode.config.getComparBigDataURL()%></td><td><%=BaseDataCode.config.getComparSpecialURL()%></td><tr>
  <tr><th>数据门请求数据轮询时间</th><th>请求合法IP</th><tr>
  <tr><td><%=BaseDataCode.config.getDataDoorPostSleep()%>（毫秒）</td><td><%=BaseDataCode.config.getLegalIds()%></td><tr>
  <tr><th>前置卡口通道编号</th><th></th><tr>
  <tr><td><%=BaseDataCode.config.getFrontId()%></td><td></td><tr>
  <tr><td colspan="4">
    <!--  input type="button" onclick="window.open('home_edit.action','I3')" class="btn btn-info btn-lg btn-block" value="修改"-->
  </td></tr>
</table>
</fieldset>
</body>
</html>
