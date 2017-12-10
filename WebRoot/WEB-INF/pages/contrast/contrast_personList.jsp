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
  <%@ include file = "/js/include/ymPrompt.inc"%>
  <script src="<%=basePath%>js/lib/My97DatePicker/WdatePicker.js"></script>
</head>
<script>
$(function(){
	$("#contrastPersonGrid").jqGrid({
		url:"contrastPerson_ajaxGrid.action",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
	   		{name: "id",sortable:false,key:true,hidden:true},
            {name: "names",width:"200px",align:"center",sortable:false,label:"姓名"},
            {name: "gender",width:"70px",align:"center",sortable:false,label:"性别"},
            {name: "idcard",width:"180px",align:"center",sortable:false,label:"身份证号"},
            {name: "location",width:"140px",align:"center",sortable:false,label:"位置"},
            {name: "captureTime",width:"180px",align:"center",sortable:false,label:"采集时间"},
            {name: "tag",width:"280px",align:"center",sortable:false,label:"标签"},
            {name: "action",width:"174px",align:"center",sortable:false,label:"处置手段"},
            {name: "source",width:"80px",align:"center",sortable:false,label:"来源"},
            {name: "isCheckedView",width:"80px",align:"center",sortable:false,label:"状态"},
            {name: "id",width:"40px",align:"center",sortable:false,label:"操作",
            	formatter:function (cellvalue, options, rowObject ){  
				    return "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"dealWarnPerson('"+cellvalue+"');\">详细<\/button>";  
				}
            }
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
    	onSelectRow:function(rowId,statues){},
    	ondblClickRow:function(rowId,statues){
    		dealWarnPerson(rowId);
    	}
	});
	
	$(window).resize(function (e) {
		$("#contrastPersonGrid").setGridWidth($(window).width()-20);
	});
	
	$(".input-group-addon").on("click", function(){//搜索  
		mySearch();
	});
	$("#inputValue").val("");
});
	
	/** 刷新Grid @*/
	function refreshGrid(){
		$("#contrastPersonGrid").trigger("reloadGrid");//刷新Grid
	}
	
	/** 搜索  @*/
	function mySearch(){
		var startdate = $("#startDate").val();
		var enddate = $("#endDate").val();
		var inputValue = encodeURIComponent($("#inputValue").val());
		var url = "contrastPerson_ajaxGrid.action?inputValue="+inputValue+"&startdate="+startdate+"&enddate="+enddate;
		$("#contrastPersonGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}
	
	/**处理人员预警信息 @*/
	function dealWarnPerson(id){
		ymPrompt.win({
			message : "contrastPerson_detail.action?contrastPerson.id=" + id,
			width : 1300,
			height : 780,
			title : '预警人员详细信息',
			maxBtn : true,
			minBtn : true,
			closeBtn : true,
			iframe : true
		});
	}

</script>
<body style="overflow: hidden;">
<table width="100%" border="0">
	<tr>
		<td width="10px;"></td>
		<td width="430px;">
			<div>
				时间：
				<input id="startDate" class="Wdate" type="text" style="width:180px;height:30px;" onFocus="var endDate=$dp.$('endDate');WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
			  	--
			  	<input id="endDate" class="Wdate" type="text" style="width:180px;height:30px;" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'})"/>
			</div>
		</td>
		<td>
			<div class="lk-panel-reasarch">
			  <div class="input-group">
			    <input type="text" id="inputValue" placeholder="请输入查询条件" class="form-control"><span class="input-group-addon btn btn-primary">搜索</span>
			  </div>
			</div>
		</td>	
	</tr>
</table>

<div style="margin: 0 10px;">
	<table width="100%" id="contrastPersonGrid" style="font-size: 14px; "></table>
	<div id="pager01"></div>
</div>
</body>
</html>