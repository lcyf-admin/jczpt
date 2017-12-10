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
	$("#dictItemGrid").jqGrid({
		url:"dictItem_ajaxGrid.do?seq:dictId=${dict.id}",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
			{name: "id",sortable:false,key:true,hidden: true},
			{name: "codes",width:"120px",align:"center",sortable:false,label:"选项编码"},
            {name: "options",width:"120px",align:"center",sortable:false,label:"选项名称"},
            {name: "ranking",width:"120px",align:"center",sortable:false,label:"选项顺序"},
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
		$("#dictItemGrid").setGridWidth($(window).width());
	});
});
	
	/**增加数据字典项  @*/
	function addDictItem(){
		window.open("dictItem_add.do?dict.id=${dict.id}","I3");
	}
	
	/**编辑数据字典项  @*/
	function editDictItem(dictItemId){
		window.open("dictItem_edit.do?dict.id=${dict.id}&dictItem.id="+dictItemId,"I3");
	}
	
	/**删除数据字典项  @*/
	function deleteDictItem(dictItemId){
		if(!window.confirm("确认删除数据字典项？"))return;
		$.ajax({
	 		url:"dictItem_ajaxDelete.do?dictItemId="+dictItemId,
	 		type:"post",
	 		async:false,
	 		dataType:"text",
	 		success:function(data){
	 			$("#dictItemGrid").trigger("reloadGrid");//刷新Grid
	 		}
		});
	}

</script>
<body style="overflow: hidden;">
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.location.href='dict_list.do';"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
	<div style="text-align: center;font-size: 2em;color: #aaa;padding-top: 6px;">【${dict.names}】的数据集</div>
</div>

<div style="margin: 10px;">
  <input type="button" onclick="addDictItem();" class="btn btn-info" value="增加数据项"/>
</div>
<table width="100%" id="dictItemGrid" style="font-size: 14px; "></table>
</body>
</html>