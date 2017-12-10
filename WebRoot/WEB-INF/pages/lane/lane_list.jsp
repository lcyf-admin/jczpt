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
	var gridURL = "<%=basePath%>lane_ajaxGrid.do";
	$(function(){
		$("#laneGrid").jqGrid({
			url:gridURL,
		   	mtype:"post",
			datatype: "json",
			shrinkToFit: true,
		   	colModel: [
	            {name: "ID",sortable:false,key:true,hidden:false,align:"center",label:"车道编号"},
	            {name: "LANENAME",width:"100px",align:"center",sortable:false,label:"车道名称"},
	            {name: "LANETYPE",width:"100px",align:"center",sortable:false,label:"车道类型"},
	            {name: "RANKING",width:"80px",align:"center",sortable:false,label:"排列顺序"},
	            {name: "OPT",width:"100px",align:"center",sortable:false,label:"操作",formatter:"optionLink"}
	        ],
	        rownumbers : true,
	        rowNum: 10000,
		   	prmNames: {page:"page",rows:"rows"},
		    viewrecords: true,
		    height: "100%",
		    autowidth: true,
	    	jsonReader: {
	    		repeatitems : false,
	    		root : "gridModel"
	    	},
	    	onSelectRow:function(rowId,statues){}
		});
		
		$(window).resize(function (e) {
			$("#laneGrid").setGridWidth($(window).width());
		});
		
		$(".input-group-addon").on("click", function(){//搜索  
			search();
		});
		$("#inputValue").val("");
	});

	//刷新列表
	function refreshGrid(url){
		if(url){
			$("#laneGrid").jqGrid("setGridParam",{url:url,page:1}).trigger("reloadGrid");
		}else{
			$("#laneGrid").trigger("reloadGrid");//刷新Grid
		}
	}
	
	//增加设备
	function addLane(){
		var dataUrl = "<%=basePath%>lane_add.do";
	    if (dataUrl == undefined || $.trim(dataUrl).length == 0) {
	        return false;
	    }
	    window.open(dataUrl, "I3");
	}
	
	//删除设备
	function deleteLane(laneId){
		if(!window.confirm("确认删除该设备吗？")) return;
		$.ajax({
			type: "POST",
			url: "<%=basePath%>lane_ajaxRemove.do?laneId="+laneId,
	 		async: false,
	 		dataType: "text",
			success: function(msg){
				refreshGrid(gridURL);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("服务器忙，请稍后再试！");
			}
		});
	}
	
	//搜索
	function search(){
		var inputValue = encodeURIComponent($("#inputValue").val());
		var url = "<%=basePath%>lane_ajaxGrid.do?inputValue="+inputValue;
		$("#laneGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}
	
	//编辑设备
	function editLane(laneId){
		var editUrl = "<%=basePath%>lane_edit.do?lane.id=" + laneId;
		window.parent.open(editUrl, "I3");
	}
</script>
<body style="overflow: hidden;">
<div class="lk-panel-reasarch">
	<div class="input-group">
		<input type="text" id="inputValue" placeholder="请输入查询条件" class="form-control"><span class="input-group-addon btn btn-primary">搜索</span>
	</div>
	<div class="input-btn">
		<input type="button" onclick="addLane();" class="btn btn-info" value="增加车道"/>
	</div>
</div>
<table width="100%" id="laneGrid" style="font-size: 14px; "></table>
</body>
</html>