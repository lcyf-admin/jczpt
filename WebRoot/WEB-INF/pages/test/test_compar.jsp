<%@page language="java" contentType="text/html; charset=UTF-8" %>
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
<style>
table th{ text-align: right;line-height: 40px;height:40px;}
</style>
<script>
function personCompar(isInputStream){
	$("#data").text(new Date()+"-----start\n");
	$.ajax({
		type: "POST",
		url: "test_ajaxCompar.action",
		data: {
			"time": new Date().getTime(),
			"type": "person",
			"url": $("#url").val(),
			"key": $("#key").val(),
			"idcard": $("#idcard").val()
		},
 		async: true,
 		dataType: "text",
		success: function(data){
			$("#data").append(data);
			$("#data").append("\n"+new Date()+"-----end");
		},
		error:function(request){
			$("#data").append(request.status+":"+request.statusText);
			$("#data").append("\n"+new Date()+"-----end");
		}
	});
}	
	
function vehicleCompar(isInputStream){
	$("#data").text(new Date()+"-----start\n");
	$.ajax({
		type: "POST",
		url: "test_ajaxCompar.action",
		data: {
			"time": new Date().getTime(),
			"type": "plate",
			"url": $("#url").val(),
			"key": $("#key").val(),
			"plate": $("#plate").val(),
			"plate_color":  $("#plate_color").val()
		},
 		async: true,
 		dataType: "text",
		success: function(data){
			$("#data").append(data);
			$("#data").append("\n"+new Date()+"-----end");
		},
		error:function(request){
			$("#data").append(request.status+":"+request.statusText);
			$("#data").append("\n"+new Date()+"-----end");
		}
	});
}	

function macCompar(isInputStream){
	$("#data").text(new Date()+"-----start\n");
	$.ajax({
		type: "POST",
		url: "test_ajaxCompar.action",
		data: {
			"time": new Date().getTime(),
			"type": "mac",
			"url": $("#url").val(),
			"key": $("#key").val(),
			"mac": $("#mac").val()
		},
 		async: true,
 		dataType: "text",
		success: function(data){
			$("#data").append(data);
			$("#data").append("\n"+new Date()+"-----end");
		},
		error:function(request){
			$("#data").append(request.status+":"+request.statusText);
			$("#data").append("\n"+new Date()+"-----end");
		}
	});
}	

function imCompar(isInputStream){
	$("#data").text(new Date()+"-----start\n");
	$.ajax({
		type: "POST",
		url: "test_ajaxCompar.action",
		data: {
			"time": new Date().getTime(),
			"type": "imei,imsi",
			"url": $("#url").val(),
			"key": $("#key").val(),
			"imei": $("#imei").val(),
			"imsi": $("#imsi").val()
		},
 		async: true,
 		dataType: "text",
		success: function(data){
			$("#data").append(data);
			$("#data").append("\n"+new Date()+"-----end");
		},
		error:function(request){
			$("#data").append(request.status+":"+request.statusText);
			$("#data").append("\n"+new Date()+"-----end");
		}
	});
}
</script>
<body style="overflow-y: scroll;">
<h5>VPN地址: http://12.39.120.125:8088/SynService/dataComparison.do</h5>
<table style="width: 100%;">
  <tr>
    <th width="65px;">key: </th>
    <td width="460px;"><input type="text" id="key" class="form-control" value="NDVkMmM5NDdkNTIyZGM5YTI5NzhjYzI4YWRjZTRmYzM2OTgwNjVkNQ=="/></td>
    <th width="65px;">url: </th>
    <td width="500px;"><input type="text" id="url" class="form-control" value="http://21.0.23.78/dkbd"/></td>
    <td ></td>
  </tr>
</table>
<table border=0 style="width: 100%;">
  <tr>
    <th width="65px;">身份证号:</th>
    <td width="200px;"><input type="text" id="idcard" class="form-control" value=""/></td>
    <th width="65px;">车牌号:</th>
    <td width="95px;"><input type="text" id="plate" class="form-control" value=""/></td>
    <th width="40px;">颜色:</th>
    <td width="65px;"><select type="text" id="plate_color" class="form-control" >
        <option>蓝</option>
        <option>黄</option>
        <option>白</option>
        <option>黑</option>
      </select>
    </td>
    <th width="65px;">mac:</th>
    <td width="200px;"><input type="text" id="mac" class="form-control" value=""/></td>
    <th width="65px;">imei:</th>
    <td width="100px;"><input type="text" id="imei" class="form-control" value=""/></td>
    <th width="35px;">imsi:</th>
    <td width="100px;"><input type="text" id="imsi" class="form-control" value=""/></td>
    <td></td>
  </tr>
  <tr>
    <td colspan="2" style="text-align: center;">
      <input type="button" onclick="personCompar(1);" class="btn btn-sm btn-info" value="人员比对"/>
      <!-- input type="button" onclick="personCompar(2);" class="btn btn-sm btn-info" value="比人stream"/ -->
    </td>
    <td colspan="4" style="text-align: center;">
      <input type="button" onclick="vehicleCompar(1);" class="btn btn-sm btn-warning" value="车辆比对" />
      <!-- input type="button" onclick="vehicleCompar(2);" class="btn btn-sm btn-warning" value="比车stream" /-->
    </td>
    <td colspan="2" style="text-align: center;">
      <input type="button" onclick="macCompar(1);" class="btn btn-sm btn-success" value="mac比对" />
      <!-- input type="button" onclick="vehicleCompar(2);" class="btn btn-sm btn-warning" value="比mac stream" /-->
    </td>
    <td colspan="4" style="text-align: center;">
      <input type="button" onclick="imCompar(1);" class="btn btn-sm btn-primary" value="imei,imsi比对" />
      <!-- input type="button" onclick="vehicleCompar(2);" class="btn btn-sm btn-warning" value="比imei,imsi stream" /-->
    </td>
   <td></td>
  </tr>
</table>
<div>返回数据：</div>
<div><textarea rows="" cols="" id="data" style="width: 1000px;height: 600px;" ></textarea></div>
</body>
</html>