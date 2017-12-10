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
	$("#contrastVehicleGrid").jqGrid({
		url:"contrastVehicle_ajaxGrid.action",
	   	mtype:"post",
		datatype: "json",
		shrinkToFit: true,
		autowidth: true,
		height: "100%",
	   	colModel: [
            {name: "id",sortable:false,key:true,hidden:true},
            {name: "carNum",width:"100px",align:"center",sortable:false,label:"车牌号"},
            {name: "vehicleType",width:"150px",align:"center",sortable:false,label:"品牌"},
            {name: "names",width:"180px",align:"center",sortable:false,label:"车主"},
            {name: "idcard",width:"180px",align:"center",sortable:false,label:"身份证号"},
            {name: "passdate",width:"180px",align:"center",sortable:false,label:"采集时间"},
            {name: "action",width:"100px",align:"center",sortable:false,label:"处置"},
            {name: "tag",width:"300px",align:"center",sortable:false,label:"标签"},
            {name: "location",width:"180px",align:"center",sortable:false,label:"位置"},
            {name: "isChecked",width:"100px",align:"center",sortable:false,label:"状态",
            	formatter:function (cellvalue, options, rowObject ){
            		if("1" == cellvalue) return "<font color=green>已核查</font>";
            		return "<font color=red>未核查</font>";
				}
            },
            {name: "id",width:"60px",align:"center",sortable:false,label:"操作",
            	formatter:function (cellvalue, options, rowObject ){  
				    return "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"dealContrastVehicle('"+cellvalue+"');\">详细<\/button>";  
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
    		dealContrastVehicle(rowId);
    	}
	});
	
	$(window).resize(function (e) {
		$("#contrastVehicleGrid").setGridWidth($(window).width());
	});
	
	$(".input-group-addon").on("click", function(){//搜索  
		mySearch();
	});
	$("#inputValue").val("");
});
	
	/** 刷新Grid @*/
	function refreshGrid(){
		$("#contrastVehicleGrid").trigger("reloadGrid");//刷新Grid
	}
	
	/** 搜索  @*/
	function mySearch(){
		var startdate = $("#startDate").val();
		var enddate = $("#endDate").val();
		var inputValue = encodeURIComponent($("#inputValue").val());
		var url = "contrastVehicle_ajaxGrid.action?inputValue="+inputValue+"&startdate="+startdate+"&enddate="+enddate;
		$("#contrastVehicleGrid").jqGrid("setGridParam",{url: url,page:1}).trigger("reloadGrid");//刷新Grid
	}
	
	/**处理车辆预警信息 @*/
	function dealContrastVehicle(id){
		ymPrompt.win({
			message : "contrastVehicle_detail.action?contrastVehicle.id=" + id,
			width : 1300,
			height : 800,
			title : '预警车辆详细信息',
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


<table width="100%" id="contrastVehicleGrid" style="font-size: 14px; "></table>
<div id="pager01"></div>
</body>
</html>