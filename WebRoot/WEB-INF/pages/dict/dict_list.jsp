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
	$("#dictGrid").jqGrid({
		url:"dict_ajaxGrid.do",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
			{name: "id",width:"120px",align:"center",sortable:false,key:true,label:"代码"},
            {name: "names",width:"120px",align:"center",sortable:false,label:"名称"},
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
		$("#dictGrid").setGridWidth($(window).width());
	});
});
	
	/**编辑数据字典  @*/
	function editDict(dictId){
		window.open("dict_edit.do?dict.id="+dictId,"I3");
	}
	
	/**编辑数据字典-数据集  @*/
	function setDictItem(dictId){
		window.open("dict_item.do?dict.id="+dictId,"I3");
	}
	
	/**删除数据字典  @*/
	function deleteDict(dictId){
		if(!window.confirm("确认删除数据字典？"))return;
		$.ajax({
	 		url:"dict_ajaxDelete.do?dictId="+dictId,
	 		type:"post",
	 		async:false,
	 		dataType:"text",
	 		success:function(data){
	 			$("#dictGrid").trigger("reloadGrid");//刷新Grid
	 		}
		});
	}

</script>
<body style="overflow: hidden;">
<div style="margin: 10px;">
  <input type="button" onclick="window.open('dict_add.do','I3')" class="btn btn-info" value="增加数据字典"/>
</div>
<table width="100%" id="dictGrid" style="font-size: 14px; "></table>
</body>
</html>