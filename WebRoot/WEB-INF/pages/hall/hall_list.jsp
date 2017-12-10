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
	var gridURL = "<%=basePath%>hall_ajaxGrid";
	$(function(){
		$("#hallGrid").jqGrid({
			url:gridURL,
		   	mtype:"post",
			datatype: "json",
			shrinkToFit: true,
		   	colModel: [
	            {name: "ID",sortable:false,key:true,hidden:true},
	            {name: "HALLNAME",width:"100px",align:"center",sortable:false,label:"安检厅名称"},
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
			$("#hallGrid").setGridWidth($(window).width());
		});
		
		$(".input-group-addon").on("click", function(){//搜索  
			search();
		});
		$("#inputValue").val("");
	});

	//刷新列表
	function refreshGrid(url){
		if(url){
			$("#hallGrid").jqGrid("setGridParam",{url:url,page:1}).trigger("reloadGrid");
		}else{
			$("#hallGrid").trigger("reloadGrid");//刷新Grid
		}
	}
	
	//增加设备
	function addHall(){
		var dataUrl = "<%=basePath%>hall_add";
	    if (dataUrl == undefined || $.trim(dataUrl).length == 0) {
	        return false;
	    }
	    window.open(dataUrl, "I3");
	}
	
	//删除设备
	function deleteHall(hallId){
		if(!window.confirm("确认删除该设备吗？")) return;
		$.ajax({
			type: "POST",
			url: "<%=basePath%>hall_ajaxRemove?hallId="+hallId,
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
		var url = "<%=basePath%>hall_ajaxGrid?inputValue="+inputValue;
		$("#hallGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}
	
	//编辑设备
	function editHall(hallId){
		var editUrl = "<%=basePath%>hall_edit?hallId=" + hallId;
		window.parent.open(editUrl, "I3");
	}
</script>
<body style="overflow: hidden;">
<div class="lk-panel-reasarch">
	<div class="input-group">
		<input type="text" id="inputValue" placeholder="请输入查询条件" class="form-control"><span class="input-group-addon btn btn-primary">搜索</span>
	</div>
	<div class="input-btn">
		<input type="button" onclick="addHall();" class="btn btn-info" value="增加安检厅"/>
	</div>
</div>
<table width="100%" id="hallGrid" style="font-size: 14px; "></table>
</body>
</html>