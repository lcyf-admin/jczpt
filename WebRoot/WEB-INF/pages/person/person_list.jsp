<%@page import="cn.lhkj.commons.base.BaseDataCode"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	List<String> monthList = BaseDataCode.getMonthList();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head> 
  <base href="<%=basePath%>"/>
  <%@ include file = "/js/include/common.inc"%>
  <%@ include file = "/js/include/jqgrid.inc"%>
  <%@ include file = "/js/include/ymPrompt.inc"%>
  <%@ include file = "/js/include/timepicker.inc"%>
</head>
<style>
.input-group-export {
    padding: 6px 12px;
    font-size: 14px;
    font-weight: 400;
    color: #555;
    text-align: center;
    background-color: #eee;
    border: 1px solid #ccc;
    border-radius: 4px;
}
</style>
<script>
$(function(){
    $(".timepicker").datetimepicker({timeFormat: "HH:mm:ss"});
	$("#priorDatapersonGrid").jqGrid({
		url:"person_ajaxGrid.action",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
            {name: "id",sortable:false,key:true,hidden:true},
            {name: "names",width:"100px",align:"center",sortable:false,label:"姓名"},
            {name: "gender",width:"50px",align:"center",sortable:false,label:"性别"},
            {name: "idcard",width:"100px",align:"center",sortable:false,label:"身份证号"},
            {name: "location",width:"100px",align:"center",sortable:false,label:"位置"},
            {name: "captureTime",width:"100px",align:"center",sortable:false,label:"时间"},
            {name: "isCheck",width:"40px",align:"center",sortable:false,label:"违禁品",
            	formatter:function (cellvalue, options, rowObject ){
            		if("0" == cellvalue) return "<font color=green>未录入</font>";
            		return "<font color=red>"+cellvalue+"</font>";
				}
            },
            {name: "id",width:"40px",align:"center",sortable:false,label:"操作",
            formatter:function (cellvalue, options, rowObject ){  
					return "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"personDetail('"+cellvalue+"');\">详情<\/button>"/* + 
						   "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"addContraband('"+cellvalue+"');\">违禁品录入<\/button>" */;            
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
    	onSelectRow:function(rowId,statues){
    		//personDetail(rowId);
    	},
    	
    	ondblClickRow:function(rowId,statues){
    		personDetail(rowId);
    	}
	});
	
	$(window).resize(function (e) {
		$("#priorDatapersonGrid").setGridWidth($(window).width());
	});
	
	$(".input-group-addon").on("click", function(){//搜索  
		mySearch();
	});
	
	$(".input-group-export").on("click", function(){//导出 
		myExport();
	});	
	
	$("#inputValue").val("");
});
	
	/** 刷新Grid @*/
	function refreshGrid(){
		$("#priorDatapersonGrid").trigger("reloadGrid");//刷新Grid
	}
	
	/** 搜索  @*/
	function mySearch(){
		var startdate = $("#startDate").val();
		var enddate = $("#endDate").val();
		var monthId = $("#monthId").val();
		var contrabandName = $("#contrabandName").val();
		var inputValue = encodeURIComponent($("#inputValue").val());
		var url = "person_ajaxGrid.action?inputValue="+inputValue+
		          "&startdate="+startdate+
		          "&enddate="+enddate+
		          "&contrabandName="+contrabandName+
		          "&monthId="+monthId;
		$("#priorDatapersonGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}

	/** 导出  @*/
	function myExport(){
		var startdate = $("#startDate").val();
		var enddate = $("#endDate").val();
		var monthId = $("#monthId").val();
		var contrabandName = $("#contrabandName").val();
		var inputValue = encodeURIComponent($("#inputValue").val());
		$.ajax({
			type : "POST",
			url : "person_ajaxExist.action",
			async : false,
			data : {inputValue:inputValue,startdate:startdate,enddate:enddate,contrabandName:contrabandName,monthId:monthId},
			success : function(data) {
				/////data为1有数据，为0无数据；
				if(data == "1"){
					window.location.href ="person_ajaxExport.action?inputValue="+inputValue+
							          "&startdate="+startdate+
							          "&enddate="+enddate+
							          "&contrabandName="+contrabandName+
							          "&monthId="+monthId;				
				}else{
					alert("当前条件暂无数据");
				}
			}
		});

	}

	/**人员详细 @*/
	function personDetail(id){
		ymPrompt.win({
			message : "person_detail.action?person.id="+id,
			width : 900,
			height : 800,
			title : '人员详细',
			maxBtn : true,
			minBtn : true,
			closeBtn : true,
			iframe : true
		});
	}
	
	
	/**违禁品录入 @*/
	function addContraband(id){
		ymPrompt.win({
			message : "person_contraband.action?person.id="+id,
			width : 900,
			height : 500,
			title : '违禁品录入',
			maxBtn : false,
			minBtn : false,
			closeBtn : true,
			iframe : true
		});
	}
</script>
<body style="overflow: hidden;">
<table width="100%" border="0">
  <tr>
    <td width="55px;" style="text-align: right;">月份：</td>
    <td width="100px;">
      <select class="form-control" id="monthId" onchange="mySearch();" >
      <%for(String value : monthList){ %>
        <option><%=value%></option>
      <%} %>
      </select>
    </td>
    <td width="55px;" style="text-align: right;">时间：</td>
    <td width="365px;">
      <input type="text" id="startDate" readonly="readonly" class="timepicker form-control" style="width: 170px;display: inline;background-color: #fff; "/>
      --<input type="text" id="endDate" readonly="readonly" class="timepicker form-control" style="width: 170px;display: inline;background-color: #fff; "/>
    </td>
    
    <td width="100px;" style="text-align: right;">违禁品名称：</td>
    <td width="150px;">
      <select class="form-control" id="contrabandName" >
      	<option></option>
        <option>枪支弹药</option>
        <option>管制刀具</option>
        <option>易燃易爆</option>
        <option>毒品</option>
        <option>液体</option>
        <option>其他</option>
      </select>
    </td>  
    
    <td width="200px">
      <div class="lk-panel-reasarch" style="margin-left:20px">
        <div class="input-group">
          <input type="text" id="inputValue" placeholder="请输入查询条件" class="form-control"><span class="input-group-addon btn btn-primary">搜索</span>
        </div>
      </div>
    </td>	
    
    <td>
      <div style="margin-left:20px;">
    	<button class="btn btn-primary input-group-export">导出数据</button>    	
      </div>
    </td>
  </tr>
</table>

<table width="100%" id="priorDatapersonGrid" style="font-size: 14px; "></table>
<div id="pager01"></div>
</body>
</html>