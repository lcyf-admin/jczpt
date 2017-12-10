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
  <%@ include file = "/js/include/jqgrid.inc"%>
</head>
<script>
$(function(){
	$("#roleGrid").jqGrid({
		url:"role_ajaxGrid.do",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
			{name: "id",sortable:false,key:true,hidden:true},
            {name: "roleName",width:"120px",align:"center",sortable:false,label:"角色名称"},
            {name: "isBaseView",width:"120px",align:"center",sortable:false,label:"是否基础角色"},
            {name: "opt",width:"120px",align:"center",sortable:false,label:"操作"}
        ],
        rownumbers : true,
	   	rowNum: 10000,
	   	prmNames: {page:"page",rows:"rows"},
	    viewrecords: true,
    	jsonReader: {
    		repeatitems : false,
    		root : "gridModel"
    	},
    	onSelectRow:function(rowId,statues){}
	});
	
	$(window).resize(function (e) {
		$("#roleGrid").setGridWidth($(window).width());
	});
});
	
	/**编辑角色  @*/
	function editRole(roleId){
		window.open("role_edit.do?role.id="+roleId,"I3");
	}
	
	/**删除角色  @*/
	function deleteRole(roleId){
		if(!window.confirm("确认删除角色？"))return;
		$.ajax({
	 		url:"role_ajaxDelete.do?roleId="+roleId,
	 		type:"post",
	 		async:false,
	 		dataType:"text",
	 		success:function(data){
	 			$("#roleGrid").trigger("reloadGrid");//刷新Grid
	 		}
		});
	}
	
	/**设置菜单 */
	function menu(roleId){
		window.open("role_menu.do?role.id="+roleId,"I3");
	}
	
	/**设置菜单 */
	function user(roleId){
		window.open("role_user.do?role.id="+roleId,"I3");
	}
</script>
<body style="overflow: hidden;">
<div style="margin: 10px;">
  <input type="button" onclick="window.open('role_add.do','I3')" class="btn btn-info" value="增加角色"/>
  &nbsp;&nbsp;&nbsp;<font color="green" size=".8em">注：所有用户享有基础角色的菜单权限！</font>
</div>
<table width="100%" id="roleGrid" style="font-size: 14px; "></table>
</body>
</html>