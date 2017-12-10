<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<html>
<head>
  <base href="<%=basePath%>"/>
  <%@ include file = "/js/include/common.inc"%>
  <%@ include file = "/js/include/timepicker.inc"%>
  <script src="<%=basePath%>js/lib/Highcharts/highcharts.js"></script>
</head>
<script>
var content = "<p>男性：0</p><p>女性：0</p>";
$(document).ready(function(){
	$("[data-toggle='popover']").popover();

	$(".timepicker").datetimepicker({timeFormat: "HH:mm:ss"});
	var date = new Date();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
	    month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
	    strDate = "0" + strDate;
	}
	var currentdate = date.getFullYear()+"-"+month+"-"+strDate;
	$("#startDate").val(currentdate+" 00:00:00");
	$("#endDate").val(currentdate+" 23:59:59");
	
    equipmentfun();
    
    //默认加载当天的数据
    trafficfun(currentdate+" 00:00:00",currentdate+" 23:59:59");
    
    $("#imp_person").popover({
		trigger: "manual",
       	placement : "bottom",
       	html: "true",
       	content :  "<div id='popover_content'></div>",
       	animation: false
   	}).on("mouseenter", function () {
		var _this = this;
   		$(this).popover("show");
   		$("#popover_content").empty().append(content);
      	$(this).siblings(".popover").on("mouseleave", function () {
			$(_this).popover("hide");
		});
	}).on("mouseleave", function () {
   		var _this = this;
		setTimeout(function () {
		if (!$(".popover:hover").length) {
			$(_this).popover("hide")
			}
		}, 100);
	});
});

	function showlinefun(data){//折线图
		$("#container").highcharts({
	        chart: { type: "line" },
	        title: { text: data.ptitle },
	        subtitle: { text: "人员流量及预警监测数据" },
	        xAxis: { categories: eval(data.pcategories) },
	        yAxis: { title: { text: "数量" } },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true          // 开启数据标签
	                },
	                enableMouseTracking: false // 关闭鼠标跟踪，对应的提示框、点击事件会失效
	            }
	        },
	        series: eval(data.pseries)
	    });
	}

	function showcolumnfun(data){//柱图
		$("#container1").highcharts({
	        chart: { type: "column" },
	        title: { text: data.vtitle },
	        subtitle: { text: "车辆流量及预警监测数据" },
	        xAxis: { categories: eval(data.vcategories) },
	        yAxis: {  min: 0, title: { text: "数量" } },
	        tooltip: {
	            headerFormat: "<span style='font-size:12px'>{point.key}</span><table>",
	            pointFormat: "<tr><td style='color:{series.color};padding:0'>{series.name}: </td>" +
	            "<td style='padding:0'><b>{point.y:.0f}</b></td></tr>",
	            footerFormat: "</table>",
	            shared: true,
	            useHTML: true
	        },
	        series: eval(data.vseries)
	    });
	}


	/**加载安检设备*@*/
	function equipmentfun(){
		$.ajax({
	 		url: "chart_ajaxEquipment",
	 		type: "post",
	 		async: true,
	 		dataType: "json",
	 		success: function(data){
	 			$("#imp_lane").html(data.lane);//安检车道
			  //$("#imp_check").html(data.check);//安检设备
	 		}
		});
	}
	
	/**统计数据 @*/
	function statisticefun(){
		var startdate = $("#startDate").val();
		var enddate = $("#endDate").val();
		if("" == startdate)return;
		if("" == enddate)return;
		trafficfun(startdate,enddate);
	}
	
	/**统计数据 @*/
	function trafficfun(startdate,enddate){
		$.ajax({
	 		url: "chart_ajaxTraffic",
	 		type: "post",
	 		data: {"startsh": startdate, "endstr": enddate},//初始化取数据条数
	 		async: true,
	 		dataType: "json",
	 		success: function(data){
	 			$("#imp_person").empty();
	 			$("#imp_person").html(data.person);
				$("#imp_vehicle").html(data.vehicle);
				$("#imp_vehicle_shunt").html(data.vehicleShunt);
	 			showlinefun(data);
	 			showcolumnfun(data);
	 			content = "<p>男性："+data.male+"</p><p>女性："+data.female+"</p>";
	 		}
		});
	}
</script>
<style>
.viewTable{ width: 100%;border-collapse:collapse;font-size: 1.5em;}
.viewTable th{ height: 40px;text-align: center;}
.viewTable td{ height: 50px;text-align: center;}

</style>
<body style="overflow-x: hidden; min-height: 900px;">
<table class="viewTable bg-info" >
  <tr>
    <td width="590px">日期：
      <input type="text" id="startDate" readonly="readonly" class="timepicker" style="width: 215px;display: inline; "/>
      --
      <input type="text" id="endDate" readonly="readonly" class="timepicker" style="width: 215px;display: inline;"/>
    </td>
    <td width="100px">
      <input type="button" onclick="statisticefun()" class="btn btn-info btn-block" value="统计"/>
    </td>
    <td></td>
  </tr>
