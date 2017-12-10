<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	$.ajax({
		url:"orgzon_ajaxOrgzonZTree.do",
 		type:"post",
 		async:false,
 		dataType:"json",
 		success:function(data){
 			$.fn.zTree.init($("#orgzonZTree"),{
 				view: {dblClickExpand: true,showLine: false,selectedMulti: false},
				data: {simpleData: {enable: true}},
				callback: {
					onClick: function(e, treeId, treeNode){
						if(treeNode.isRoot){//根节点
							$("#user_orgName").val("");
							$("#user_orgId").val("");
							return false;
						}else{
							$("#user_orgName").val(treeNode.name);
							$("#user_orgId").val(treeNode.id);
						}
					}
				}
			}, data);
 		}
	});
	
	//表单验证
	$("#form").bootstrapValidator({
		message: "This value is not valid",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid: "glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        },
        fields: {
        	"account": {
                validators: {
                    notEmpty: {
                        message: "账号不能为空"
                    },
                    stringLength: {
                    	min: 1,
                    	max: 20,
                    	message: "请输入1-20个字符"
                    },
                    remote: {
                    	url: "user_ajaxExist.do",
                    	message: "该账号已存在",
                    	delay: 1000, //每输入一个字符，就发送ajax请求，服务器压力太大，设置每1秒发送一次ajax
                    	type: "POST"
                    }
                }
            },
            "names": {
                validators: {
                    notEmpty: {
                        message: "姓名不能为空"
                    },
                    stringLength: {
                    	min: 1,
                    	max: 30,
                    	message: "请输入1-30个字符"
                    }
                }
            },
            "serials": {
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
                    	max: 3,
                    	message: "请输入0-999的数字"
                    }
                }
            }
        }
	});
});

	//保存用户信息
   	function submitForm(){
   		var $form = $("#form");
	    var data = $form.data("bootstrapValidator");
	    if(!data) return false;
        data.validate();// 修复记忆的组件不验证
        if (!data.isValid()) return false;//无效
   		$.ajax({
   			type:"POST",
   			url:"user_ajaxAdd.do",
   			async:false,
   			data:$("#form").serialize(),
   			success:function(data){
				window.open("user_list.do", "I3");
   			}
   		});
   	}
</script>
<body style="overflow: hidden;min-height: 450px;">
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.history.go(-1);"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;padding-top: 6px;">添加用户信息</div>
</div>
<form class="form-horizontal" role="form" id="form" action="">
  <input type="hidden" name="status" value="1"/>
  <fieldset>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-1 control-label">帐号</label>
      <div class="col-sm-4">
        <input class="form-control" type="text" name="account" placeholder="请输入帐号"/>
      </div>
      <label class="col-sm-1 control-label">组织机构</label>
      <div class="col-sm-4">
        <input readonly="readonly" class="form-control" id="user_orgName" onclick="showMenu('user_orgName','orgzonZTree');" type="text" placeholder="请选择组织机构"/>
      	<input type="hidden" id="user_orgId" name="orgId"/>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-1 control-label" for="ds_host">姓名</label>
      <div class="col-sm-4">
        <input class="form-control" name="names"  type="text" placeholder="请输入姓名"/>
      </div>
      <label class="col-sm-1 control-label">性别</label>
      <div class="col-sm-4 ">
        <select class="form-control" name="gender">
		  <option>男</option>
		  <option>女</option>
		</select>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-1 control-label">显示顺序</label>
      <div class="col-sm-4">
        <input class="form-control" name="serials" type="text" placeholder="请输入显示顺序"/>
      </div>
      <label class="col-sm-1 control-label" ></label>
      <div class="col-sm-4 "></div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group" align="center">
      <button type="button" class="btn btn-success " onclick="submitForm();">提&nbsp;&nbsp;交</button>
      &nbsp;&nbsp;&nbsp;&nbsp;
	  <button type="button" class="btn btn-default " onclick="$('#form')[0].reset();">重&nbsp;&nbsp;置</button>
    </div>
  </fieldset>     
</form>
<ul id="orgzonZTree" class="ztree myztree" style="margin-top:0;display:none;height:200px; position: absolute;border: 1px solid #ccc;"></ul>
</body>
</html>