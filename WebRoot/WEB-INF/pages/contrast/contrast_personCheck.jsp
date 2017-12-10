<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<title>车辆预警核录表</title>
<%@ include file="/js/include/common.inc"%>
<%@ include file = "/js/include/validator.inc"%>
<%@ include file = "/js/include/timepicker.inc"%>
</head>
<body>
<div align="center" style="margin:20px 0;">
	<h3><b>人员预警核录表</b></h3>
</div>
<div class="lk-panel">
  <form class="form-horizontal" role="form" id="form" action="">
  	<input type="hidden" name="checkPerson.taskId" value="${contrastPerson.id}" />
    <table class="showTable" border="1">
      <tr>
      	<th>是否预警对象：</th>
      	<td>
      	  <input type="radio" id="isContrast1" name="checkPerson.isContrast" value="1" checked>是&nbsp;&nbsp;&nbsp;
      	  <input type="radio" id="isContrast0" name="checkPerson.isContrast" value="0">否
      	</td>
      </tr>
      <tbody id="detail"> 
        <tr>
      	  <th>预警类型：</th>
      	  <td>
      	    <input type="radio" name="checkPerson.yjType" value="人证合一" checked>人证合一&nbsp;&nbsp;&nbsp;
      	    <input type="radio" name="checkPerson.yjType" value="数据门">数据门
      	  </td>
        </tr>
	    <tr>
	      <th>处置措施：</th>
	      <td>
	          <input type="radio" id="action0" name="checkPerson.action" value="信息采集" checked>信息采集&nbsp;&nbsp;&nbsp;
      	      <input type="radio" id="action1" name="checkPerson.action" value="滞留审查">滞留审查&nbsp;&nbsp;&nbsp;
      	      <input type="radio" id="action2" name="checkPerson.action" value="立即抓捕">立即抓捕
	      </td>
	    </tr>
	  </tbody>
	  <tbody id="XXCJ">
	    <tr>
	     <th>人员检查：</th>
	     <td>
	     	<table border="0" width="100%">
	            <tr>
	              <th class="cth"><span style = "color:red">*</span>身份证号：</th>
	              <td class="ctd"><input class="form-control" type="text" name="checkPerson.idcard" id="idcard_person" placeholder="请输入身份证号--必填"/></td>
	              <th  class="cth">手机号：</th>
	              <td class="ctd"><input class="form-control" type="text" name="checkPerson.phonenum" placeholder="请输入手机号"/></td>
	              <th class="cth">人证核验结果：</th>
	              <td class="ctd">
	                <input type="radio" name="checkPerson.match" value="1" checked>一致&nbsp;&nbsp;&nbsp;
	      		    <input type="radio" name="checkPerson.match" value="0">不一致
	              </td>
	            </tr>
	            <tr>
	              <th class="cth">手机检查：</th>
	              <td class="ctd">
	                <input type="radio"  name="checkPerson.isDubious" value="0" checked>无可疑&nbsp;&nbsp;&nbsp;
	      	        <input type="radio" name="checkPerson.isDubious" value="1">有可疑
	      	      </td>
	      	      <th class="cth">可疑描述：</th>
	              <td class="ctd" colspan="2" >
	                <input class="form-control" type="text" name="checkPerson.finds" placeholder="请输入可疑描述"/>
	              </td>
	              <td class="ctd remark">(可疑URL、暴恐音视频等)</td>
	            </tr>
            </table>
	      </td>
	    </tr>
	    <tr>
	      <th>同行人员信息采集：</th>
	      <td style="padding: 2px;" id="peersTD">&nbsp;&nbsp;
	        <button type="button" class="btn btn-primary btn-ms" onclick="addPeers();">增加同行人</button>
	      </td>
	    </tr>
	    <tr>
	      <th>同行车辆信息采集：</th>
	      <td style="padding: 2px;" id="vehiclesTD">&nbsp;&nbsp;
	        <button type="button" class="btn btn-primary btn-ms" onclick="addVehicles();">增加同行车</button>
	      </td>
	    </tr>
	  </tbody>
	  <tbody id="ZLSC" style="display: none;" >
	    <tr>
	      <th>是否请假：</th>
	      <td>
	        <input type="radio" name="checkPerson.isVacation" value="1" checked>已请假&nbsp;&nbsp;&nbsp;
      	    <input type="radio" name="checkPerson.isVacation" value="0">未请假  
          </td>
	    </tr>
	    <tr>
          <th>人员去向：</th>
          <td><input class="form-control" type="text" name="checkPerson.direction" placeholder="请输入人员去向"/></td>
        </tr>
        <tr>
          <th>目的地来由：</th>
          <td><input class="form-control" type="text" name="checkPerson.directionReason" placeholder="请输入目的地来由"/></td>
        </tr>
        <tr>
	      <th>管辖片区民警联系方式：</th>
	      <td><input class="form-control" type="text" name="checkPerson.copnum" placeholder="请输入管辖片区民警联系方式"/></td>
	    </tr>
	    <tr>
	      <th>前往内地原因：</th>
	      <td><input class="form-control" type="text" name="checkPerson.reason" placeholder="○经商 ○上学 ○其他____ ○无法合理解释"/></td>
	    </tr>
	    <tr>
	      <th>返回时间：</th>
	      <td><input type="text" readonly="readonly" name="checkPerson.backtime" class="form-control timepicker" style="width: 215px;display: inline; "/>
	    </tr>
	    <tr>
	      <th>近一年是否离开：</th>
	      <td>
	        <input type="radio" name="checkPerson.isLeave" value="0" checked>否&nbsp;&nbsp;&nbsp;
      	    <input type="radio" name="checkPerson.isLeave" value="1">是
          </td>
	    </tr>
      </tbody>
        <tr>
	      <th>核查结论：</th>
	      <td>
	        <select class="form-control" name="checkPerson.hcjl" >
	          <option value="1">不能排除嫌疑继续布控</option>
	          <option value="2">排除嫌疑建议撤控</option>
	        </select>
          </td>
	    </tr>
    </table>
    <div class="form-group" align="center" style="margin-top: 20px;">
      <button type="button" class="btn btn-default btn-lg" onclick="window.history.back(-1);">返&nbsp;&nbsp;回</button>
      &nbsp;&nbsp;&nbsp;&nbsp;
	  <button type="button" class="btn btn-success btn-lg" onclick="submitForm();" >提&nbsp;&nbsp;交</button>
    </div>
  </form>