</table>
<div class="row" style="margin-top: 20px;margin-left: 20px;">
  <div class="col-xs-12 col-md-6 col-lg-3">
    <div class="panel panel-blue panel-widget ">
      <div class="row ">
	    <div class="col-sm-3 col-lg-5 widget-left">
		  <em class="glyphicon glyphicon-road glyphicon-l"></em>
	    </div>
		<div class="col-sm-9 col-lg-7 widget-right">
		  <div class="large"><label id="imp_lane">0</label>条</div>
		  <div class="text-muted">安检车道</div>
		</div>
      </div>
    </div>
  </div>
  <div class="col-xs-12 col-md-6 col-lg-3">
    <div class="panel panel-teal panel-widget">
      <div class="row ">
        <div class="col-sm-3 col-lg-5 widget-left">
		  <em class="glyphicon glyphicon-hdd glyphicon-l"></em>
		</div>
		<div class="col-sm-9 col-lg-7 widget-right">
		  <div class="large"><label id="imp_vehicle_shunt">0</label>辆</div>
		  <div class="text-muted">前置卡口</div>
	    </div>
      </div>
    </div>
  </div>
  <div class="col-xs-12 col-md-6 col-lg-3">
	<div class="panel panel-red panel-widget">
	  <div class="row">
	    <div class="col-sm-3 col-lg-5 widget-left">
		  <em class="glyphicon icon-truck glyphicon-l"></em>
		</div>
		<div class="col-sm-9 col-lg-7 widget-right">
		  <div class="large"><label id="imp_vehicle">0</label>辆</div>
		  <div class="text-muted">车道流量</div>
		</div>
	  </div>
    </div>
  </div>
  <div class="col-xs-12 col-md-6 col-lg-3">
    <div class="panel panel-orange panel-widget">
      <div class="row ">
        <div class="col-sm-3 col-lg-5 widget-left">
		  <em class="glyphicon glyphicon-user glyphicon-l" ></em>
		</div>
		<div class="col-sm-9 col-lg-7 widget-right">
		  <div class="large">
		    <label id="imp_person">0</label>名
		  </div>
		  <div class="text-muted">数据门流量</div>
		</div>
	  </div>
    </div>
  </div>
</div>
<div class="row">
  <div class="col-lg-5">
    <div id="container" style="height: 700px;"></div>
  </div>
  <div class="col-lg-7">
    <div id="container1" style="height: 700px;"></div>
  </div>
</div>
</body>
<style>
.large        { font-size: 2em;}
.color-gray   { color: #5f6468; }
.color-blue   { color: #30a5ff; }
.color-teal   { color: #1ebfae; }
.color-orange { color: #ffb53e; }
.color-red    { color: #f9243f; }
.glyphicon-l  { font-size: 3em; }
/*Panels*/
.panel        { border: 0; }
.panel-blue   { background: #30a5ff; color: #fff; }
.panel-teal   { background: #1ebfae; color: #fff; }
.panel-orange { background: #ffb53e; color: #fff; }
.panel-red    { background: #f9243f; color: #fff; }
.panel-widget { padding: 0; position: relative; background: #fff; }

.widget-right .text-muted {	color: #9fadbb; }
.widget-right .large { color: #5f6468; }

.panel-blue .widget-left { background: #30a5ff; color: #fff; }
.panel-teal .widget-left { background: #1ebfae; color: #fff; }
.panel-orange .widget-left { background: #ffb53e; color: #fff; }
.panel-red .widget-left { background: #f9243f; color: #fff; }


.widget-left {
	height: 80px;
	padding-top: 15px;
	text-align: center;
	border-top-left-radius: 4px;
	border-bottom-left-radius: 4px;
}

.widget-right {
	text-align: left;
	line-height: 1.6em;
	margin: 0px;
	padding: 20px;
	height: 80px;
	color: #999;
	font-weight: 300;
	background: #fff;
	border-top-right-radius: 4px;
	border-bottom-right-radius: 4px;
}

@media (max-width: 768px) {
  .widget-right {
	width: 100%;
	margin: 0;
	text-align: center;
	border-top-left-radius: 0px;
	border-top-right-radius: 0px;
	border-bottom-left-radius: 4px;
	border-bottom-right-radius: 4px;
  }
	
  .widget-left {
	border-top-left-radius: 4px;
	border-top-right-radius: 4px;
	border-bottom-left-radius: 0px;
	border-bottom-right-radius: 0px;
  }
}
</style>
</html>
