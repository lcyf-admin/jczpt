<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>Untitled Document</title>
		
		<script type="text/javascript" src="ymPrompt_ex.js"></script>
		<style type='text/css'>
		*{
			font-family:'宋体'
		}
		</style>
	</head>
	<body>
	Iframe中的下拉框：<select>
			<option>下拉项一</option>
			<option>下拉项二</option>
		</select><input type="button" value="信息提示" onclick="ymPrompt.alert({message:'http://www.qq.com',title:'确认要提交吗?',handler:function(){alert('good')}})" />
	</body>
</html>
