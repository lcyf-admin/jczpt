<%@page import="cn.lhkj.commons.util.StringUtil"%>
<%@page import="cn.lhkj.commons.entity.SessionBean"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	SessionBean user = (SessionBean)session.getAttribute("SESSION_BEAN");
	String stationId = StringUtil.trim(user.getStationId());
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<title>车辆预警核录表</title>
<%@ include file="/js/include/common.inc"%>
<%@ include file = "/js/include/validator.inc"%>
</head>
<body>
<div align="center" style="margin:20px 0;">
	<h3><b>车辆预警核录表</b></h3>
</div>
<div class="lk-panel">
  <form class="form-horizontal" role="form" id="form" action="">
  	<input type="hidden" name="checkVehicle.taskId" value="${contrastVehicle.id}" />
  	<input type="hidden" name="checkVehicle.carNum" value="${contrastVehicle.carNum}" />
    <table class="showTable" border="1">
      <tr>
        <th>预警描述：</th>
        <td style="padding: 0;">&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" id="yjType0" name="checkVehicle.yjType" value="二手车" checked>二手车预警&nbsp;&nbsp;&nbsp;
      	  <input type="radio" id="yjType1" name="checkVehicle.yjType" value="布控对象车辆">布控对象车辆预警
  	    </td>
      </tr>
      <tr>
        <th>车辆信息采集：</th>
        <td style="padding: 0;">
          <table border="0" width="100%">
            <tr>
              <th class="cth">车牌颜色：</th>
              <td class="ctd" style="width: 251px;">
                <select class="form-control" type="text" name="checkVehicle.platecolor">
                  <option>蓝色</option><option>黄色</option><option>白色</option><option>黑色</option>
                </select>
              </td>
              <th class="cth">车身颜色：</th>
              <td class="ctd"><input class="form-control" type="text" name="checkVehicle.vehiclecolor" placeholder="请输入车身颜色"/></td>
              <th class="cth">车辆类型：</th>
              <td class="ctd"><input class="form-control" type="text" name="checkVehicle.vehicletype" placeholder="请输入车辆类型"/></td>
            </tr>
          </table>
  	    </td>
      </tr>
      <tr>
        <th>车辆信息采集：</th>
        <td style="padding: 0;">
          <table border="0" width="100%">
            <tr>
              <th class="cth">车辆性质：</th>
              <td class="ctd">
	          <select class="form-control" style="width: 300px;display: inline;" id="explains" name="checkVehicle.vnature">
	      	    <option value="1" selected>私家车</option>
	      	    <option value="2">运营车</option>
	      	    <option value="3">党政军企事业车辆</option>
	      	  </select>
	      	  </td>
             
              <th class="cth">车辆所属单位：</th>
              <td class="ctd"><input class="form-control" type="text" name="checkVehicle.vunit" placeholder="请输入车辆所属单位"/></td>
            </tr>
				 <th class="cth">是否再次核查：</th>
				 <td class="ctd">
				   <input type="radio"  name="checkVehicle.acheck" value="1" checked id="acheck1">是&nbsp;&nbsp;&nbsp;
				  <input type="radio" name="checkVehicle.acheck" value="0" id="acheck0">否
				</td>
	      	    <th class="cth" id="akeason_title">*不再核查原因：</th>  
		        <td class="ctd" colspan="2">
		          <select class="form-control" style="width: 200px;display: inline;" id="akeason" name="checkVehicle.akeason" onchange="changeAkeason(this.value)" >
		          	<option value="0"></option>
		      	    <option value="1">借用亲属车辆</option>
		      	    <option value="2">借用社会关系车辆</option>
		      	    <option value="3">正在办理过户车辆</option>
		      	    <option value="4">租赁公司</option>
		      	    <option value="5">企事业单位车辆</option>
		      	    <option value="6">其他</option>
		      	  </select>
		      	</td>  
	      	
		      	<th class="cth" id="acontent_title">*其他原因：</th>
	            <td class="ctd" colspan="2" >
	               <input class="form-control" type="text" name="checkVehicle.acontent" id="acontent" placeholder="请输入不再核查其他原因"/>
	        	</td>            
          </table>
  	    </td>
      </tr>
   <%if(stationId.indexOf("653101") != -1){ %>
      <tr>
        <th>车辆信息采集：</th>
        <td style="padding: 0;">
          <table border="0" width="100%">
            <tr>
              <th class="cth">车辆去向：</th>
              <td class="ctd"><input class="form-control" type="text" name="checkVehicle.direction" placeholder="请输入车辆去向"/></td>
              <th class="cth">目的地：</th>
              <td class="ctd"><input class="form-control" type="text" name="checkVehicle.destination" placeholder="请输入目的地"/></td>
              <th class="cth">滞留时间：</th>
              <td class="ctd"><input class="form-control" type="text" name="checkVehicle.residenceTime" placeholder="请输入滞留时间"/></td>
            </tr>
            <tr>
              <th class="cth">办事是由：</th>
              <td class="ctd"><input class="form-control" type="text" name="checkVehicle.reason" placeholder="请输入办事是由"/></td>
              <th class="cth">核录人：</th>
              <td class="ctd"><input class="form-control" type="text" name="checkVehicle.enterer" placeholder="请输入核录人"/></td>
              <th class="cth"></th>
              <td class="ctd"></td>
            </tr>
          </table>
  	    </td>
      </tr>
    <%} %>  
      <tr>
        <th>驾驶员信息采集：</th>
        <td style="padding: 0;">
          <table border="0" width="100%">
            <input type="hidden" name="driver.isDriver" value="1" />
            <tr>
              <th class="cth">姓名：</th>
              <td class="ctd"><input class="form-control" type="text" name="driver.name" placeholder="请输入姓名"/></td>
              <th class="cth" ><label style=color:red>*</label>身份证号：</th>
              <td class="ctd"><input class="form-control" type="text" name="driver.idcard" id="idcard" placeholder="请输入身份证号--必填"/></td>
            </tr>
            <tr>
              <th class="cth"><label style=color:red>*</label>手机号：</th>
              <td class="ctd"><input class="form-control" type="text" name="driver.phonenum" id="phonenum" placeholder="请输入手机号--必填" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
              <th class="cth">人证核验结果：</th>
              <td class="ctd">
                <input type="radio" name="driver.match" value="1" checked>一致&nbsp;&nbsp;&nbsp;
      		    <input type="radio" name="driver.match" value="0">不一致
              </td>
            </tr>
            <tr>
              <th class="cth">手机检查：</th>
              <td class="ctd">
                <input type="radio"  name="driver.isDubious" value="0" checked>无可疑&nbsp;&nbsp;&nbsp;
      	        <input type="radio" name="driver.isDubious" value="1">有可疑
      	      </td>
      	      <th class="cth">可疑描述：</th>
              <td class="ctd" colspan="2" >
                <input class="form-control" type="text" name="driver.finds" placeholder="请输入可疑描述"/>
              </td>
              <td class="ctd remark">(可疑URL、暴恐音视频等)</td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <th>乘客信息采集：</th>
         <td style="padding: 2px;" id="passengerTD">&nbsp;&nbsp;
           <button type="button" class="btn btn-primary btn-ms" onclick="addPassenger();">增加乘客</button>
         </td>
      </tr>
      <tr>
        <th>车内违禁物品：</th>
        <td><input class="form-control" type="text" value="无" name="checkVehicle.forbids" placeholder="请输入违禁物品"/></td>
      </tr>
    </table>

    <table class="showTable" border="1" id="yjType0_table">
      <tr>
        <th>驾驶人员与车辆登记人是否一致：</th>
        <td>
          <select class="form-control" style="width: 300px;display: inline;" id="explains" name="checkVehicle.relations">
      	    <option value="1">一致</option>
      	    <option value="2">不一致，借亲属、朋友车辆</option>
      	    <option value="3">不一致，公务车辆</option>
      	    <option value="4">不一致，车辆未过户</option>
      	    <option value="5">不一致，其他</option>
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
	function addPassenger(){
		var uuid = generateUUID();
		var passengerTable = "<table border='0' class='ctable' width='100%'>"+
            "<tr>"+
              "<th class='cth'>身份证号：</th>"+
              "<td class='ctd'><input class='form-control' type='text' name='passenger_idcard"+uuid+"' placeholder='请输入身份证号'/></td>"+
              "<th class='cth'>手机号：</th>"+
              "<td class='ctd'><input class='form-control' type='text' name='passenger_phonenum"+uuid+"' placeholder='请输入手机号'/></td>"+
              "<th class='cth'>人证核验结果：</th>"+
              "<td class='ctd'>"+
                "<input type='radio' name='passenger_match"+uuid+"' value='1' checked>一致&nbsp;&nbsp;&nbsp;"+
      		    "<input type='radio' name='passenger_match"+uuid+"' value='0'>不一致"+
              "</td>"+
              "<td class='ctd' rowspan='2'>"+
                "<button type='button' class='btn btn-danger btn-ms' onclick='deletePassenger(this);'>删除</button>"+
              "</td>"+
            "</tr>"+
            "<tr>"+
              "<th class='cth'>手机检查：</th>"+
              "<td class='ctd'><input type='radio' id='dubious0' name='passenger_isDubious"+uuid+"' value='0' checked>无可疑&nbsp;&nbsp;&nbsp;"+
      	        "<input type='radio' id='dubious1' name='passenger_isDubious"+uuid+"' value='1'>有可疑"+
      	      "</td>"+
      	      "<th class='cth'>可疑描述：</th>"+
              "<td class='ctd' colspan='2' >"+
                "<input class='form-control' type='text' name='passenger_finds"+uuid+"' placeholder='请输入可疑描述'/>"+
              "</td>"+
              "<td class='ctd remark'>(可疑URL、暴恐音视频等)</td>"+
            "</tr>"+
          "</table>";
		$("#passengerTD").append(passengerTable);
	};
	
	/**删除乘客信息 @*/
	function deletePassenger(t){
		$(t).parent().parent().parent().remove();
	}
	
