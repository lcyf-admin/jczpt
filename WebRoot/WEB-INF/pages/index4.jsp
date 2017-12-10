<%@page import="cn.lhkj.commons.base.BaseDataCode"%>
<%@page import="cn.lhkj.commons.util.StringUtil"%>
<%@page import="cn.lhkj.commons.entity.SessionBean"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	SessionBean user = (SessionBean)session.getAttribute("SESSION_BEAN");
	String userId = user.getAccount();
	String stationId = StringUtil.trim(user.getStationId());
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <title>检查站综合管理系统</title>
  <base href="<%=basePath%>"/>
  <%@ include file = "/js/include/index.inc"%>
  <%@ include file = "/js/include/ymPrompt.inc"%>
  <%@ include file = "/js/include/ztree.inc"%>
</head>
<script >
var account = "<%=userId%>";
var stationId = "<%=stationId%>";
</script>
<style>
.jcz_name {
    padding-top: 22px;
    padding-left: 320px;
    font-size: 30px;
    color: white;
    font-family: "SimHei";

}
.strok-outside {
    text-shadow:0 0 8px #1d1e72,-0 -0 8px #1d1e72;
    #text-shadow: 0 3px #1d1e72, 3px 0 #1d1e72, -3px 0 #1d1e72, 0 -3px #1d1e72;
}

.dropdown-menu {
	min-width: 78px !important;
    margin: 2px -21px 0 !important;
    background-color: rgb(51, 66, 165) !important;
    }
    
.dropdown-menu>li>a {
    color: white !important;
}    

.dropdown-menu>li>a:focus, .dropdown-menu>li>a:hover {
    background-color: rgb(51, 66, 165) !important;
}

#dropdownMenu1:focus, #dropdownMenu1:hover {
    color: #ff9900 !important;
    text-decoration: none;
}
</style>


<body id="indexBody">
  <!-- 头部 -->
  <div id="header2">
    <div id="more">
	 <ul id="topMenu">
	   <%-- <li ><p style="background: url(images/index/adm.png) no-repeat;left: 10px;top: 5px;"></p><span>${SESSION_BEAN.userView.names}</span></li> --%>
	   <li onclick="manualData()"><span>手动录入</span></li> 
	   <li onclick="dataStatistics()"><p style="background: url(images/index/tj.png) no-repeat;left: 10px;top: 5px;"></p>统计</li>
	   <li onclick="refresh()"><p style="background: url(images/index/ref.png) no-repeat;left: 10px;top: 5px;"></p><span>刷新</span></li>
	   <%if("admin".equals(userId)){%>
	   <li onclick="manage()"><p style="background: url(images/index/gui.png) no-repeat;left: 10px;top: 5px;"></p>后台管理</li>
	   <%}%>
       <li class="dropdown">
		    <a class="dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown" style="color: #a5abbd">样式</a>
		    <ul class="dropdown-menu " role="menu" aria-labelledby="dropdownMenu1">
		        <li role="presentation">
		            <a role="menuitem" tabindex="-1" href="index1">样式1</a>
		        </li>
		        <li role="presentation">
		            <a role="menuitem" tabindex="-1" href="index2">样式2</a>
		        </li>
		        <li role="presentation">
		            <a role="menuitem" tabindex="-1" href="index3">样式3</a>
		        </li>
		        <li role="presentation">
		            <a role="menuitem" tabindex="-1" href="index4">样式4</a>
		        </li>
		    </ul>
      	</li>
       <li onclick="logout()"><p style="background: url(images/index/clo.png) no-repeat;left: 10px;top: 5px;"></p><span>注销</span></li>
      </ul>
    </div>
    <img src="images/index/logo2.png" style="top: 0px;position: absolute;left: 20px;" title="当前版本号：<%=BaseDataCode.version%>">
   	<p class="jcz_name"><lable class="strok-outside">${SESSION_BEAN.userView.orgName}</lable></p>
