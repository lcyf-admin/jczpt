<%@page import="cn.lhkj.project.lane.entity.Lane"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Lane lane = (Lane)request.getAttribute("lane");
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
	
   	//更新设备信息
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
		   			url:"<%=basePath%>lane_ajaxUpdate.do",
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
		更新车道信息
	</div>
</div>
<form id="form" class="form-horizontal" role="form" action="" method="post">
  <fieldset>
  	<div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="lane_id">车道编号</label>
      <div class="col-sm-8 col-md-6">
		<input class="form-control" id="lane_id" name="id" type="text" placeholder="请输入车道编号" value="${lane.id}" readonly="readonly" />
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="lane_laneName">车道名称</label>
      <div class="col-sm-8 col-md-6">
		<input class="form-control" id="lane_laneName" name="laneName" type="text" placeholder="请输入车道名称" value="${lane.laneName}" />
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>
    
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="lane_laneName">车道类型</label>
      <div class="col-sm-8 col-md-6">
        <select class="form-control" placeholder="请选择车道类型" name="laneType">
        	<option value="" ></option>
          	<option value="特检车道" <%if("特检车道".equals(lane.getLaneType())){ %> selected="selected" <%} %>>特检车道</option>
          	<option value="客车车道" <%if("客车车道".equals(lane.getLaneType())){ %> selected="selected" <%} %>>客车车道</option>
          	<option value="货车车道" <%if("货车车道".equals(lane.getLaneType())){ %> selected="selected" <%} %>>货车车道</option>
          	<option value="轿车车道" <%if("轿车车道".equals(lane.getLaneType())){ %> selected="selected" <%} %>>轿车车道</option>
		</select>
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>

    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="lane_laneName">排列顺序</label>
      <div class="col-sm-8 col-md-6">
        <input class="form-control" type="text" name="ranking" placeholder="请输入排列顺序" value="${lane.ranking}" />
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