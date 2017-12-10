<%@page import="cn.lhkj.project.hall.entity.Hall"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="cn.lhkj.project.equipment.entity.Equipment" %>
<%@page import="java.util.List"%>
<%@page import="cn.lhkj.project.system.entity.DictItem"%>
<%@page import="cn.lhkj.commons.base.BaseDataDict"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Equipment equipment = (Equipment)request.getAttribute("equipment");
	List<DictItem> list = BaseDataDict.dictMap.get("WQUIP_TYPE");
	List<Hall> hallList = (List<Hall>)request.getAttribute("hallList");
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
	$("#form").bootstrapValidator({//表单验证
		message: "必填字段",
        feedbackIcons: feedbackIcons,
		fields: {
            "names": {
            	validators: {
	            	notEmpty: { message: "设备名称不能为空" },
	              	stringLength: { min: 1, max: 20, message: "请输入1-20个字符" }
	        	}
			},
	  		"url": {
	       		validators: {
	          		stringLength: { min: 1, max: 100, message: "请输入1-100个字符" },
	             	regexp: {
	               		regexp: /^((https|http|ftp|rtsp|mms)?:\/\/)+[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
	              		message: "服务地址格式错误"
	             	}
	     		}
			},
			"remark": {
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
		data.validate();// 修复记忆的组件不验证
		if (!data.isValid()) return false;//无效
       	$.ajax({
   			type:"POST",
   			url:"equipment_ajaxUpdate.do",
   			async:false,
   			data:$("#form").serialize(),
   			success:function(data){
			    window.open("equipment_listHall.do", "I3");
   			}
   		});
   	}
</script>
</head>
<body>
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.history.go(-1);"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;">
		更新安检厅设备信息
	</div>
</div>
<form id="form" class="form-horizontal" role="form" action="" method="post">
	<%-- <input type="hidden" name="id" value="${equipment.id}" /> --%>
  <fieldset>
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="equipment_id">设备编号</label>
      <div class="col-sm-8 col-md-6">
        <input class="form-control" id="equipment_id" name="id" type="text" placeholder="请输入设备编号" value="${equipment.id}" readonly="readonly" />
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>  
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="equipment_names">设备名称</label>
      <div class="col-sm-8 col-md-6">
        <input class="form-control" id="equipment_names" name="names" type="text" placeholder="请输入设备名称" value="${equipment.names}" />
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div> 
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="equipment_url">服务地址</label>
      <div class="col-sm-8 col-md-6">
        <input class="form-control" id="equipment_url" name="url" type="text" placeholder="请输入服务地址" value="${equipment.url}" />
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="equipment_type">设备类型</label>
      <div class="col-sm-8 col-md-6">
        <select class="form-control" id="equipment_type" name="type">
        <%for(DictItem t : list){%>
          <option value="<%=t.getId()%>" <%if(t.getId().equals(equipment.getType())){ %> selected="selected" <%} %>><%=t.getOptions()%></option>
       	<%} %>
        </select>
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="equipment_hallId">安检大厅</label>
      <div class="col-sm-8 col-md-6">
        <select class="form-control" id="equipment_hallId" placeholder="请选择安检大厅" name="hallId">
        <%for(Hall t : hallList){%>
          <option value="<%=t.getId()%>" <%if(t.getId().equals(equipment.getHallId())){ %> selected="selected" <%} %>><%=t.getHallName()%></option>
       	<%} %>
		</select>
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 col-md-2 control-label" for="equipment_remark">备注</label>
      <div class="col-sm-8 col-md-6">
        <input class="form-control" id="equipment_remark" name="remark" type="text" value="${equipment.remark}" />
      </div>
      <div class="col-sm-1 col-md-4 lk-col-hidden"></div>
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