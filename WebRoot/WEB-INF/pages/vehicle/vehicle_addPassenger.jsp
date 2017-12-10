<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="x-hidden" > 
<head>
<title></title>
  <base href="<%=basePath%>" />
  <%@ include file = "/js/include/common.inc"%>
  <%@ include file = "/js/include/validator.inc"%>
<script type="text/javascript">
$(function(){
	$("#form").bootstrapValidator({
		message: "必填字段",
        feedbackIcons: feedbackIcons,
        fields: {
            "userName": {
                validators: {
                    notEmpty: { message: "乘客姓名不能为空" },
                    stringLength: { min: 1, max: 50, message: "请输入1-50个字符" }
                }
            },"cardNum": {
                validators: {
                    notEmpty: { message: "身份证号不能为空" },
                    regexp: {
                    	regexp: /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,
                    	message: "身份证号校验错误"
                    }
                }
            },"minzu": {
                validators: {
                	stringLength: { min: 1, max: 20, message: "请输入1-20个字符"}
                }
            },"qianfa": {
                validators: {
                    stringLength: { min: 1, max: 20, message: "请输入1-20个字符" }
                }
            },"youxiaoqi": {
                validators: {
                    stringLength: {	min: 0,	max: 20, message: "请输入1-20个字符" }
                }
            },"address": {
                validators: {
                    stringLength: {	min: 0,	max: 100, message: "请输入1-100个字符" }
                }
            }
        }
	});
		
});
   	
   	//保存设备信息
   	function submitForm(){
   		var $form = $("#form");
	    var data = $form.data("bootstrapValidator");
	    if(!data) return false;
		data.validate(); // 修复记忆的组件不验证
		if(!data.isValid()) return false;
       	$.ajax({
   			type: "POST",
   			url: "vehicle_ajaxAddPassenger.action",
   			async: false,
   			data: $("#form").serialize(),
   			success:function(data){
			    setTimeout(top.closeWindow,500);
			    top.window.location.reload();
   			}
   		});
   	}
</script>
</head>
<body>
<form id="form" class="form-horizontal" role="form" action="" method="post" style="margin-top: 30px;">
  <input type="hidden" name="vehicleId" value="${id}"/>
  <fieldset>
    <div class="form-group">
      <label class="col-sm-3 control-label" for="userName">乘客姓名</label>
      <div class="col-sm-8">
        <input class="form-control" id="userName" name="userName" type="text" placeholder="必填，请输入乘客姓名"/>
      </div>
      <div class="col-sm-1 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label" for="cardNum">身份证号</label>
      <div class="col-sm-8">
        <input class="form-control" id="cardNum" name="cardNum" type="text" placeholder="必填，请输入身份证号"/>
      </div>
      <div class="col-sm-1 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label" for="minzu">民族</label>
      <div class="col-sm-8">
        <input class="form-control" id="minzu" name="minzu" type="text"/>
      </div>
      <div class="col-sm-1 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label" for="qianfa">签发机关</label>
      <div class="col-sm-8">
        <input class="form-control" id="qianfa" name="qianfa" type="text" />
      </div>
      <div class="col-sm-1 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label" for="youxiaoqi">有效期</label>
      <div class="col-sm-8">
        <input class="form-control" id="youxiaoqi" name="youxiaoqi" type="text" />
      </div>
      <div class="col-sm-1 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label" for="address">地址</label>
      <div class="col-sm-8">
        <input class="form-control" id="address" name="address" type="text" />
      </div>
      <div class="col-sm-1 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-11" align="center">
        <button type="button" class="btn btn-success btn-lg" onclick="submitForm();">提&nbsp;&nbsp;交</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-default btn-lg" onclick="$('#form')[0].reset();">重&nbsp;&nbsp;置</button>
      </div>
      <div class="col-sm-1 lk-col-hidden"></div>
    </div>
  </fieldset>
</form>
<div class="lktable-edit-footer"></div>
</body>
</html>