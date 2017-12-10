<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
  <title>检查站综合管理系统</title>
  <base href="<%=basePath%>"/>
  <script src="${contextPath}/js/lib/jqurey/jquery-1.9.1.js"></script>
  <link rel="stylesheet" href="${contextPath}/css/login/style.css" />
</head>
<script>
$(function(){
	if(top.location != self.location){
		top.location = "<%=basePath%>";
	}
	$("#account").focus();
});
	
	/**提交表单@*/
	function submitForm() {
		if($("#account").val()=="" || $("#password").val()=="") {//判空
			alert("用户名或密码不能为空");
			$("#password").val("");
			$("#account").focus();
			return;
		}
		$.ajax({
	 		url: "ajaxLogin",
	 		type: "post",
	 		data: {
	 			account: $("#account").val(),
				password: $("#password").val()
			},
	 		async: false,
	 		dataType: "json",
	 		success: function(data){
	 			if(data.status == "y"){
					window.location.href = data.packagePath;
				}else{
					alert(data.info)
				}
	 		}
		});
	}
			
	//回车提交
	function enterIn(evt) {
		var evt=evt?evt:(window.event?window.event:null);//兼容IE和FF 
		if (evt.keyCode==13){ 
			document.getElementById("imgbt").click();
		} 
	}
</script>
<body class="index">
  <div class="div">
    <div class="main">
      <img src="css/login/logo.png"/>
      <h1 class="blue">检查站综合管理平台</h1>
      <h6 class="blue">JIAN CHA ZHAN ZONG HE GUAN LI PING TAI</h6>
      <br>
      <form action="" method="post" id="loginForm">
        <div class="input_sty_a">
		  <div class="user user_1">
            <input autocomplete="off" value="" datatype="*" id="account" name="account" tabindex="1" type="text" class="sty_a" onfocus="if(this.value==='请输入用户名') {this.value='';}" onblur="if (this.value==='') {this.value='请输入用户名';}">
		  </div>
		  <div class="user user_2">
		    <input autocomplete="off" value="" id="password" name="password" tabindex="2" type="password"  class="sty_a"  onkeydown="enterIn(event)">
		  </div>
		 </div>
         <input type="button" id="imgbt" value="登  录" class="btn" tabindex="3" onmouseover="this.className='btn_hov';" onmouseout="this.className='btn';" onclick="submitForm()">
      </form>
   </div>
   <div class="download"><a href="<%=request.getContextPath()%>/xz.jsp"  target="_black"><img src="<%=request.getContextPath()%>/images/index/download.jpg" width='23' height='25'/><font color="#FFFFFF" size="6">帮助工具及文档下载</font> </a></div>
  </div>
<style>
.download {
	margin-top:20px;
	text-align:center;
}
</style>
</body>
</html>
	