</div>
</body>
<script type="text/javascript">
	function addPeers(){//添加同行人员
		var uuid = generateUUID();
		var passengerTable = "<table border='0' class='ctable' width='100%'>"+
            "<tr>"+
              "<th class='cth'>身份证号：</th>"+
              "<td class='ctd'><input class='form-control' type='text' name='peers_idcard"+uuid+"' placeholder='请输入身份证号'/></td>"+
              "<th class='cth'>手机号：</th>"+
              "<td class='ctd'><input class='form-control' type='text' name='peers_phonenum"+uuid+"' placeholder='请输入手机号'/></td>"+
              "<td class='ctd'>"+
                "<button type='button' class='btn btn-danger btn-ms' onclick='deletedPeers(this);'>删除</button>"+
              "</td>"+
            "</tr>"+
          "</table>";
		$("#peersTD").append(passengerTable);
	};
	
	/**删除同行人 @*/
	function deletedPeers(t){
		$(t).parent().parent().parent().remove();
	}
	
	function addVehicles(){//添加同行人员
		var uuid = generateUUID();
		var passengerTable = "<table border='0' class='ctable' width='100%'>"+
            "<tr>"+
              "<th class='cth'>车牌号：</th>"+
              "<td class='ctd'><input class='form-control' type='text' name='peerVehicle_carNum"+uuid+"' placeholder='请输入车牌号'/></td>"+
              "<th class='cth'>车牌颜色：</th>"+
              "<td class='ctd'><select class='form-control' type='text' name='peerVehicle_plateColor"+uuid+"'><option>蓝色</option><option>黄色</option><option>白色</option><option>黑色</option></select></td>"+
              "<td class='ctd'>"+
              "</td>"+           
            "</tr>"+
            "<tr>"+
              "<th class='cth'>车身颜色：</th>"+
              "<td class='ctd'><input class='form-control' type='text' name='peerVehicle_color"+uuid+"' placeholder='请输入车身颜色'/></td>"+
              "<th class='cth'>车辆类型：</th>"+
              "<td class='ctd'><input class='form-control' type='text' name='peerVehicle_type"+uuid+"' placeholder='请输入车辆类型'/></td>"+
              "<td class='ctd'>"+
                "<button type='button' class='btn btn-danger btn-ms' onclick='deleteVehicles(this);'>删除</button>"+
              "</td>"+
            "</tr>"+
          "</table>";
		$("#vehiclesTD").append(passengerTable);
	};
	
	/**删除同行车 @*/
	function deleteVehicles(t){
		$(t).parent().parent().parent().remove();
	}
	
