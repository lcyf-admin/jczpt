<%@page import="cn.lhkj.commons.util.StringUtil"%>
<%@page import="cn.lhkj.commons.entity.SessionBean"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	SessionBean user = (SessionBean)session.getAttribute("SESSION_BEAN");
	String stationId = StringUtil.trim(user.getStationId());
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<title>违禁品核录表</title>
<%@ include file="/js/include/common.inc"%>
<%@ include file = "/js/include/validator.inc"%>
<%@ include file = "/js/include/timepicker.inc"%>
<script src="<%=basePath%>js/lib/jqurey/jquery-form.js"></script>  
</head>
<body>
<div align="center" style="margin:20px 0;">
	<h3><b>太赫兹安检违禁物品核录表</b></h3>
</div>
<div class="lk-panel">
  <form method="post" action="person_ajaxAdd.action" id="form" enctype="multipart/form-data">
	<input type="hidden" name="personId" value="${personId}" />
    <table class="showTable" border="1">
      <tr>
        <th>违禁物品名称：</th>
        <td>
    	<select type="text" name="name" class="form-control" style="width: 100%;display: inline;background-color: #fff;"/>
            <option>枪支弹药</option>
            <option>管制刀具</option>
            <option>易燃易爆</option>
            <option>毒品</option>
            <option>液体</option>
            <option>其他</option>
    	</select>
        </td>
      </tr>
      
      <tr>
        <th>备注：</th>
        <td><input class="form-control" type="text" value="" name="remark" placeholder="备注"/></td>
      </tr>
      
      <tr>
        <th>采集时间：</th>
        <td><input name="checkTime" type="text" id="startDate" readonly="readonly" class="timepicker form-control" style="display: inline;background-color: #fff; " placeholder="采集时间"/></td>
      </tr>

      <tr>
        <th>上传图片：</th>
        <td><input type="file" class="form-control-input" name="file" id="file" value="file"/></td>
      </tr>

    </table>	
	
    <div class="form-group" align="center" style="margin-top: 20px;">
	  <button type="submit" class="btn btn-success btn-lg" >提&nbsp;&nbsp;交</button>
    </div>
   	
  </form> 


</div>
</body>
<script type="text/javascript">
	$(".timepicker").datetimepicker({timeFormat: "HH:mm:ss"});
	
	$(function() {
	 	$("#form").ajaxForm(function(data){
	 		//var obj = data.parseJSON(); 
	 		var obj = JSON.parse(data);
			if(obj.status == "y"){
				window.location.href = "person_detail.action?person.id=${personId}";
			}else{
				alert("添加失败");
			}
		}); 
	});
</script>
<style>
.lk-panel{
	border-radius:10px;
	padding: 0;
	margin: 5px;
}
.showTable   { width: 100%;border-collapse:collapse;font-size: 1.2em;margin-bottom:5px;}
.showTable th{ height: 50px; text-align: right;padding-right: 10px; width: 170px; }
.showTable td{ height: 50px; text-align: left;padding: 0 20px; }

.ctable      { width: 100%;border: 1px solid #ccc;margin-bottom: 5px;}
.cth         { width: 120px !important;padding-right: 0px !important;font-size: 13px; !important;}
.ctd		 { padding: 0 10px !important;}
.remark      { font-size: 0.8em;}  
</style>
</html>