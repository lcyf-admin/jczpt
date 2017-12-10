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
	var gridURL = "equipment_ajaxOfLaneGrid.do";
$(function(){
	$("#equipmentGrid").jqGrid({
		url:gridURL,
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
	   	colModel: [
            {name: "ID",width:"160px",sortable:false,key:true,hidden:false,align:"center",label:"设备编号"},
            {name: "NAMES",width:"100px",align:"center",sortable:false,label:"设备名称"},
            {name: "URL",width:"260px",align:"center",sortable:false,label:"服务地址"},
            {name: "LANENAME",width:"90px",align:"center",sortable:false,label:"车道"},
            {name: "LANETYPE",width:"90px",align:"center",sortable:false,label:"车道类型"},
            {name: "TYPE",width:"80px",align:"center",sortable:false,label:"设备类型"},
            {name: "REMARK",width:"80px",align:"center",sortable:false,label:"备注"},
            {name: "SHOWWAY",width:"100px",align:"center",sortable:false,label:"分流屏展示"},
            {name: "OPT",width:"100px",align:"center",sortable:false,label:"操作",formatter:"optionLink"}
        ],
        rownumbers : true,
	   	rowNum: 10000,
	   	prmNames: {page:"page",rows:"rows"},
	    viewrecords: true,
	    height: "100%",
    	jsonReader: {
    		repeatitems : false,
    		root : "gridModel"
    	},
    	onSelectRow:function(rowId,statues){}
	});
		
	$(window).resize(function (e) {
		$("#equipmentGrid").setGridWidth($(window).width());
	});
	
	$(".input-group-addon").on("click", function(){//搜索  
		mySearch();
	});
	$("#inputValue").val("");
});

	//刷新列表
	function refreshGrid(url){
		if(url){
			$("#equipmentGrid").jqGrid("setGridParam",{url:url,page:1}).trigger("reloadGrid");
		}else{
			$("#equipmentGrid").trigger("reloadGrid");//刷新Grid
		}
	}
	
	//增加车道设备
	function addOfLane(){
	    window.open("equipment_addOfLane.do", "I3");
	}
	
	//删除设备
	function deleteEquipment(equipmentId){
		if(!window.confirm("确认删除该设备吗？")) return;
		$.ajax({
			type: "POST",
			url: "equipment_ajaxRemove.do?equipmentId="+equipmentId,
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
	function mySearch(){
		var inputValue = encodeURIComponent($("#inputValue").val());
		var url = "equipment_ajaxOfLaneGrid.do?inputValue="+inputValue;
		$("#equipmentGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}
	
	//编辑设备
	function editEquipment(equipmentId){
		var editUrl = "equipment_editOfLane.do?equipment.id=" + equipmentId;
		window.parent.open(editUrl, "I3");
	}
</script>
<body style="overflow: hidden;">
<div class="lk-panel-reasarch">
  <div class="input-group">
    <input type="text" id="inputValue" placeholder="请输入查询条件" class="form-control"><span class="input-group-addon btn btn-primary">搜索</span>
  </div>
  <div class="input-btn">
    <div class="btn-group">
    	<input type="button" onclick="javascript:addOfLane();" class="btn btn-info" value="增加车道设备"/>
	</div>	
  </div>
</div>
<table width="100%" id="equipmentGrid" style="font-size: 14px; "></table>
</body>
</html>