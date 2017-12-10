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
	$("#transferGrid").jqGrid({
		url:"transfer_ajaxGrid.action",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
            {name: "id",sortable:false,key:true,hidden:true},
            {name: "tranNO",width:"280px",align:"center",sortable:false,label:"通讯编号"},
            {name: "ver",width:"90px",align:"center",sortable:false,label:"接口版本"},
            {name: "tranType",width:"90px",align:"center",sortable:false,label:"通讯类别"},
            {name: "tranResult",width:"65px",align:"center",sortable:false,label:"状态"},
            {name: "sendTime",width:"180px",align:"center",sortable:false,label:"调用时间"},
            {name: "tranMsg",width:"270px",align:"center",sortable:false,label:"描述"},
            {name: "opt",width:"80px",align:"center",sortable:false,label:"操作"}
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
		$("#transferGrid").setGridWidth($(window).width());
	});
	
	$(".input-group-addon").on("click", function(){//搜索  
		mySearch();
	});
	$("#inputValue").val("");
});
	/**跳转到详细界面  @*/
	function detail(id){
		window.location.href = "transfer_detail.action?transfer.id="+id;
	}

	/** 刷新Grid @*/
	function refreshGrid(){
		$("#transferGrid").trigger("reloadGrid");//刷新Grid
	}
	
	/** 搜索  @*/
	function mySearch(){
		var inputValue = encodeURIComponent($("#inputValue").val());
		var url = "transfer_ajaxGrid.action?inputValue="+inputValue;
		$("#transferGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}

</script>
<body style="overflow: hidden;">

<div class="lk-panel-reasarch">
  <div class="input-group">
    <input type="text" id="inputValue" placeholder="请输入查询条件" class="form-control"><span class="input-group-addon btn btn-primary">搜索</span>
  </div>
</div>
<table width="100%" id="transferGrid" style="font-size: 14px; "></table>
<div id="pager01"></div>
</body>
</html>