<%@page import="cn.lhkj.commons.util.StringUtil"%>
<%@page import="cn.lhkj.project.system.entity.Orgzon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	String flag = (String)request.getAttribute("flag");//sibling增加同级；children增加下级；edit编辑
	Orgzon orgzon = (Orgzon)request.getAttribute("orgzon");//当前选中的部门
	
	String id = "";//部门ID
	String pid = "";//父部门ID
	String pName = "";//父部门名称
	String names = "";//部门名称
	String detailName = "";//部门名称
	String codes = "";//部门编号
	Integer prio = 0;//部门顺序
	String x = "";
	String y = "";
	if("edit".equals(flag)){//编辑
		id = StringUtil.trim(orgzon.getId());
		pid = StringUtil.trim(orgzon.getPid());
		pName = StringUtil.trim(orgzon.getpNames());
		names = StringUtil.trim(orgzon.getNames());
		detailName = StringUtil.trim(orgzon.getDetailName());
		prio = orgzon.getPrio();
		codes = StringUtil.trim(orgzon.getCodes());
		x = StringUtil.trim(orgzon.getX());
		y = StringUtil.trim(orgzon.getY());
	}else if("sibling".equals(flag)){//增加同级
		pid = StringUtil.trim(orgzon.getPid());
		pName = StringUtil.trim(orgzon.getpNames());
	}else if("children".equals(flag)){//增加下级
		pid = StringUtil.trim(orgzon.getId());
		pName = StringUtil.trim(orgzon.getNames());
	}
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>" />
  <%@ include file = "/js/include/common.inc"%>
  <%@ include file = "/js/include/ztree.inc"%>
  <%@ include file = "/js/include/validator.inc"%>
</head>
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
            "names": {
                validators: {
                    notEmpty: {
                        message: "机构名称不能为空"
                    },
                    stringLength: {
                    	min: 1,
                    	max: 30,
                    	message: "请输入1-30个字符"
                    }
                }
            },
            "prio": {
                validators: {
                    notEmpty: {
                        message: "显示顺序不能为空"
                    },
                    regexp: {
                    	regexp: /^[0-9]+$/,
                    	message: "请填入数字"
                    },
                    stringLength: {
                    	min: 1,
                    	max: 6,
                    	message: "请输入合法数字"
                    }
                }
            }
        }
	});
		
});

	//保存车组织机构信息
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
		   			url:"<%=basePath%>orgzon_ajaxUpdate.action?flag=<%=flag%>",
		   			async:false,
		   			data:$("#form").serialize(),
		   			success:function(data){
		   				var dataUrl = "<%=basePath%>orgzon_list.action";
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
<body>
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.history.go(-1);"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;padding-top: 6px;">添加/修改部门信息</div>
</div>
<form class="form-horizontal" role="form" id="form" action="">
  <input type="hidden" value="<%=id%>" name="id" />
  <input type="hidden" value="<%=pid%>" name="pid" />
  <fieldset>
  	<div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">上级部门</label>
      <div class="col-sm-8 ">
      	<p class="form-control-static"><%=pName%></p>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">机构编号</label>
      <div class="col-sm-8 ">
        <input class="form-control" type="text" value="<%=codes%>" name="codes" placeholder="请输入机构编号"/>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">机构名称</label>
      <div class="col-sm-8 ">
        <input class="form-control" type="text" value="<%=names%>" name="names" placeholder="请输入机构名称"/>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">机构详细名称</label>
      <div class="col-sm-8 ">
        <input class="form-control" type="text" value="<%=detailName%>" name="detailName" placeholder="请输入机构详细名称"/>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">经度</label>
      <div class="col-sm-8 ">
        <input class="form-control" type="text" value="<%=x%>" name="x" placeholder="请输入经度"/>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">纬度</label>
      <div class="col-sm-8 ">
        <input class="form-control" type="text" value="<%=y%>" name="y" placeholder="请输入纬度"/>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">显示顺序</label>
      <div class="col-sm-8 ">
        <input class="form-control" type="text" value="<%=prio%>" name="prio" placeholder="请输入机构显示顺序"/>
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