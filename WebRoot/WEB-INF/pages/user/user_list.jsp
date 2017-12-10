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
	$("#userGrid").jqGrid({
		url:"user_ajaxGrid.do",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
            {name: "id",sortable:false,key:true,hidden:true},
            {name: "account",width:"100px",align:"center",sortable:false,label:"账号"},
            {name: "names",width:"100px",align:"center",sortable:false,label:"姓名"},
            {name: "gender",width:"40px",align:"center",sortable:false,label:"性别"},
            {name: "orgCodes",width:"120px",align:"center",sortable:false,label:"站点编号"},
            {name: "orgName",width:"180px",align:"center",sortable:false,label:"站点名称"},
            {name: "serials",width:"80px",align:"center",sortable:false,label:"顺序"},
            {name: "statusView",width:"74px",align:"center",sortable:false,label:"状态"},
            {name: "opt",width:"120px",align:"center",sortable:false,label:"操作"},
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
		$("#userGrid").setGridWidth($(window).width());
	});
	
	$(".input-group-addon").on("click", function(){//搜索  
		mySearch();
	});
	$("#inputValue").val("");
});
	/**删除用户 @*/
	function deleteUser(userId){
		if(!window.confirm("确认删除用户？"))return;
		$.ajax({
	 		url:"user_ajaxDelete.do?userId="+userId,
	 		type:"post",
	 		async:false,
	 		dataType:"text",
	 		success:function(data){
	 			refreshGrid();
	 		}
		});
	}
	
	/**编辑用户 @*/
	function editUser(userId){
		window.open("user_edit.do?user.id="+userId,"I3");
	}
	
	/**删除用户 @*/
	function deleteUser(userId){
		if(!window.confirm("确认删除用户？"))return;
		$.ajax({
	 		url:"user_ajaxDelete.do?userId="+userId,
	 		type:"post",
	 		async:false,
	 		dataType:"text",
	 		success:function(data){
	 			refreshGrid();
	 		}
		});
	}

	/** 刷新Grid @*/
	function refreshGrid(){
		$("#userGrid").trigger("reloadGrid");//刷新Grid
	}
	
	/** 搜索  @*/
	function mySearch(){
		var inputValue = encodeURIComponent($("#inputValue").val());
		var url = "user_ajaxGrid.do?inputValue="+inputValue;
		$("#userGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}

</script>
<body style="overflow: hidden;">

<div class="lk-panel-reasarch">
  <div class="input-group">
    <input type="text" id="inputValue" placeholder="请输入查询条件" class="form-control"><span class="input-group-addon btn btn-primary">搜索</span>
  </div>
  <div class="input-btn">
    <input type="button" onclick="window.open('user_add.do','I3')" class="btn btn-info" value="增加用户"/>
  </div>
</div>
<table width="100%" id="userGrid" style="font-size: 14px; "></table>
<div id="pager01"></div>
</body>
</html>