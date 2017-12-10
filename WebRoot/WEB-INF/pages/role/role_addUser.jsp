<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>"/>
  <%@ include file = "/js/include/common.inc"%>
  <%@ include file = "/js/include/ztree.inc"%>
  <%@ include file = "/js/include/jqgrid.inc"%>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$("#userGrid").jqGrid({
		url:"role_ajaxUserForSelectGrid.do?roleId=${role.id}",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
            {name: "id",sortable:false,key:true,hidden:true},
            {name: "account",width:"100px",align:"center",sortable:false,label:"账号"},
            {name: "names",width:"100px",align:"center",sortable:false,label:"姓名"},
            {name: "orgName",width:"180px",align:"center",sortable:false,label:"组织机构"},
            {name: "selectOpt",width:"120px",align:"center",sortable:false,label:"操作"},
        ],
        rownumbers : true,
	   	rowNum:20,
	   	rowList:[15,20,30,50],
	   	pager: "#pager01",
	   	prmNames: {page:"page",rows:"rows"},
	    viewrecords: true,
    	jsonReader: {
    		repeatitems : false,
    		root : "gridModel",
			page : "page",
			total : "total",
			records : "records"
    	},
    	onSelectRow:function(rowId,statues){}
	});
	
	$(window).resize(function (e) {
		$("#userGrid").setGridWidth($(window).width());
	});
	
	$(".input-group-addon").on("click", function(){//搜索  
		mySearch();
	});
	
	$("#inputValue").val("");
});
	
	/**选择用户*/
	function selectUser(id,name,account){
		if($("#option_"+id).length > 0) return;
		var option = "<option id='option_"+id+"' value='"+id+"'>"+name+"("+account+")</option>"
		$("#selectedUser").append(option);
	}
	
	/** 刷新Grid @*/
	function refreshGrid(){
		$("#userGrid").trigger("reloadGrid");//刷新Grid
	}
	
	/** 搜索  @*/
	function mySearch(){
		var inputValue = encodeURIComponent($("#inputValue").val());
		var url = "role_ajaxUserForSelectGrid.do?roleId=${role.id}&inputValue="+inputValue;
		$("#userGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}
	
	/**添加选择的用户  @*/
	function selectUser(userId){
		$.ajax({
	 		url:"role_ajaxAddUser.do",
	 		type:"post",
	 		async:false,
	 		dataType:"json",
	 		data:{
	 			userId: userId,
	 			roleId: "${role.id}"
	 		},
	 		success:function(data){
	 			if(data.status == "n"){
					alert(data.info);
					return;
				}
				refreshGrid();
	 		}
		});
	}
</script>
<body style="overflow: hidden;">
<div class="lk-panel-return ">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.history.go(-1);"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;padding-top: 6px;">【${role.roleName}】角色-添加用户</div>
</div>

<div class="lk-panel-reasarch">
  <div class="input-group">
    <input type="text" id="inputValue" placeholder="请输入查询条件" class="form-control"><span class="input-group-addon btn btn-primary">搜索</span>
  </div>
</div>
<table width="100%" id="userGrid" style="font-size: 14px; "></table>
<div id="pager01"></div>
</body>
</html>