$(function(){
	$(".timepicker").datetimepicker({timeFormat: ""});
	/////////////////////////////////	
	$("#isContrast0").bind("click", function(){
		$("#detail").hide();
		$("#XXCJ").hide();
		$("#ZLSC").hide();
	});
	$("#isContrast1").bind("click", function(){
		$("#detail").show();
		var value = $(":radio[name='checkPerson.action']:checked").val();
		if(value == "信息采集"){
			$("#XXCJ").show();
			$("#ZLSC").hide();
		}else if(value == "滞留审查"){
			$("#XXCJ").hide();
			$("#ZLSC").show();
		}else{
			$("#XXCJ").hide();
			$("#ZLSC").hide();
		}
	});
	///////////////////////////////////////////////
	$("#action0").bind("click", function(){
		$("#XXCJ").show();
		$("#ZLSC").hide();
	});
	$("#action1").bind("click", function(){
		$("#XXCJ").hide();
		$("#ZLSC").show();
	});
	$("#action2").bind("click", function(){
		$("#XXCJ").hide();
		$("#ZLSC").hide();
	});
	///////////////////////////////////////////////////
	
	//表单验证
	$("#form").bootstrapValidator({
		message: "This value is not valid",
        feedbackIcons: {
            valid: "glyphicon glyphicon-ok",
            invalid: "glyphicon glyphicon-remove",
            validating: "glyphicon glyphicon-refresh"
        }
	});
});

	//保存信息
   	function submitForm(){
   		//身份证号校验
	   	if($("#action0")[0].checked == true){
			idcard = $("#idcard_person").val();
			var result=idcard.match(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/);
			if(idcard == null || idcard == undefined || idcard == ''){
				alert("身份证号不能为空");
				return;
			} else if (result==null){
				alert("身份证号不合法");
				return;
			}
		}	
   		var $form = $("#form");
	    var data = $form.data("bootstrapValidator");
	    if (!data) return false;
	    data.validate();//修复记忆的组件不验证
        if (!data.isValid()) return false;//无效
   		$.ajax({
   			type: "POST",
   			url: "checkPerson_ajaxAdd",
   			async: false,
   			data: $("#form").serialize(),
   			success: function(data){
   				window.location.href = "contrastPerson_detail?contrastPerson.id=${contrastPerson.id}";
   			}
   		});
   	}
</script>
<style>
.lk-panel{
	border-radius:10px;
	padding: 0;
	margin: 5px;
}
.showTable   { width: 100%;border-collapse:collapse;font-size: 1.2em;margin-bottom:5px;}
.showTable th{ height: 50px;line-height:50px; text-align: right;padding-right: 20px; width: 220px; }
.showTable td{ height: 50px;line-height:50px; text-align: left;padding: 0 20px; }

.ctable      { width: 100%;border: 1px solid #ccc;margin-bottom: 5px;}
.cth         { width: 120px !important;padding-right: 0px !important;font-size: 13px; !important;}
.ctd		 { padding: 0 10px !important;}
.remark      { font-size: 0.8em;}  
</style>
</html>