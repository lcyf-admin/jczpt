<%@page import="cn.lhkj.commons.entity.SessionBean"%>
<%@page import="cn.lhkj.project.check.entity.CheckVehiclePassenger"%>
<%@page import="java.util.List"%>
<%@page import="cn.lhkj.project.check.entity.CheckVehicle"%>
<%@page import="cn.lhkj.commons.util.StringUtil"%>
<%@page import="cn.lhkj.project.vehicle.entity.Vehicle"%>
<%@page import="cn.lhkj.project.contrast.entity.ContrastVehicle"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	ContrastVehicle contrastVehicle = (ContrastVehicle)request.getAttribute("contrastVehicle");
	String vehiclePhoto = contrastVehicle.getGatherPhotoURL();
	if(StringUtil.isNull(vehiclePhoto)){
		Vehicle vehicle = (Vehicle)request.getAttribute("vehicle");
		vehiclePhoto = vehicle.getCarImg();
	}
	
	CheckVehicle checkVehicle = (CheckVehicle)request.getAttribute("checkVehicle");
	List<CheckVehiclePassenger> passengerList = (List<CheckVehiclePassenger>)request.getAttribute("passengerList");

	SessionBean user = (SessionBean)session.getAttribute("SESSION_BEAN");
	String stationId = StringUtil.trim(user.getStationId());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="x-hidden" > 
<head>
<title></title>
  <base href="<%=basePath%>" />
  <%@ include file = "/js/include/common.inc"%>
</head>
<body>
<div style="margin: 10px;">
  <button type="button" class="btn btn-primary btn-lg btn-block" onclick="window.open('vehicle_editDetail.action?id=${contrastVehicle.vehicleId}','vehicleEditDetail')">查看采集信息</button>
</div>
<div class="lk-panel">
  <h3 class="bg-info">预警车辆详细信息</h3>
  <table class="showTable">
    <tr >
      <th>车牌号</th>
      <td >${contrastVehicle.carNum}</td>
      <td rowspan="5" style="padding: 0;" width="375px;"><img height="250px" width="375px" 
          src="<%=vehiclePhoto%>" 
		  onerror="this.src='images/defautVehicle.jpg'" />
      </td>
    </tr>
    <tr class="danger">
      <th>品牌信息</th>
      <td >${contrastVehicle.vehicleType}</td>
    </tr>
    <tr >
      <th>车牌状态</th>
      <td >${contrastVehicle.label}</td>
    </tr>
    <tr class="danger">
      <th>采集时间</th>
      <td >${contrastVehicle.passdateView}</td>
    </tr>
    <tr >
      <th>发现位置</th>
      <td >${contrastVehicle.location}</td>
    </tr>
  </table>
     
  <table class="showTable">
    <tr class="danger">
      <th>车主姓名</th>
      <td>${contrastVehicle.names}</td>
      <td rowspan="5" style="padding: 0;" width="203px;" valign="top"><img height="250px" width="203px" 
        src="data:image/png;base64,<%=contrastVehicle.getPersonPhoto()%>"
	    onerror="this.src='images/defaut.jpg'" >
      </td>
    </tr>
    <tr >
      <th>车主身份证</th>
      <td >${contrastVehicle.idcard}</td>
    </tr>
    <tr class="danger">
      <th>身份证地址</th>
      <td >${contrastVehicle.address}</td>
    </tr>
    <tr >
      <th>标签</th>
      <td >${contrastVehicle.tag}</td>
    </tr>
    <tr class="danger">
      <th>处置措施</th>
      <td >${contrastVehicle.action}</td>
    </tr>
  </table>
