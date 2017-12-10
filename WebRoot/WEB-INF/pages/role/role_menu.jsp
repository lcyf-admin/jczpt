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
<script>
$(function(){
	$.ajax({
		url:"menu_ajaxZTree.do",
 		type:"post",
 		async:false,
 		data:{roleId: "${role.id}"},
 		dataType:"json",
 		success:function(data){
 			$.fn.zTree.init($("#menuZtree"),{
 				check: {enable: true},
 				view: {dblClickExpand: true,showLine: false},
 				data: {simpleData: {enable: true}}
			}, data);
 		}
	});
});

	/** 提交角色的菜单树*/
	function updateRoleMenu(){
		var ids = getCheckedNodeIds($.fn.zTree.getZTreeObj("menuZtree").getNodes());
		$.ajax({
			type:"post",
			url:"role_ajaxUpdateRoleMenu.do",
			data:{
				menuIds : ids,
				roleId : "${role.id}"
			},
			dataType:"json",
			success:function(data){
				if(data.status == "n"){
					alert(data.info);
					return;
				}else{
					window.location.href = "role_list.do";
				}
			}
		});
	}
	
	/**遍历获得所有选中ID*/
	function getCheckedNodeIds(nodes){
		var ids = "";
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].checked){
				if(ids == ""){
					ids += nodes[i].id;
				}else{
					ids += ","+nodes[i].id;
				}
				if(nodes[i].isParent && nodes[i].children.length != 0){
					ids += ","+getCheckedNodeIds(nodes[i].children);
				}
			}
		}
		return ids;
	}
	
</script>
</head>
<body>
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.history.go(-1);"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;padding-top: 6px;">【${role.roleName}】设置菜单</div>
</div>
<button type="button" class="btn btn-success " onclick="updateRoleMenu();">提&nbsp;&nbsp;交</button>
<ul id="menuZtree" class="ztree myztree"></ul>
</body>
</html>