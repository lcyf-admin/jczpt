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
</head>

<script type="text/javascript">
	var settings = {
		view: {
			dblClickExpand: true,
			showLine: false,
			selectedMulti: false
		},
		data: {simpleData: {enable: true},key:{ title:"title"}},
		callback: {
			onClick: function(e, treeId, treeNode){
				if($("#orgzonId").val() == treeNode.id) return;
				$("#orgzonId").val(treeNode.id);
				$("#treeNode_tId").val(treeNode.tId);
				
				$(".btn-sm").attr("disabled",true);
				if(treeNode.isRoot){//根节点
					$("#addChildren").attr("disabled",false);
				}else{//非根节点
					$("#addSibling").attr("disabled",false);
					$("#addChildren").attr("disabled",false);
					$("#editOrgzon").attr("disabled",false);
					if(treeNode.isLeaf){//叶子节点
						$("#deleteOrgzon").attr("disabled",false);
					}
				}
			}
		}
	};

	/**获取组织机构树所有数据*/
	function addTreeData(){
		$.ajax({
	 		url:"orgzon_ajaxOrgzonZTree.action",
	 		type:"post",
	 		async: true,
	 		dataType:"json",
	 		success:function(data){
	 			$.fn.zTree.init($("#orgzonZtree"), settings, data);
	 		}
		});
	};
$(function(){
	addTreeData();
	
	$("#addChildren").on("click",function(){//增加下级
		var url = "orgzon_edit.action?orgzon.id="+$("#orgzonId").val()+"&flag=children";
		window.open(url,"I3");
	});
	
	$("#addSibling").on("click",function(){//增加同级
		var url = "orgzon_edit.action?orgzon.id="+$("#orgzonId").val()+"&flag=sibling";
		window.open(url,"I3");
	});
	
	$("#editOrgzon").on("click",function(){//编辑组织机构
		var url = "orgzon_edit.action?orgzon.id="+$("#orgzonId").val()+"&flag=edit";
		window.open(url,"I3");
		
	});
	
	$("#deleteOrgzon").on("click",function(){//编辑删除组织机构
		if(!window.confirm("确认删除此组织机构吗？")) return;		
		$.ajax({
			type: "POST",
			url: "orgzon_ajaxRemove.action?orgzonId="+$("#orgzonId").val(),
	 		async: false,
	 		dataType: "text",
			success: function(msg){
				window.location.reload();
				/*var treeObj = $.fn.zTree.getZTreeObj("orgzonZtree");
				var node = treeObj.getNodeByTId($("#treeNode_tId").val());
				treeObj.removeNode(node);
				$(".btn-sm").attr("disabled",true);*/
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("服务器忙，请稍后再试！")
			}
		});	
	});
	
	$("#treeNode_tId").val("");
	$("#orgzonId").val("");
});



</script>
<body style="overflow: hidden;">
<input id="treeNode_tId" type="hidden"/>
<input id="orgzonId" type="hidden"/>
<div style="margin-bottom: 10px;" id="btnGroup" >
	<button id="addChildren" disabled="disabled" class="btn btn-info btn-sm">增加下级</button>
	<button id="addSibling" disabled="disabled" class="btn btn-success btn-sm">增加同级</button>
	<button id="editOrgzon" disabled="disabled" class="btn btn-warning btn-sm">编&nbsp;&nbsp;辑</button>
	<button id="deleteOrgzon" disabled="disabled" class="btn btn-danger btn-sm">删&nbsp;&nbsp;除</button>
</div>
<ul id="orgzonZtree" class="ztree myztree"></ul>
</body>
</html>