$(function() {
	$("#yjType0").bind("click", function() {
		$("#yjType0_table").show();
		$("#yjType1_table").hide();
	});

	$("#yjType1").bind("click", function() {
		$("#yjType0_table").hide();
		$("#yjType1_table").show();
	});
	
	$("#driverMatch0").bind("click", function() {
		$("#explains").attr("disabled",false);
	});

	$("#driverMatch1").bind("click", function() {
		$("#explains").attr("disabled",true);
		$("#explains").val("");
	});
	
	$("#akeason_title").hide();
	$("#akeason").hide();
	$("#acontent_title").hide();
	$("#acontent").hide();
	$("#acheck1").bind("click", function() {
		$("#akeason").attr("disabled",true);
		$("#akeason_title").hide();
		$("#akeason").hide();
		$("#acontent_title").hide();
		$("#acontent").hide();
	});

	$("#acheck0").bind("click", function() {
		$("#akeason").attr("disabled",false);
		$("#akeason").val("");
		$("#akeason_title").show();
		$("#akeason").show();
	});
	
	//表单验证
	$("#form").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {
			valid : "glyphicon glyphicon-ok",
			invalid : "glyphicon glyphicon-remove",
			validating : "glyphicon glyphicon-refresh"
		}
	});
});

	function changeAkeason(val){
		if(val == 6){
			$("#acontent_title").show();
			$("#acontent").show();
		}else{
			$("#acontent_title").hide();
			$("#acontent").hide();
		}
	}

	//保存信息
	function submitForm() {
		if($("#acheck0")[0].checked == true){
			//不在其他核查原因
			var akeason = $("#akeason").val();
			if(akeason == 0){
				alert("请选择不再核查的原因");
				return;
			}
			if(akeason == 6){
				var acontent = $("#acontent").val();
				if(acontent == null || acontent == undefined || acontent == ''){
					alert("请输入不再核查的其他原因");
					return;
				}
			}
			
		}	
		
		//手机号校验
		phonenum = $("#phonenum").val();
		if(phonenum == null || phonenum == undefined || phonenum == ''){
			alert("手机号不能为空");
			return;
		}else if(phonenum.length != 11){
			alert("手机号格式不正确");
			return;
		}
		
		//身份证号校验
		idcard = $("#idcard").val();
		var result=idcard.match(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/);
		if(idcard == null || idcard == undefined || idcard == ''){
			alert("身份证号不能为空");
		} else if (result==null){
			alert("身份证号不合法");
		} else {
			if(!window.confirm("提交后将不能再修改，确认提交？")) return;
			var $form = $("#form");
			var data = $form.data("bootstrapValidator");
			if (data) {
				// 修复记忆的组件不验证
				data.validate();
				if (!data.isValid()) {//无效
					return false;
				} else {//有效
					$.ajax({
						type : "POST",
						url : "checkVehicle_ajaxAdd",
						async : false,
						data : $("#form").serialize(),
						success : function(data) {
							window.location.href = "contrastVehicle_detail?contrastVehicle.id=${contrastVehicle.id}";
						}
					});
				}
			}		
		}

	}
</script>
<style>
.lk-panel{
	border-radius:10px;
	padding: 0;
	margin: 5px;
}
.showTable   { width: 100%;border-collapse:collapse;font-size: 1.2em;margin-bottom:5px;}
.showTable th{ height: 50px; text-align: right;padding-right: 10px; width: 170px; }
.showTable td{ height: 50px; text-align: left;padding: 0 20px; }

.ctable      { width: 100%;border: 1px solid #ccc;margin-bottom: 5px;}
.cth         { width: 120px !important;padding-right: 0px !important;font-size: 13px; !important;}
.ctd		 { padding: 0 10px !important;}
.remark      { font-size: 0.8em;}  
</style>
</html>