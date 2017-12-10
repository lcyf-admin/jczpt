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
        	"id": {
                validators: {
                    notEmpty: {  message: "字典编码不能为空" },
                    stringLength: { min: 1, max: 30, message: "请输入1-30个字符" },
                    remote: {
                    	url: "dict_ajaxExist.do",
                    	message: "该字典编码已存在",
                    	delay: 1000, //每输入一个字符，就发送ajax请求，服务器压力太大，设置每1秒发送一次ajax
                    	type: "POST"
                    }
                }
            },
            "names": {
                validators: {
                    notEmpty: { message: "字典名称不能为空" },
                    stringLength: { min: 1, max: 20, message: "请输入1-30个字符" }
                }
            }
        }
	});
});

	//保存数据字典信息
   	function submitForm(){
   		var $form = $("#form");
	    var data = $form.data("bootstrapValidator");
	    if(!data) return false;
		data.validate();// 修复记忆的组件不验证
		if (!data.isValid()) return false;//无效
   		$.ajax({
   			type:"POST",
   			url:"dict_ajaxAdd.do",
   			async:false,
   			data:$("#form").serialize(),
   			success:function(data){
			    window.open("dict_list.do", "I3");
   			}
   		});
   	}
</script>
<body>
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.history.go(-1);"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;padding-top: 6px;">添加数据字典</div>
</div>
<form class="form-horizontal" role="form" id="form" action="">
  <fieldset>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">字典编码</label>
      <div class="col-sm-8 ">
        <input class="form-control" type="text" name="id" placeholder="请输入字典编码"/>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">字典名称</label>
      <div class="col-sm-8 ">
        <input class="form-control" type="text" name="names" placeholder="请输入字典名称"/>
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