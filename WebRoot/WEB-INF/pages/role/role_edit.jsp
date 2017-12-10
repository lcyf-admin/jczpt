<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>" />
  <%@ include file = "/js/include/common.inc"%>
  <%@ include file = "/js/include/validator.inc"%>
</head>
<script type="text/javascript">
$(function(){
	//表单验证
	$("#form").bootstrapValidator({
		message: "This value is not valid",
        feedbackIcons: feedbackIcons,
        fields: {
            "roleName": {
                validators: { 
                	notEmpty: { message: "角色名称不能为空" },
            		stringLength: { min: 1, max: 30, message: "请输入1-30个字符" }
                }
            }
        }
	});
	
	$("#role_isBase").val("${role.isBase}");
});

	//保存角色信息
   	function submitForm(){
   		var $form = $("#form");
	    var data = $form.data("bootstrapValidator");
	    if(!data) return false;
	    data.validate();// 修复记忆的组件不验证
	    if (!data.isValid()) return false;//无效
   		$.ajax({
   			type:"POST",
   			url:"role_ajaxUpdate.do",
   			async:false,
   			data:$("#form").serialize(),
   			success:function(data){
			    window.open("role_list.do", "I3");
   			}
   		});
   	}
</script>
<body>
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.history.go(-1);"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;padding-top: 6px;">编辑角色</div>
</div>
<form class="form-horizontal" role="form" id="form" action="">
  <input type="hidden" name="id" value="${role.id}">
  <fieldset>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">角色名称</label>
      <div class="col-sm-8 ">
        <input class="form-control" type="text" name="roleName" value="${role.roleName}" placeholder="请输入角色名称"/>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">是否基础角色</label>
      <div class="col-sm-8 ">
      	<select class="form-control" name="isBase" id="role_isBase">
      	  <option value="0">否</option>
      	  <option value="1">是</option>
      	</select>
      </div>
      <div class="col-sm-1"></div>
    </div>
    
    <div class="form-group" align="center">
      <button type="button" class="btn btn-success " onclick="submitForm();">提&nbsp;&nbsp;交</button>
      &nbsp;&nbsp;&nbsp;&nbsp;
	  <button type="button" class="btn btn-default " onclick="$('#form')[0].reset();">重&nbsp;&nbsp;置</button>
    </div>
  </fieldset>     
</form>
</body>
</html>