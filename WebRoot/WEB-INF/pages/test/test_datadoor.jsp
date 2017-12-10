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
function showPerson(){
	$("#data").text(new Date()+"-----start\n");
	$.ajax({
		type: "POST",
		url: "test_ajaxDataDoorPerson.action",
		data: { 
			"time": new Date().getTime(),
			"api_key": $("#api_key").val(),
			"api_secret": $("#api_secret").val(),
			"begin_time": $("#begin_time").val(),
			"end_time": $("#end_time").val(),
			"page": $("#page").val(),
			"page_number": $("#page_number").val(),
			"url": $("#url").val()
		},
		async: true,
 		dataType: "text",
		success: function(data){
			$("#data").append(eval("'"+data+"'"));
			$("#data").append("\n"+new Date()+"-----end");
		},
		error:function(request){
			$("#data").append(request.status+":"+request.statusText);
			$("#data").append("\n"+new Date()+"-----end");
		}
	});
}	
	
function showWarning(){
	$("#data").text(new Date()+"-----start\n");
	$.ajax({
		type: "POST",
		url: "test_ajaxDataDoorWarning.action",
		data: { 
			"time": new Date().getTime(),
			"api_key": $("#api_key").val(),
			"api_secret": $("#api_secret").val(),
			"begin_time": $("#begin_time").val(),
			"end_time": $("#end_time").val(),
			"page": $("#page").val(),
			"page_number": $("#page_number").val(),
			"url": $("#url2").val()
		},
 		async: true,
 		dataType: "text",
		success: function(data){
			$("#data").append(eval("'"+data+"'"));
			$("#data").append("\n"+new Date()+"-----end");
		},
		error: function(request){
			$("#data").append(request.status+":"+request.statusText);
			$("#data").append("\n"+new Date()+"-----end");
		}
	});
}	
	
$(function(){
	var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear()+"-"+month+"-"+strDate;
    $("#begin_time").val(currentdate+" 00:00:00");
    $("#end_time").val(currentdate+" 23:59:59");
});	
</script>
<body style="overflow-y: scroll;">
<table style="width: 100%;">
	<tr>
		<th width="120px;">page:</th>
		<td width="160px;"><input type="text" id="page" class="form-control" value="1"/></td>
		<th width="120px;">page_number:</th>
		<td width="160px;"><input type="text" id="page_number" class="form-control" value="10"/></td>
		<th width="120px;">api_key:</th>
		<td width="160px;"><input type="text" id="api_key" class="form-control" value=""/></td>
		<td width="120px;"></td>
		<td width="160px;"></td>
		<td></td>
	</tr>
	<tr>
		<th >begin_time:</th>
		<td ><input type="text" id="begin_time" class="form-control" value="2017-04-17 00:00:00"/></td>
		<th >end_time:</th>
		<td ><input type="text" id="end_time" class="form-control" value="2017-04-17 23:59:00"/></td>
		<th width="120px;">api_secret:</th>
		<td width="160px;"><input type="text" id="api_secret" class="form-control" value=""/></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<th >通行人员url:</th>
		<td colspan="3"><input type="text" id="url" class="form-control" value="http://22.129.76.28:9000/collection/v1.0/person"/></td>
		<th >警告数据url:</th>
		<td colspan="3"><input type="text" id="url2" class="form-control" value="http://22.129.76.28:9000/collection/v1.0/warning"/></td>
		<td ></td>
	</tr>
</table>
<div style="margin-top: 10px;">
  	<input type="button" onclick="showPerson();" class="btn btn-sm btn-info" value="通行人员查询"/>
  	<input type="button" onclick="showWarning();" class="btn btn-sm btn-warning" value="警告数据查询" />
</div>
<div>返回数据：</div>
<div><textarea rows="" cols="" id="data" style="width: 800px;height: 600px;" ></textarea></div>
</body>
</html>