<%@page import="cn.lhkj.commons.util.StringUtil"%>
<%@page import="cn.lhkj.project.person.entity.Person"%>
<%@page import="cn.lhkj.project.person.entity.CheckPersonContraband"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Person person = (Person)request.getAttribute("person");
	CheckPersonContraband checkPersonContraband = (CheckPersonContraband)request.getAttribute("checkPersonContraband");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="x-hidden" > 
<head>
  <title></title>
  <base href="<%=basePath%>" />
  <%@ include file = "/js/include/common.inc"%>
</head>
<body >
<div class="lk-panel">
  <h3 class="bg-info">数据门人员数据</h3>
  <table class="showTable">
    <tr class="danger">
      <th>姓名</th>
      <td>${person.names}</td>
      <td rowspan="5" style="width: 204px;">
      	<%if(!StringUtil.isNull(person.getLocalImgUrl())){%>
      		<img height="250px" width="200px" onerror="this.src='images/defaut.jpg'" src="<%=person.getLocalImgUrl()%>">
      	<%}else{ %>
      		<img height="250px" width="200px" onerror="this.src='images/defaut.jpg'" src="<%=person.getImgUrl()%>">
      	<%} %>
      </td>
    </tr>
    <tr>
      <th>性别</th>
      <td>${person.gender}</td>
    </tr>
    <tr class="danger">
      <th>身份证号</th>
      <td>${person.idcard}</td>
    </tr>
    <tr>
      <th>年龄</th>
      <td>${person.age}</td>
    </tr>
    <tr class="danger">
      <th>有效期</th>
      <td >${person.startDate} 至 ${person.endDate}</td>
    </tr>
    <tr class="danger">
      <th>时间</th>
      <td colspan="2">${person.captureTimeView}</td>
    </tr>
    <tr>
      <th>位置</th>
      <td colspan="2">${person.location}</td>
    </tr>
    <tr class="danger">
      <th>住址</th>
      <td colspan="2">${person.address}</td>
    </tr>
    <tr class="danger">
      <th>是否人证合一</th>
      <td colspan="2">${person.isAPerson}</td>
    </tr>
  </table>
</div>

<%if("0".equals(person.getIsCheck())){ %>
<div style="margin: 10px;">
  <button type="button" class="btn btn-info btn-lg btn-block" onclick="window.location.href='person_contraband.action?person.id=${person.id}'">太赫兹安检违禁物品核录</button>
</div>
<%}else{%>
<h3 class="bg-info">携带违禁品详情</h3>
<table class="showTable" style="margin-bottom: 50px;">
  <tr>
    <th>违禁品名称</th>
    <td>${checkPersonContraband.name}</td>
    <td rowspan="3" style="width: 204px;">
    	<%if(!StringUtil.isNull(checkPersonContraband.getContrabandPhotoUrl())){%>
    		<img height="153px" width="200px" onerror="this.src='images/defaut.jpg'" src="<%=checkPersonContraband.getContrabandPhotoUrl()%>">
    	<%}else{ %>
    		<img height="153px" width="200px" onerror="this.src='images/defaut.jpg'" src="images/defaut.jpg">
    	<%} %>
    </td>

  </tr>
  
  <tr >
    <th>备注</th>
    <td>${checkPersonContraband.remark}</td>
  </tr>  
  
  <tr >
    <th>采集时间</th>
    <td>${checkPersonContraband.checkTimeView}</td>
  </tr>  
</table>	
<%}%>

<div></div>
</body>
<style>
.lk-panel{
	border-radius:10px;
	padding: 0;
	margin: 5px;
}
h3{
  margin: 0;
  padding: 10px 15px;
  border-top: 1px solid #ccc;
  border-left: 1px solid #ccc;
  border-right: 1px solid #ccc;
}
.danger { background-color: #F9F9F9; }
.showTable   { width: 100%;border-collapse:collapse;font-size: 1.4em}
.showTable th{ height: 50px;line-height:50px; color:blue; text-align: right;padding-right: 20px; width: 210px; border: 1px solid #ccc; }
.showTable td{ height: 50px;line-height:50px; text-align: left;padding-left: 20px;border: 1px solid #ccc; }
</style>
</html>