</div>
<%if("0".equals(contrastVehicle.getIsChecked()) && !StringUtil.isNull(contrastVehicle.getVehicleId())){ %>
<div style="margin: 10px;">
  <button type="button" class="btn btn-info btn-lg btn-block" onclick="window.location.href='contrastVehicle_check.action?contrastVehicle.id=${contrastVehicle.id}'">核&nbsp;&nbsp;录</button>
</div>
<%}else{
	if("1".equals(contrastVehicle.getIsChecked()) && checkVehicle !=null){ %>
<h3 class="bg-info">核录结果</h3>
<table class="showTable" style="margin-bottom: 50px;">
  <tr >
    <th>预警描述</th>
    <td>${checkVehicle.yjType}</td>
  </tr>
  <tr class="danger">
    <th>车辆信息</th>
    <td style="padding: 5px;">
      <table border="0" width="100%" class="ctable">
        <tr>
          <th class="cth">车身颜色：</th>
          <td class="ctd">${checkVehicle.vehiclecolor}</td>
          <th class="cth">车牌颜色：</th>
          <td class="ctd">${checkVehicle.platecolor}</td>
          <th class="cth">车辆类型：</th>
          <td class="ctd">${checkVehicle.vehicletype}</td>
        </tr>
      </table>
    </td>
  </tr>
  
   <tr class="danger"> 
    <th>车辆信息</th>
    <td style="padding: 5px;">
      <table border="0" width="100%" class="ctable">
        <tr>
          <th class="cth">车辆性质：</th>
          <td class="ctd">${checkVehicle.vnatureView}</td>
          <th class="cth">车辆所属单位：</th>
          <td class="ctd">${checkVehicle.vunit}</td>
        </tr>
        <tr>
        <%if("1".equals(checkVehicle.getAcheck())){%>
          <th class="cth">是否再次核查：</th>
          <td class="ctd">是</td>  
          <th class="cth"></th>
          <td class="ctd"></td>   
        <%}else{%>
            <th class="cth">是否再次核查：</th>
            <td class="ctd">否</td>
	        <th class="cth">不再核查原因：</th>
	        <td class="ctd">${checkVehicle.akeasonView}</td>
	        <%if("6".equals(checkVehicle.getAkeason())){%>
	        <th class="cth">其他原因：</th>
	        <td class="ctd">${checkVehicle.acontent}</td>      
	        <% } %>
         <% } %>
        </tr>
      </table>
    </td>
  </tr>
  
  <%if(stationId.indexOf("653101") != -1){ %>
  <tr class="danger">
    <th>车辆信息</th>
    <td style="padding: 5px;">
      <table border="0" width="100%" class="ctable">
        <tr>
          <th class="cth">车辆去向：</th>
          <td class="ctd">${checkVehicle.direction}</td>
          <th class="cth">目的地：</th>
          <td class="ctd">${checkVehicle.destination}</td>
          <th class="cth">滞留时间：</th>
          <td class="ctd">${checkVehicle.residenceTime}</td>
        </tr>
        <tr>
          <th class="cth">办事是由：</th>
          <td class="ctd">${checkVehicle.reason}</td>
          <th class="cth">核录人：</th>
          <td class="ctd">${checkVehicle.enterer}</td>
          <th class="cth"></th>
          <td class="ctd"></td>
        </tr>
      </table>
    </td>
  </tr>
  <%} %>
  <tr class="danger">
    <th>驾驶员信息</th>
    <td style="padding: 5px;">
      <table border="0" width="100%" class="ctable">
        <tr>
          <th class="cth">姓名：</th>
          <td class="ctd">${driver.name}</td>
          <th class="cth">身份证号：</th>
          <td class="ctd">${driver.idcard}</td>
        </tr>
        <tr>
          <th class="cth">手机号：</th>
          <td class="ctd">${driver.phonenum}</td>
          <th class="cth">人证核验结果：</th>
          <td class="ctd">${driver.matchView}</td>
        </tr>
        <tr>
          <th class="cth">手机检查：</th>
          <td class="ctd">${driver.isDubiousView}</td>
          <th class="cth">可疑描述：</th>
          <td class="ctd" colspan="3">${driver.finds}</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <th>乘客信息：</th>
    <td style="padding: 5px;">
    <%for(CheckVehiclePassenger passenger : passengerList){ %>
      <table border="0" width="100%" class="ctable">
        <tr>
          <th class="cth">身份证号：</th>
          <td class="ctd"><%=StringUtil.trim(passenger.getIdcard())%></td>
          <th class="cth">手机号：</th>
          <td class="ctd"><%=StringUtil.trim(passenger.getPhonenum())%></td>
          <th class="cth">人证核验结果：</th>
          <td class="ctd"><%=StringUtil.trim(passenger.getMatchView())%></td>
        </tr>
        <tr>
          <th class="cth">手机检查：</th>
          <td class="ctd"><%=StringUtil.trim(passenger.getIsDubiousView())%></td>
          <th class="cth">可疑描述：</th>
          <td class="ctd" colspan="3"><%=StringUtil.trim(passenger.getFinds())%></td>
        </tr>
      </table>
    <%} %>
    </td>
  </tr>
  <tr class="danger">
    <th>违禁物品：</th>
    <td >${checkVehicle.forbids}</td>
  </tr>
<%if("二手车".equals(checkVehicle.getYjType())){ %>  
  <tr>
    <th>驾驶人员与车辆登记人是否一致：</th>
    <td >${checkVehicle.relationsView}</td>
  </tr>
<%}%>

</table>
<%}} %>
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
.showTable   { width: 100%;border-collapse:collapse;font-size: 1.4em;margin-bottom:5px;}
.showTable th{ height: 50px;line-height:35px; color:blue; text-align: right;padding-right: 15px; width: 220px; border: 1px solid #ccc; }
.showTable td{ height: 50px;line-height:35px; text-align: left;padding-left: 20px;border: 1px solid #ccc; }

.ctable      { width: 100%;border: 1px solid #ccc;margin-bottom: 5px;}
.cth         { height: 35px !important; line-height:35px !important;width: 120px !important;padding-right: 0px !important;font-size: 16px !important; border: 0 !important;}
.ctd		 { height: 35px !important; line-height:35px !important;width: 228px !important;padding: 0 10px !important;border: 0 !important;}
</style>
</html>