<!--     <div id="nav">
      <span>首页</span>
    </div> -->
    <div id="site" style="cursor: pointer;">
		<!-- <div style="background: url(images/index/site.png) no-repeat;width: 20px;height: 20px;display: inline-block;position: absolute;top: 2px;left: -15px;"></div> -->
		<%if("admin".equals(userId)){%>
		<input readonly="readonly" value="${SESSION_BEAN.userView.orgName}" style="width:330px;height:25px;" class="form-control" id="station" onclick="showMenu('station','orgzonZTree');" type="text" placeholder="请选站点"/>
		<%}else{ %>
		<%-- <p><small>经度：${SESSION_BEAN.userView.x}&nbsp;&nbsp;纬度：${SESSION_BEAN.userView.y}</small></p> --%>
		<div>
			<p>值班人员：${SESSION_BEAN.userView.names}</p>	
		</div>
		<%} %>
	</div>
  </div>
  <!-- 主体 -->
  <div class="container-fluid">
        <div class="row">
      <div class="col-lg-7 lk-panel" style="min-height: 345px;">
        <h3 class="grad">
          <img class="title_icon" src="images/index/Equipment_state.png">
          <span class="title">检查设备</span>
        </h3>
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-3 col-sm-6 Equipment">
			  <div class="photo" id="equipment_1">
			  	<div ></div>
				<div ></div>
			    <p>前置卡口</p>
				<img src="images/index/Equipment_01.png">
			  </div>
            </div>  
            <div class="col-md-3 col-sm-6 Equipment">
			  <div class="photo" id="equipment_2">
                <div ></div>
				<div ></div>
                <p>安检车道</p>
				<img src="images/index/Equipment_02.png">
			  </div>
            </div>
            
            <div class="col-md-3 col-sm-6 Equipment">
			  <div class="photo" id="equipment_3">
				<div ></div>
				<div ></div>
				<p>数据门</p>
				<img src="images/index/Equipment_04.png">
			  </div>
            </div>
            
            <div class="col-md-3 col-sm-6 Equipment">
			  <div class="photo" id="equipment_4">
			    <div ></div>
				<div ></div>
			    <p>车底扫描</p>
                <img src="images/index/Equipment_05.png">
              </div>
            </div>
          </div>
        </div>
        <div style="clear: both;"></div>
      </div>
      <div class="col-lg-5">
        <!-- 车辆预警 -->
		<div class="lk-panel li_height">
		  <h3 class="grad">
		  	<img class="title_icon" src="images/index/info.png">
		  	<span class="title">预警车辆</span>
		  	<span class="more" style="float: right;margin: 12px;cursor: pointer;" onclick="warnVehicleMore();">更多</span>
		  </h3>
		  <table class="carhis" >
		  	<tbody id="warnVehicleList">
		  	<%for(int i=0;i<3;i++){%><tr <%if(i==1){%> class="gray" <%}%>>
          	  <%for(int j=0;j<5;j++){%>
          	   <td id="warnVehicleList_<%=i%>_<%=j%>">-</td>
              <%}%></tr>
            <%}%>
		  	</tbody>
		  </table>
		</div>
		<!-- 人员预警 -->
		<div class="lk-panel li_height" style="margin-top: 5px;">
		  <h3 class="grad">
		    <img class="title_icon" src="images/index/info.png">
		    <span class="title">预警人员</span>
		    <span class="more" style="float: right;margin: 12px;cursor: pointer;" onclick="warnPersonMore()">更多</span>
		  </h3>
		  <table class="carhis" >
		    <tbody id="warnPersonList">
		    <%for(int i=0;i<3;i++){%><tr <%if(i==1){%> class="gray" <%}%>>
          	  <%for(int j=0;j<5;j++){%>
          	   <td id="warnPersonList_<%=i%>_<%=j%>">-</td>
              <%}%></tr>
            <%}%>
		    </tbody>
		  </table>
        </div>
      </div>
    </div> <!--row end  -->
    <div class="row" style="margin-top: 5px;">
      <div class="col-lg-7 lk-panel">
        <!-- 实时车辆 -->
		<h3 class="grad">
		  <img class="title_icon" src="images/index/car_info.png">
		  <span class="title">实时车辆</span>
		</h3>
		<table width="100%" style="border-collapse:collapse;"><tr>
		  <td style="width: 375px;padding: 0;">
		  	<img height="250px" width="375px" id="vehicle_carImg" src="images/defautVehicle.jpg" 
		  		onerror="this.src='images/defautVehicle.jpg'" />
		  </td>
		  <td>
		    <table class="vehicleTable">
		      <tr><th >车牌号</th><td id="vehicle_carNum"></td></tr>
		      <tr class="gray"><th >车牌颜色</th><td id="vehicle_plateColor"></td></tr>
		      <tr><th>时间</th><td id="vehicle_passdate"></td></tr>
		      <tr class="gray"><th>位置</th><td id="vehicle_laneName"></td></tr>
		      <tr ><th style="height: 80px;">驾驶人员</th><td>
		        <p id="driver_name"></p>
		        <p id="driver_idcard"></p>
		      </td></tr>
		    </table>
		  </td>
		  <td style="width: 120px;padding: 0;">
		    <img height="250px" id="vehicle_driverImg" src="images/defaut.jpg" style="max-width: 375px;min-width:200px;"
		      onerror="this.src='images/defaut.jpg'" >
		  </td>
		</tr></table>
      </div>
      <div class="col-lg-5">
        <div class="lk-panel">
          <h3 class="grad">
            <img class="title_icon" src="images/index/per.png">
            <span class="title">实时人员</span>
          </h3>
          <table class="showTable">
		    <tr>
			  <th>姓名</th><td id="person_names"></td>
			  <td rowspan="5" style="width: 200px;padding: 0;">
			  	<img height="250px" width="200px" id="person_img" src="images/defaut.jpg" onerror="this.src='images/defaut.jpg'">
			  </td>
			</tr>
		    <tr class="gray"><th>身份证号</th><td id="person_idcard"></td></tr>
		    <tr ><th>位置</th><td id="person_location"></td></tr>
		    <tr class="gray"><th>时间</th><td id="person_captureTime"></td></tr>
		    <tr><th>是否人证合一</th><td id="person_isAPerson"></td></tr>
		  </table>
        </div>
      </div>
    </div><!--row end  -->
    <div class="row" style="margin-top: 5px;">
      <div class="col-lg-7 lk-panel">
        <h3 class="grad">
          <img class="title_icon" src="images/index/his.png">
          <span class="title">历史记录-车辆</span>
          <span class="more" style="float: right;margin: 12px;cursor: pointer;" onclick="priorVehicleMore()">更多</span>
        </h3>
		<table cellspacing="0" class="carhis" border="0" >
		  <tr class="title_gray bor">
		    <th style="text-align: center;">车牌号</th>
		    <th style="text-align: center;">车牌颜色</th>
			<th style="text-align: center;">身份证号</th>
			<th style="text-align: center;">时间</th>
			<th style="text-align: center;" >位置</th>
          </tr>
          <tbody id="vehicleList">
          <%for(int i=0;i<4;i++){%><tr <%if(i==0||i==2){%> class="gray" <%}%>>
          	<%for(int j=0;j<5;j++){%>
          	 <td id="vehicleList_<%=i%>_<%=j%>">-</td>
            <%}%></tr>
          <%}%>
          </tbody>
		</table>
      </div>
      <div class="col-lg-5">
        <div class="lk-panel">
          <h3 class="grad">
            <img class="title_icon" src="images/index/his.png">
            <span class="title">历史记录-人员</span>
            <span class="more" style="float: right;margin: 12px;cursor: pointer;" onclick="priorPersonMore()">更多</span>
          </h3>
		  <table cellspacing="0" class="carhis" border="0">
		    <tr class="title_gray">
			  <th style="text-align: center;">姓名</th>
			  <th style="text-align: center;">身份证号</th>
			  <th style="text-align: center;">年龄</th>
			  <th style="text-align: center;">位置</th>
			  <th style="text-align: center;">时间</th>
		    </tr>
		    <tbody id="personList">
		    <%for(int i=0;i<4;i++){%><tr <%if(i==0||i==2){%> class="gray" <%}%>>
	          <%for(int j=0;j<5;j++){%>
	          <td id="personList_<%=i%>_<%=j%>">-</td>
	         <%}%></tr>
            <%}%>
		    </tbody>
		  </table>
        </div>
      </div>
    </div>
  </div><!--container-fluid end  -->
  <div style="height:30px;"></div>
<ul id="orgzonZTree" class="ztree myztree" style="margin-top:0;display:none;height:200px; position: absolute;border: 1px solid #ccc;"></ul>
</body>
</html>