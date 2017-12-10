<%@page import="cn.lhkj.commons.util.StringUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cn.lhkj.project.vehicle.entity.Passenger"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	List<Passenger> passengerList = (List<Passenger>)request.getAttribute("passengerList");
	if(passengerList == null){
		passengerList = new ArrayList<Passenger>();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="x-hidden" > 
<head>
  <title></title>
  <base href="<%=basePath%>" />
  <%@ include file = "/js/include/common.inc"%>
  <%@ include file = "/js/include/ymPrompt.inc"%>
</head>
<script>
  /**录入乘客信息 @*/
  function addPassenger(id){
    ymPrompt.win({
      message : "vehicle_addPassenger.action?id="+id,
      width : 1050,
      height : 550,
      title : "录入乘客信息",
      maxBtn : false,
      minBtn : false,
      closeBtn : true,
      iframe : true
    });
  }
  
  function deletePassenger(id){
  	if(window.confirm("确认删除？")){
  		$.ajax({
   			type: "POST",
   			url: "vehicle_ajaxDeletePassenger.action?id="+id,
   			async: false,
   			success:function(data){
			    window.location.reload();
   			}
   		});
  	}
  }
</script>
<body style="border-radius:10px;padding: 0;	margin: 5px 360px 5px 360px;width: 1200px;" >
<div align="center">
<div style="margin: 10px 0 10px 0;">
  <button type="button" class="btn btn-primary btn-lg btn-block" onclick="addPassenger('${vehicle.id}')">录入乘客信息</button>
</div>
<div >
  <h3 class="bg-info">车辆信息</h3>
  <div class="imgDivCar">
    <img height="250px" width="375px" src="${vehicle.carImg}" onerror="this.src='images/defautVehicle.jpg'" >
  	<img height="250px" src="${vehicle.cardImg}" onerror="this.src='images/defaut.jpg'" style="max-width: 375px;" />
  </div>
  <div style="margin-right: 377px;">
    <table class="showTable">
      <tr >
        <th style="width: 100px;">车牌号</th>
	    <td>${vehicle.carNum}</td>
	  </tr>
	  <tr class="danger">
	    <th>采集时间</th>
	    <td>${vehicle.passdateView}</td>
	  </tr>
	  <tr >
	    <th>车牌颜色</th>
	    <td >${vehicle.plateColor}</td>
	  </tr>
	  <tr class="danger">
	    <th>车辆类型</th>
	    <td >${vehicle.vehicleType}</td>
	  </tr>
	  <tr >
	    <th >位置</th>
	    <td >${lane.laneName}</td>
	  </tr>
      <tr class="danger">
        <th >司机姓名</th>
        <td >${vehicle.userName}</td>
	  </tr>
	  <tr >
	    <th >司机身份证号</th>
	    <td >${vehicle.cardNum}</td>
	  </tr>
	  <tr class="danger">
	    <th>司机性别</th>
	    <td>${vehicle.sex}</td>
	  </tr>
	  <tr >
	    <th>民族</th>
	    <td>${vehicle.minzu}</td>
	  </tr>
	  <tr class="danger">
	    <th >签发机关</th>
	    <td >${vehicle.qianfa}</td>
	  </tr>
	  <tr>
	    <th >有效期</th>
	    <td >${vehicle.youxiaoqi}</td>
	  </tr>
	  <tr class="danger">
	    <th >住址</th>
	    <td >${vehicle.address}</td>
	  </tr>
    </table>
  </div>
</div>
<%for(Passenger t : passengerList){%>
<div> 
  <h3 class="bg-info">乘客信息</h3>
  <div class="imgDivPerson">
    <img height="250px"src="<%=StringUtil.trim(t.getCardImg())%>" onerror="this.src='images/defaut.jpg'" style="max-width: 375px;" />
    <button style="margin-top: 20px;" type="button" class="btn btn-danger btn-lg btn-block" onclick="deletePassenger('<%=StringUtil.trim(t.getId())%>')">删&nbsp;&nbsp;除</button>
  </div>
  <div style="margin-right: 377px;">
    <table class="showTable">
      <tr >
        <th >姓名</th>
        <td ><%=StringUtil.trim(t.getUserName())%></td>
	  </tr>
	  <tr class="danger">
	    <th >身份证号</th>
	    <td ><%=StringUtil.trim(t.getCardNum())%></td>
	  </tr>
	  <tr >
	    <th>性别</th>
	    <td><%=StringUtil.trim(t.getSex())%></td>
	  </tr>
	  <tr class="danger">
	    <th>民族</th>
	    <td><%=StringUtil.trim(t.getMinzu())%></td>
	  </tr>
	  <tr >
	    <th >签发机关</th>
	    <td ><%=StringUtil.trim(t.getQianfa())%></td>
	  </tr>
	  <tr class="danger">
	    <th >有效期</th>
	    <td ><%=StringUtil.trim(t.getYouxiaoqi())%></td>
	  </tr>
	  <tr >
	    <th >住址</th>
	    <td ><%=StringUtil.trim(t.getAddress())%></td>
	  </tr>
    </table>
  </div>
</div> 
<%} %>
</div>
</body>
<style>
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
.showTable th{ height: 50px;line-height:50px; text-align: right;padding-right: 20px;color:blue; width:180px;border: 1px solid #ccc; }
.showTable td{ height: 50px;line-height:50px; text-align: left;padding-left: 20px;border: 1px solid #ccc; }

.imgDivCar{
  width: 376px;
  height: 613px;
  float: right;
  background: #F1F1F1;
  border-bottom: 1px solid #ccc;
  border-right: 1px solid #ccc;
  text-align:center
}

.imgDivPerson{
  width: 375px;
  height: 357px;
  float: right;
  background: #F1F1F1;
  border-bottom: 1px solid #ccc;
  border-right: 1px solid #ccc;
  text-align:center
}
</style>
</html>