<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>" />
  <%@ include file = "/js/include/common.inc"%>
</head>
<body style="overflow: hidden;min-height: 450px;">
<div style="margin: 10px 0 20px 0;border-bottom: 1px solid #ddd;height: 50px;">
	<div style="float: left;" title="返回">
		<a class="btn" href="javascript: window.history.go(-1);"><i class="icon-chevron-left" style="font-size: 2em;">  返回</i></a>
	</div>
</div>
<form class="form-horizontal" role="form" id="form" action="">
  <fieldset>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">通讯号编号</label>
      <div class="col-sm-8 ">
      	<p class="form-control-static">${transfer.tranNO}</p>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">接口版本</label>
      <div class="col-sm-8 ">
      	<p class="form-control-static">${transfer.ver}</p>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">请求类别</label>
      <div class="col-sm-8 ">
      	<p class="form-control-static">${transfer.tranType}</p>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">状态</label>
      <div class="col-sm-8 ">
      	<p class="form-control-static">${transfer.tranResult}</p>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">信息描述</label>
      <div class="col-sm-8 ">
      	<p class="form-control-static">${transfer.tranMsg}</p>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">调用时间</label>
      <div class="col-sm-8 ">
      	<p class="form-control-static">${transfer.sendTime}</p>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">MD5签名</label>
      <div class="col-sm-8 ">
      	<p class="form-control-static">${transfer.key}</p>
      </div>
      <div class="col-sm-1"></div>
    </div>
    <div class="form-group">
      <div class="col-sm-1"></div>
      <label class="col-sm-2 control-label">接口参数</label>
      <div class="col-sm-8 ">
      	<p class="form-control-static">${transfer.data}</p>
      </div>
      <div class="col-sm-1"></div>
    </div>
  </fieldset>     
</form>
</body>
</html>