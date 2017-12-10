<%@page import="cn.lhkj.commons.util.StringUtil"%>
<%@page import="cn.lhkj.project.check.entity.CheckPersonPeers"%>
<%@page import="cn.lhkj.project.check.entity.CheckPersonPeersVehicle"%>
<%@page import="java.util.List"%>
<%@page import="cn.lhkj.project.check.entity.CheckPerson"%>
<%@page import="cn.lhkj.project.contrast.entity.ContrastPerson"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	ContrastPerson contrastPerson = (ContrastPerson)request.getAttribute("contrastPerson");
	CheckPerson checkPerson = (CheckPerson)request.getAttribute("checkPerson");
	List<CheckPersonPeers> peersList = (List<CheckPersonPeers>)request.getAttribute("peersList");
	List<CheckPersonPeersVehicle> peersVehicleList = (List<CheckPersonPeersVehicle>)request.getAttribute("peersVehicleList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="x-hidden" > 
<head>
<title></title>
  <base href="<%=basePath%>" />
  <%@ include file = "/js/include/common.inc"%>
<script type="text/javascript">

	
</script>
</head>
<body>
<div class="lk-panel">
  <h3 class="bg-info">预警人员详细信息</h3>
  <table class="showTable">
    <tr >
      <th style="width: 250px;">姓名</th>
      <td >${contrastPerson.names}</td>
      <td rowspan="5" style="padding: 0;" width="203px;" valign="top"><img height="250px" width="203px" 
        src="${contrastPerson.gatherPhotoURL}"
	    onerror="this.src='images/defaut.jpg'" >
      </td>
    </tr>
    <tr class="danger">
      <th>身份证号</th>
      <td >${contrastPerson.idcard}</td>
    </tr>
    <tr>
      <th>身份地址</th>
      <td >${contrastPerson.address}</td>
    </tr>
    <tr class="danger">
      <th>性别</th>
      <td >${contrastPerson.gender}</td>
    </tr>
    <tr>
      <th>出生年月</th>
      <td >${contrastPerson.birth}</td>
    </tr>
    <tr class="danger">
      <th>采集时间</th>
      <td colspan="2">${contrastPerson.captureTimeView}</td>
    </tr>
     <tr >
      <th>发现位置</th>
      <td colspan="2">${contrastPerson.location}</td>
    </tr>
    <tr class="danger">
      <th>标签</th>
      <td colspan="2">${contrastPerson.tag}</td>
    </tr>
    <tr >
      <th>处置措施</th>
      <td colspan="2">${contrastPerson.action}</td>
    </tr>
  </table>
</div>
<%if("0".equals(contrastPerson.getIsChecked())){ %>
<div style="margin: 10px;">
  <button type="button" class="btn btn-info btn-lg btn-block" onclick="window.location.href='contrastPerson_check.action?contrastPerson.id=${contrastPerson.id}'">核&nbsp;&nbsp;录</button>
</div>
<%}else{ %>
<h3 class="bg-info">核录结果</h3>
<table class="showTable" border="1" style="margin-bottom: 30px;">
  <tr>
    <th>是否预警对象：</th>
    <td>${checkPerson.isContrastView}</td>
  </tr>
<%if("1".equals(checkPerson.getIsContrast())){ %> 
	<tr>
      <th>预警类别：</th>
      <td>${checkPerson.yjType}</td>
    </tr>
    <tr>
      <th>处置措施：</th>
      <td>${checkPerson.action}</td>
    </tr>
  <%if("信息采集".equals(checkPerson.getAction())){ %>  
    <tr>
	  <th>人员检查：</th>
	  <td style="padding: 5px;">
	      <table border="0" width="100%" class="ctable">
	        <tr>
	          <th class="cth">身份证号：</th>
	          <td class="ctd">${checkPerson.idcard}</td>
	          <th class="cth">手机号：</th>
	          <td class="ctd">${checkPerson.phonenum}</td>
	          <th class="cth">人证核验结果：</th>
	          <td class="ctd">${checkPerson.matchView}</td>
	        </tr>
	        <tr>
	          <th class="cth">手机检查：</th>
	          <td class="ctd">${checkPerson.isDubiousView}</td>
	          <th class="cth">可疑描述：</th>
	          <td class="ctd" colspan="3">${checkPerson.finds}</td>
	        </tr>
	      </table>
	  </td>
    </tr>
    <tr>
	    <th>同行人：</th>
	    <td style="padding: 5px;">
	    <%for(CheckPersonPeers peers : peersList){ %>
	      <table border="0" width="100%" class="ctable">
	        <tr>
	          <th class="cth">身份证号：</th>
	          <td class="ctd"><%=StringUtil.trim(peers.getIdcard())%></td>
	          <th class="cth">手机号：</th>
	          <td class="ctd"><%=StringUtil.trim(peers.getPhonenum())%></td>
	        </tr>
	      </table>
	    <%} %>
	    </td>
	</tr>
	    <tr>
	    <th>同行车：</th>
	    <td style="padding: 5px;">
	    <%for(CheckPersonPeersVehicle vehicle : peersVehicleList){ %>
	      <table border="0" width="100%" class="ctable">
	        <tr>
	          <th class="cth">车牌号：</th>
	          <td class="ctd"><%=StringUtil.trim(vehicle.getCarNum())%></td>
	          <th class="cth">车牌颜色：</th>
	          <td class="ctd"><%=StringUtil.trim(vehicle.getPlateColor())%></td>
	        </tr>
	        <tr>
	          <th class="cth">车身颜色：</th>
	          <td class="ctd"><%=StringUtil.trim(vehicle.getVehicleColor())%></td>
	          <th class="cth">车辆类型：</th>
	          <td class="ctd"><%=StringUtil.trim(vehicle.getVehicleType())%></td>
	        </tr>
	      </table>
	    <%} %>
	    </td>
	</tr>
  <%}else if("滞留审查".equals(checkPerson.getAction())){ %>  
    <tr>
      <th>是否请假：</th>
	  <td>${checkPerson.isVacationView}</td>
    </tr>
    <tr>
      <th>人员去向：</th>
	  <td>${checkPerson.direction}</td>
    </tr>
    <tr>
      <th>目的地来由：</th>
	  <td>${checkPerson.directionReason}</td>
    </tr>
    <tr>
      <th>管辖片区民警联系方式：</th>
	  <td>${checkPerson.copnum}</td>
    </tr>
    <tr>
      <th>前往内地原因：</th>
      <td>${checkPerson.reason}</td>
    </tr>
    <tr>
      <th>返回时间：</th>
      <td>${checkPerson.backtimeView}</td>
    </tr>
    <tr>
      <th>近一年是否离开：</th>
      <td>${checkPerson.isLeaveView}</td>
    </tr>
<%}}%>
    <tr>
	  <th>核查结论：</th>
      <td>${checkPerson.hcjlView}</td>
	</tr>
</table>
<%} %>
	
</body>
<style>
.lk-panel{
	border-radius:10px;
	padding: 0;
	margin: 5px;
}
h3{
  margin: 0;
  margin-top: 10px;
  padding: 10px 15px;
  border-top: 1px solid #ccc;
  border-left: 1px solid #ccc;
  border-right: 1px solid #ccc;
}
.danger { background-color: #F9F9F9; }
.showTable   { width: 100%;border-collapse:collapse;font-size: 1.4em}
.showTable th{ height: 50px;line-height:50px; color:blue; text-align: right;padding-right: 20px; width: 250px; border: 1px solid #ccc; }
.showTable td{ height: 50px;line-height:50px; text-align: left;padding-left: 20px;border: 1px solid #ccc;}

.ctable      { width: 100%;border: 1px solid #ccc;margin-bottom: 5px;}
.cth         { height: 35px !important; line-height:35px !important;width: 120px !important;padding-right: 0px !important;font-size: 16px !important; border: 0 !important;}
.ctd		 { height: 35px !important; line-height:35px !important;width: 228px !important;padding: 0 10px !important;border: 0 !important;}
</style>
</html>