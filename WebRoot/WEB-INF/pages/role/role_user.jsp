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
  <%@ include file = "/js/include/jqgrid.inc"%>
<script>
$(function(){
	$("#roleUserGrid").jqGrid({
		url:"role_ajaxRoleUserGrid.do?seq:role_id=${role.id}",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
			{name: "ID",sortable:false,key:true,hidden:true},
            {name: "ACCOUNT",width:"120px",align:"center",sortable:false,label:"帐号"},
            {name: "NAMES",width:"120px",align:"center",sortable:false,label:"姓名"},
            {name: "ORG_NAME",width:"220px",align:"center",sortable:false,label:"组织机构"},
            {name: "OPT",width:"120px",align:"center",sortable:false,label:"操作"}
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
		$("#roleUserGrid").setGridWidth($(window).width());
	});
});
	/**为角色添加 用户*/
	function addRoleUser(){
		var url = "role_addUser.do?role.id=${role.id}"
		window.open(url, "I3");
	}
	
	/**删除角色中的用户 */
	function deleteUser(id){
		$.ajax({
	 		url: "role_ajaxDeleteRoleUser.do?roleUserId="+id,
	 		type: "post",
	 		async: false,
	 		dataType: "text",
	 		success: function(data){
	 			$("#roleUserGrid").trigger("reloadGrid");//刷新Grid
	 		}
		});
	}
	
</script>
</head>
<body style="overflow: hidden;">
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.open('role_list.do','I3');"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;padding-top: 6px;">【${role.roleName}】角色-用户列表</div>
</div>
<div style="margin: 10px;">
  <input type="button" onclick="addRoleUser();" class="btn btn-info" value="增加用户"/>
</div>
<table width="100%" id="roleUserGrid" style="font-size: 14px; "></table>
<div id="pager01"></div>
</body>
</html>