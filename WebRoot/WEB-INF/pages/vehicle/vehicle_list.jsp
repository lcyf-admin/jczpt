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
<script>
$(function(){
	$(".timepicker").datetimepicker({timeFormat: "HH:mm:ss"});
	$("#priorDataVehicleGrid").jqGrid({
		url:"vehicle_ajaxGrid.action",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
            {name: "ID",sortable:false,key:true,hidden:true},
            {name: "CARNUM",width:"100px",align:"center",sortable:false,label:"车牌号"},
            {name: "PLATECOLOR",width:"100px",align:"center",sortable:false,label:"车牌颜色"},
            {name: "CARDNUM",width:"100px",align:"center",sortable:false,label:"身份证号"},
            {name: "PASSDATE",width:"100px",align:"center",sortable:false,label:"时间"},
            {name: "NAMES",width:"100px",align:"center",sortable:false,label:"位置"},
            {name: "ID",width:"40px",align:"center",sortable:false,label:"操作",
            formatter:function (cellvalue, options, rowObject ){  
				    return "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"vehicleDetail('"+cellvalue+"');\">查看<\/button>";  
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
    		vehicleDetail(rowId);
    	}
	});
	
	$(window).resize(function (e) {
		$("#priorDataVehicleGrid").setGridWidth($(window).width());
	});
	
	$(".input-group-addon").on("click", function(){//搜索  
		mySearch();
	});
	$("#inputValue").val("");
});
	
	/** 刷新Grid @*/
	function refreshGrid(){
		$("#priorDataVehicleGrid").trigger("reloadGrid");//刷新Grid
	}
	
	/** 搜索  @*/
	function mySearch(){
		var startdate = $("#startDate").val();
		var enddate = $("#endDate").val();
		var monthId = $("#monthId").val();
		var passengerIdcard = $("#passengerIdcard").val();
		var inputValue = encodeURIComponent($("#inputValue").val());
		var url = "vehicle_ajaxGrid.action?inputValue="+inputValue+
				  "&startdate="+startdate+
				  "&enddate="+enddate+
				  "&passengerIdcard="+passengerIdcard+
				  "&monthId="+monthId;
		$("#priorDataVehicleGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}
	
	/**车辆详细 @*/
	function vehicleDetail(id){
		ymPrompt.win({
			message : "vehicle_detail.action?vehicle.id="+id,
			width : 1050,
			height : 850,
			title : '车辆详情',
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

    <td width="120px;" style="text-align: right;">乘客身份证号：</td>
    <td width="180px;">
      <input type="text" id="passengerIdcard" placeholder="请输入乘客身份证号" class="form-control">
    </td>
    <td width="90px;" style="text-align: right;">其他条件：</td>
    <td>
      <div class="lk-panel-reasarch" style="margin-left:20px">
       <div class="input-group">
          <input type="text" id="inputValue" placeholder="请输入查询条件" class="form-control"><span class="input-group-addon btn btn-primary">搜索</span>
        </div>
      </div>
    </td>	
  </tr>
</table>


<table width="100%" id="priorDataVehicleGrid" style="font-size: 14px; "></table>
<div id="pager01"></div>
</body>
</html>