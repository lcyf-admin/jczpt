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
<%@ include file = "/js/include/ztree.inc"%>
<%@ include file = "/js/include/validator.inc"%>
<script type="text/javascript">
	$(function(){
		//表单验证
		$("#form").bootstrapValidator({
			message: "This value is not valid",
	        feedbackIcons: {
	            valid: "glyphicon glyphicon-ok",
	            invalid: "glyphicon glyphicon-remove",
	            validating: "glyphicon glyphicon-refresh"
	        },
	        fields: {
	            "hallName": {
	                validators: {
	                    notEmpty: {
	                        message: "安检厅名称不能为空"
	                    },
	                    stringLength: {
	                    	min: 1,
	                    	max: 20,
	                    	message: "请输入1-20个字符"
	                    }
	                }
	            },
	            "ranking": {
	                validators: {
	                    notEmpty: {
	                        message: "排列顺序不能为空"
	                    },
	                    regexp: {
	                    	regexp: /^[0-9]+$/,
	                    	message: "请填入数字"
	                    },
	                    stringLength: {
	                    	min: 1,
	                    	max: 3,
	                    	message: "请输入0-999的数字"
	                    }
	                }
	            }
	        }
		});
	});
	
   	//更新设备信息
   	var gridURL = "<%=basePath%>hall_ajaxGrid.do";
   	function submitForm(){
   		var $form = $("#form");
	    var data = $form.data("bootstrapValidator");
	    if (data) {
	    	// 修复记忆的组件不验证
	        data.validate();
	        if (!data.isValid()) {//无效
	            return false;
	        }else{//有效
	        	$.ajax({
		   			type:"POST",
		   			url:"<%=basePath%>hall_ajaxUpdate.do",
		   			async:false,
		   			data:$("#form").serialize(),
		   			success:function(data){
		   				var dataUrl = "<%=basePath%>hall_list.do";
					    if (dataUrl == undefined || $.trim(dataUrl).length == 0) {
					        return false;
					    }
					    window.open(dataUrl, "I3");
		   			}
		   		});
	        }
	    }
   	}
</script>
</head>
<body>
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.history.go(-1);"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;">
		更新安检厅信息
	</div>
</div>
<form id="form" class="form-horizontal" role="form" action="" method="post">
	<input type="hidden" name="id" value="${hall.id}" />
  <fieldset>
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="hall_hallName">安检厅名称</label>
      <div class="col-sm-8 col-md-6">
		<input class="form-control" id="hall_hallName" name="hallName" type="text" placeholder="请输入安检厅名称" value="${hall.hallName}" />
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="hall_laneName">排列顺序</label>
      <div class="col-sm-8 col-md-6">
        <input class="form-control" type="text" name="ranking" polder="请输入排列顺序" value="${hall.ranking}" />
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
    	 <div class="col-sm-11 col-md-8" align="center">
    	 	<button type="button" class="btn btn-success" onclick="submitForm();">提&nbsp;&nbsp;交</button>
    	 	&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-default" onclick="$('#form')[0].reset();">重&nbsp;&nbsp;置</button>
    	 </div>
    	<div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>
  </fieldset>
</form>
<div class="lktable-edit-footer"></div>
</body>
</html>