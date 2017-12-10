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
		//表单验证
		$("#form").bootstrapValidator({
			message: "This value is not valid",
	        feedbackIcons: {
	            valid: "glyphicon glyphicon-ok",
	            invalid: "glyphicon glyphicon-remove",
	            validating: "glyphicon glyphicon-refresh"
	        },
	        fields: {
	        	"id": {
	                validators: {
	                    notEmpty: {
	                        message: "车道编号不能为空"
	                    },
	                    stringLength: {
	                    	min: 1,
	                    	max: 30,
	                    	message: "请输入1-30个字符"
	                    },
	                    remote: {
	                    	url: "<%=basePath%>lane_ajaxExist.do",
	                    	message: "该车道编号已存在",
	                    	delay: 1000, //每输入一个字符，就发送ajax请求，服务器压力太大，设置每1秒发送一次ajax
	                    	type: "POST"
	                    }
	                }
	            },
	            "laneName": {
	                validators: {
	                    notEmpty: {
	                        message: "车道名称不能为空"
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
	
   	//保存车道信息
   	var gridURL = "<%=basePath%>lane_ajaxGrid.do";
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
		   			url:"<%=basePath%>lane_ajaxSave.do",
		   			async:false,
		   			data:$("#form").serialize(),
		   			success:function(data){
		   				var dataUrl = "<%=basePath%>lane_list.do";
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
		添加车道信息
	</div>
</div>
<form id="form" class="form-horizontal" role="form" action="" method="post">
  <fieldset>
  	<div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="lane_id">车道编号</label>
      <div class="col-sm-8 col-md-6">
		<input class="form-control" id="lane_id" name="id" type="text" placeholder="请输入车道编号"/>
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="lane_laneName">车道名称</label>
      <div class="col-sm-8 col-md-6">
		<input class="form-control" id="lane_laneName" name="laneName" type="text" placeholder="请输入车道名称"/>
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>

    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="lane_laneName">车道类型</label>
      <div class="col-sm-8 col-md-6">
        <select class="form-control" placeholder="请选择车道类型" name="laneType">
        	<option value="" ></option>
          	<option value="特检车道">特检车道</option>
          	<option value="客车车道">客车车道</option>
          	<option value="货车车道">货车车道</option>
          	<option value="轿车车道">轿车车道</option>
		</select>
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>

    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="lane_laneName">排列顺序</label>
      <div class="col-sm-8 col-md-6">
        <input class="form-control" type="text" name="ranking" placeholder="请输入排列顺序"/>
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