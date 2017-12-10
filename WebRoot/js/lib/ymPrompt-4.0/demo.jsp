<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>ymPrompt消息提示组件4.0版[2009-03-02]DEMO演示及使用简介</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<script type="text/javascript" src="ymPrompt.js"></script>
	<link rel="stylesheet" id='skin' type="text/css" href="skin/qq/ymPrompt.css" />
	<style type="text/css">
		
		html{
			height:100%;width:100%;
		}
		body{
			margin:5px 10px;font-size:12px;
		}
		h1{text-align:center;font-size:24px;line-height:1.5em}
		h3{
			margin:10px 0 0;padding:0;
			font-size:14px;line-height:25px
		}
		
		.help ul li{
			margin:2px 0;list-style-type:square;line-height:1.5em
		}

		.myContent{padding:50px 0;text-align:center}

		.prop{color:#00f;font-family:fixedsys}

		.table{border-collapse:collapse;border:1px solid #999;margin:5px 0}
		.table caption{background:#eee;line-height:30px;border:1px solid #999;font-weight:bold}
		.table th,.table td{border:1px solid #999;text-align:right}
		.table th{text-align:center;line-height:22px;}
		.table td.code{
			background:#eee;font-family:'Courier New';
			line-height:20px;font-size:12px;text-align:left;
		}

		.fmt{margin:10px 20px;line-height:1.5em}
		.mycls{font-size:18px;padding:50px 0}
		.confirm{padding:15px 20px;font-weight:bold;font-size:14px;line-height:30px}
		.confirm input {width:100%;border:1px inset #ccc}

		.customCls{
			font-weight:bold;color:#00f;font-size:20px;padding:30px 0;text-align:center
		}
		#myInput{width:90%}
	</style>
	<script type="text/javascript">
		function $(id){
			return document.getElementById(id)
		}
		//ymPrompt.setDefaultCfg({slideShowHide:false});
		ymPrompt.errorInfo({message:'初始化弹出一！',title:'错误提示',height:200,width:300,fixPosition:true,msgCls:'mycls',handler:function(){}})
		
		ymPrompt.alert({message:'初始化弹出二，<br>我可以最大化最小化',title:'hello!',maxBtn:true,minBtn:true,height:230,width:350});

		window.onload=function(){
			var o=$('chgSkin');
			var css=$('skin');
			o.selectedIndex=0;
			o.onchange=function(){
				css.href='skin/'+this.options[this.selectedIndex].value+'/ymPrompt.css';
			}
		}

		function json2str(o){
			var arr=[];
			var fmt=function(s){
				if(typeof s=='object' && s!=null) return json2str(s);
				return /^(string|number)$/.test(typeof s)?"'"+s+"'":s;
			}
			for(var i in o) arr.push(i+':'+fmt(o[i]));
			return '{<br>&nbsp;&nbsp;'+arr.join(',<br>&nbsp;&nbsp;')+'&nbsp;&nbsp;<br>}';
		}

		//var location=document.location;
	</script>
 </head>
 <body>
	<h1>ymPrompt消息提示组件4.0版[2009-03-02]DEMO演示及使用简介</h1>	
	<h3>页面IFRAME:</h3>
	<iframe src="iframe.jsp" width="100%" height="60"></iframe><br />
	<table width=100%>
	<tr><td>
	<h3>页面文本:</h3>
	页面文本1<br />
	页面文本2
	</td>
	<td>
	<h3>页面Select选择框</h3>	
	<select>
		<option>下拉选项1</option>
		<option>下拉选项2</option>
		<option>下拉选项3</option>
	</select>
	</td></tr>
	</table>
	<hr/>
	<b>更换皮肤：</b><select id='chgSkin'>
		<option value='qq'>QQ</option>
		<option value='simple'>Simple</option>
		<option value='simple_gray'>SimpleGray</option>
		<option value='vista'>VISTA</option>
		<option value='dmm-green'>dmm-Green</option>
		<option value='bluebar'>bluebar</option>
		<option value='black'>black</option>
	</select>&nbsp;&nbsp;<b>修改默认配置：</b>拖动窗体的透明度：<input type='text' size=10 value='0.8' id='c2' />&nbsp;遮罩颜色：<input type='text' size=10 value='#00f' id='c0' />&nbsp;遮罩透明度:<input id='c1' size=10 type='text' value='0.1' />&nbsp;<input type="button" value="修改默认配置" onclick="ymPrompt.setDefaultCfg({winAlpha:$('c2').value||'0.8',maskAlpha:$('c1').value||'0.1',maskAlphaColor:$('c0').value||'#00f'});ymPrompt.alert({title:'提示信息',message:'恭喜！默认配置修改成功！'})" />
	<div style='display:none' id='txt'>
		在web开发中，对于浏览器默认的消息提示框（如alert,confirm等）外观无法控制，同时我们经常希望能实现一些window.open之类的弹出框，但window.open弹出框存在诸多问题，如可能被拦截，界面不美观等。
<br />为了实现更好的界面效果和控制，于是模拟系统的消息提示框及弹出窗口实现了该组件。在外观上可以通过css进行完全的控制。
	</div>
	<hr>
<table width='100%' cellpadding='3' cellspacing='0' class='table'>
	<caption>组件调用方式1(传统参数传入方式)：</caption>
	<tr align='center'>
		<th width=150>示例</th>
		<th>调用方法</th>
	</tr>
	<tr>
		<td><input type="button" value="信息提示" onclick="ymPrompt.alert('http://www.qq.com',null,null,'确认要提交吗?',handler)" /></td>
		<td class='code'>ymPrompt.alert('http://www.qq.com',null,null,'确认要提交吗?',handler)</td>
	</tr>
	<tr>
		<td><input type="button" id='b2' value="成功信息" onclick="ymPrompt.succeedInfo($('txt').innerHTML,400,260,null,handler2)" /></td>
		<td class='code'>ymPrompt.succeedInfo($('txt').innerHTML,400,260,null,handler2)</td>
	</tr>
	<tr>
		<td><input type="button" value="失败信息" onclick="ymPrompt.errorInfo('操作失败！',null,null,null,handler)" /></td>
		<td class='code'>ymPrompt.errorInfo('操作失败！',null,null,null,handler)</td>
	</tr>
	<tr>
		<td><input type="button" value="询问信息" onclick="ymPrompt.confirmInfo('信息确认框功能测试',null,null,null,handler)" /></td>
		<td class='code'>ymPrompt.confirmInfo('信息确认框功能测试',null,null,null,handler)</td>
	</tr>
	<tr>
		<td><input type="button" value="普通弹窗" onclick="ymPrompt.win('<div class=\'myContent\'>普通弹出窗口</div>',300,200,'普通弹窗测试')" /></td>
		<td class='code'>ymPrompt.win('&lt;div class=\'myContent\'&gt;普通弹出窗口&lt;/div&gt;',300,200,'普通弹窗测试')</td>
	</tr>
	<tr>
		<td><input type="button" value="iframe弹窗" onclick="ymPrompt.win('http://www.163.com',500,300,'网易官方网站',handler,null,null,true);" /></td>
		<td class='code'>ymPrompt.win('http://www.163.com',500,300,'网易官方网站',handler,null,null,{id:'a'})</td>
	</tr>
</table>

<table width='100%' cellpadding='3' cellspacing='0' class='table'>
	<caption>组件调用方式2(JSON方式)：</caption>
	<tr align='center'>
		<th width=150>示例</th>
		<th>源码</th>
	</tr>
	<tr>
		<td><input type="button" value="信息提示" onclick="ymPrompt.alert({message:'http://www.qq.com',slideShowHide:false,title:'确认要提交吗?',handler:handler})" /></td>
		<td class='code'>ymPrompt.alert({message:'http://www.qq.com',slideShowHide:false,title:'确认要提交吗?',handler:handler})</td>
	</tr>
	<tr>
		<td><input type="button" value="成功信息" onclick="ymPrompt.succeedInfo({message:$('txt').innerHTML,width:400,height:260,handler:handler2})" /></td>
		<td class='code'>ymPrompt.succeedInfo({message:$('txt').innerHTML,width:400,height:260,handler:handler2})</td>
	</tr>
	<tr>
		<td><input type="button" value="失败信息" onclick="ymPrompt.errorInfo({message:'操作失败！',handler:handler})" /></td>
		<td class='code'>ymPrompt.errorInfo({message:'操作失败！',handler:handler})</td>
	</tr>
	<tr>
		<td><input type="button" value="询问信息" onclick="ymPrompt.confirmInfo({message:'信息确认框功能测试',handler:handler})" /></td>
		<td class='code'>ymPrompt.confirmInfo({message:'信息确认框功能测试',handler:handler})</td>
	</tr>
	<tr>
		<td><input type="button" value="普通弹窗" onclick="ymPrompt.win({message:'普通弹出窗口',width:300,height:200,cls:'myContent',title:'普通弹窗测试'})" /></td>
		<td class='code'>ymPrompt.win({message:'普通弹出窗口',width:300,height:200,msgCls:'myContent',title:'普通弹窗测试'})</td>
	</tr>
	<tr>
		<td><input type="button" value="iframe弹窗" onclick="ymPrompt.win({message:'http://www.163.com',width:500,height:300,title:'网易官方网站',handler:handler,maxBtn:true,minBtn:true,iframe:true})" /></td>
		<td class='code'>ymPrompt.win({message:'http://www.163.com',width:500,height:300,title:'网易官方网站',handler:handler,maxBtn:true,minBtn:true,iframe:true})</td>
	</tr>
</table>

<table width='100%' cellpadding='3' cellspacing='0' class='table'>
	<caption>其他使用方式演示：</caption>
	<tr align='center'>
		<th width=150>示例</th>
		<th>源码</th>
	</tr>
	<tr>
		<td><input type="button" value="最简调用1" onclick="ymPrompt.alert()" /></td>
		<td class='code'>ymPrompt.alert()</td>
	</tr>
	<tr>
		<td><input type="button" value="最简调用2" onclick="ymPrompt.alert('消息内容')" /></td>
		<td class='code'>ymPrompt.alert('消息内容')</td>
	</tr>
	<tr>
		<td><input type="button" value="设置消息和标题" onclick="ymPrompt.alert({title:'我的标题',message:'我的内容'})" /></td>
		<td class='code'>ymPrompt.alert({title:'我的标题',message:'我的内容'})</td>
	</tr>
	<tr>
		<td><input type="button" value="自定义iframe属性" onclick="ymPrompt.win({title:'iframe模式',fixPosition:true,maxBtn:true,minBtn:true,iframe:{id:'myId',name:'myName',src:'http://www.baidu.com'}})" /></td>
		<td class='code'>ymPrompt.win({title:'iframe模式',fixPosition:true,maxBtn:true,minBtn:true,iframe:{id:'myId',name:'myName',src:'http://www.baidu.com'}})</td>
	</tr>
	<tr>
		<td><input type="button" value="不随滚动条滚动" onclick="ymPrompt.alert({title:'fixPosition使用演示',message:'我不会随滚动条一起滚动',fixPosition:false})" /></td>
		<td class='code'>ymPrompt.alert({title:'fixPosition使用演示',message:'我不会随滚动条一起滚动',fixPosition:false})</td>
	</tr>
	<tr>
		<td><input type="button" value="可在窗口外拖动" onclick="ymPrompt.alert({title:'dragOut使用演示',message:'我可以拖出到窗口可见区域以外',dragOut:true})" /></td>
		<td class='code'>ymPrompt.alert({title:'dragOut使用演示',message:'我可以拖出到窗口可见区域以外',dragOut:true})</td>
	</tr>
	<tr>
		<td><input type="button" value="程序控制关闭" onclick="ymPrompt.alert({title:'autoClose使用演示',message:'程序控制关闭',autoClose:false,handler:autoClose})" /></td>
		<td class='code'>ymPrompt.alert({title:'autoClose使用演示',message:'程序控制关闭',autoClose:false,handler:autoClose})</td>
	</tr>
	<tr>
		<td><input type="button" value="无标题栏" onclick="ymPrompt.win({message:'<br><center>无标题栏</center>',handler:noTitlebar,btn:[['关闭我']],titleBar:false})" /></td>
		<td class='code'>ymPrompt.win({message:'&lt;br&gt;&lt;center&gt;无标题栏&lt;/center&gt;',handler:noTitlebar,btn:[['关闭我']],titleBar:false})</td>
	</tr>
	<tr>
		<td><input type="button" value="无关闭按钮" onclick="ymPrompt.win({message:'<br><center>无关闭按钮</center>',btn: ['OK'],closeBtn:false})" /></td>
		<td class='code'>ymPrompt.win({message:'&lt;br&gt;&lt;center&gt;无关闭按钮&lt;/center&gt;',btn: ['OK'],closeBtn:false})</td>
	</tr>
	<tr>
		<td><input type="button" value="不显示遮罩" onclick="ymPrompt.alert({message:'不显示遮罩',title:'不显示遮罩',showMask:false})" /></td>
		<td class='code'>ymPrompt.alert({message:'不显示遮罩',title:'不显示遮罩',showMask:false})</td>
	</tr>
	<tr>
		<td><input type="button" value="右下角弹出" onclick="ymPrompt.alert({message:'右下角弹出',title:'右下角弹出',winPos:'rb'})" /></td>
		<td class='code'>ymPrompt.alert({message:'右下角弹出',title:'右下角弹出',winPos:'rb'})</td>
	</tr>
	<tr>
		<td><input type="button" value="自定义弹出位置" onclick="ymPrompt.alert({message:'自定义弹出位置',title:'右下角弹出',winPos:[200,400]})" /></td>
		<td class='code'>ymPrompt.alert({message:'自定义弹出位置',title:'右下角弹出',winPos:[200,1000]})</td>
	</tr>
	<tr>
		<td><input type="button" value="自定义按钮" onclick="ymPrompt.alert({message:'自定义按钮',title:'自定义按钮测试',handler:testHd,btn:[['是','yes'],['否','no'],['取消','cancel']]})" /></td>
		<td class='code'>ymPrompt.win({message:'自定义按钮',title:'自定义按钮测试',handler:testHd,btn:[['是','yes'],['否','no'],['取消','cancel']]})</td>
	</tr>
	<tr>
		<td><input type="button" value="定义窗体透明度" onclick="ymPrompt.alert({message:'拖动窗体时的透明度为0.5',title:'自定义按钮测试',winAlpha:0.5})" /></td>
		<td class='code'>ymPrompt.alert({message:'拖动窗体时的透明度为0.5',title:'自定义按钮测试',winAlpha:0.5})</td>
	</tr>
	<tr>
		<td><input type="button" value="显示窗体阴影(IE)" onclick="ymPrompt.alert({message:'显示窗体阴影',title:'显示阴影',showShadow:true})" /></td>
		<td class='code'>ymPrompt.alert({message:'显示窗体阴影',title:'显示阴影',showShadow:true})</td>
	</tr>
	<tr>
		<td><input type="button" value="窗体淡入淡出" onclick="ymPrompt.alert({message:'窗体淡入淡出',title:'淡入淡出',useSlide:true,handler:slideHd})" /></td>
		<td class='code'>ymPrompt.alert({message:'窗体淡入淡出',title:'淡入淡出',useSlide:true,handler:slideHd})</td>
	</tr>
	<tr>
		<td><input type="button" value="自定义淡入淡出" onclick="ymPrompt.alert({message:'自定义淡入淡出',title:'淡入淡出',useSlide:true,slideCfg:{increment:0.1,interval:100},handler:slideHd})" /></td>
		<td class='code'>ymPrompt.alert({message:'自定义淡入淡出',title:'淡入淡出',useSlide:true,slideCfg:{increment:0.1,interval:100},handler:slideHd})</td>
	</tr>
	<tr>
		<td><input type="button" value="最大化最小化" onclick="ymPrompt.alert({message:'显示最大化最小化按钮',title:'最大化最小化',minBtn:true,maxBtn:true})" /></td>
		<td class='code'>ymPrompt.alert({message:'显示最大化最小化按钮',title:'最大化最小化',minBtn:true,maxBtn:true})</td>
	</tr>
	<tr>
		<td><input type="button" value="自定义内容样式" onclick="ymPrompt.win({message:'自定义内容样式',title:'自定义内容样式',msgCls:'customCls'})" /></td>
		<td class='code'>ymPrompt.win({message:'自定义内容样式',title:'自定义内容样式',msgCls:'customCls'})</td>
	</tr>
	<tr>
		<td><input type="button" value="允许右键" onclick="ymPrompt.alert({message:'允许在消息框中使用右键',title:'允许右键',allowRightMenu:true})" /></td>
		<td class='code'>ymPrompt.alert({message:'允许在消息框中使用右键',title:'允许右键',allowRightMenu:true})</td>
	</tr>
	<tr>
		<td><input type="button" value="允许选择" onclick="ymPrompt.alert({message:'允许选择消息框中内容',title:'允许选择',allowSelect:true})" /></td>
		<td class='code'>ymPrompt.alert({message:'允许选择消息框中内容',title:'允许选择',allowSelect:true})</td>
	</tr>
	<tr>
		<td><input type="button" value="模仿系统Confirm" onclick="ymPrompt.confirmInfo({icoCls:'',msgCls:'confirm',message:'请输入您的姓名：<br><input type=\'text\' id=\'myInput\' onfocus=\'this.select()\' />',title:'请输入您的名字',height:150,handler:getInput,autoClose:false})" /></td>
		<td class='code'>ymPrompt.confirmInfo({icoCls:'',msgCls:'confirm',message:'请输入您的姓名：&lt;br&gt;&lt;input type='text' id='myInput' onfocus='this.select()' /&gt;',title:'请输入您的名字',height:150,handler:getInput,autoClose:false})" /></td>
	</tr>
</table>

<table width='100%' cellpadding='3' cellspacing='0' class='table'>
	<caption>组件方式及属性调用演示：</caption>
	<tr align='center'>
		<th width=150>示例</th>
		<th>源码</th>
	</tr>
	<tr>
		<td><input type="button" value="属性读取" onclick="ymPrompt.win('<div class=fmt>版本号：'+ymPrompt.version+'<br>发布日期：'+ymPrompt.pubDate+'<br>组件当前配置信息：'+json2str(ymPrompt.cfg)+'</div>',250,500)" /></td>
		<td class='code'>ymPrompt.win('&lt;div class=fmt&gt;版本号：'+ymPrompt.version+'&lt;br&gt;发布日期：'+ymPrompt.pubDate+'&lt;br&gt;组件当前配置信息：'+json2str(ymPrompt.cfg)+'&lt;/div&gt;',250,500)</td>
	</tr>
	<tr>
		<td><input type="button" value="getPage测试" onclick="ymPrompt.win({message:'iframe.html',width:500,height:300,title:'getPage测试',handler:handlerIframe,autoClose:false,iframe:true})" /></td>
		<td class='code'>ymPrompt.win({message:'iframe.html',width:500,height:300,title:'getPage测试',handler:handlerIframe,autoClose:false,iframe:true})</td>
	</tr>
	<tr>
		<td><input type="button" value="resizeWin测试" onclick="ymPrompt.alert({message:'一秒钟后我的大小改为[400,300]',height:200,width:250});setTimeout(function(){ymPrompt.resizeWin(400,300)},1000);" /></td>
		<td class='code'>ymPrompt.alert({message:'一秒钟后我的大小改为[400,300]',height:200,width:250});<br>setTimeout(function(){ymPrompt.resizeWin(400,300)},1000);</td>
	</tr>
	<tr>
		<td><input type="button" value="doHandler测试" onclick="ymPrompt.alert({message:'两秒钟后自动点击确定按钮',handler:handler});setTimeout(function(){ymPrompt.doHandler('ok')},1000);" /></td>
		<td class='code'>ymPrompt.alert({message:'两秒钟后自动点击确定按钮',handler:handler});<br>setTimeout(function(){ymPrompt.doHandler('ok')},1000);</td>
	</tr>
	<tr>
		<td><input type="button" value="getButtons测试" onclick="ymPrompt.alert({message:'点击确定显示按钮的内容',autoClose:false,btn:[['是','yes'],['否','no']],handler:getButtons})" /></td>
		<td class='code'>ymPrompt.alert({message:'点击确定显示按钮的内容',autoClose:false,btn:[['是','yes'],['否','no']],handler:getButtons})</td>
	</tr>
	<tr>
		<td><input type="button" value="模拟qq消息" onclick="ymPrompt.alert({message:'悬浮右下角，模拟qq',fixPosition:true,winPos:'rb',showMask:false})" /></td>
		<td class='code'>ymPrompt.alert({message:'悬浮右下角，模拟qq',fixPosition:true,winPos:'rb',showMask:false})</td>
	</tr>
	<tr>
		<td><input type="button" value="窗口状态函数" onclick="ymPrompt.alert({message:'窗口状态操作',width:400,autoClose:false,btn:[['最大化','max'],['最小化','min'],['正常态','normal'],['关闭','close']],handler:stateHd})" /></td>
		<td class='code'>ymPrompt.alert({message:'窗口状态控制',width:400,autoClose:false,btn:[['最大化','max'],['最小化','min'],['正常态','normal'],['关闭','close']],handler:stateHd})</td>
	</tr>
	<tr>
		<td><input type="button" value="英文化" onclick="en();ymPrompt.alert({message:'英文化成功'})" /></td>
		<td class='code'>en();ymPrompt.alert({message:'英文化成功'})</td>
	</tr>
	<tr>
		<td><input type="button" value="中文化" onclick="cn();ymPrompt.alert({message:'中文化成功'})" /></td>
		<td class='code'>cn();ymPrompt.alert({message:'中文化成功'})</td>
	</tr>
</table>
<hr>
	<h3>调用方法及参数说明</h3>
	<ol class='help'>
		<li>在页面中引入ymPrompt.js。如：&lt;script type="text/javascript" src="ymPrompt.js"&gt;&lt;/script&gt;</li>
		<li>在页面中引入对应的皮肤文件的CSS，如：&lt;link rel="stylesheet" type="text/css" href="skin/qq/ymPrompt.css" /&gt;</li>
		<li>自定义组件的默认配置信息（<span style='color:#f00'>此步骤可选</span>，该方法可以在任意时间调用）
			<br />页面的js中通过ymPrompt.setDefaultCfg(cfg)方法修改组件部分或全部的默认属性。 
			<br />如：ymPrompt.setDefaultCfg({maskAlpha:0.2,maskAlphaColor:'#00f'})
			<br />
			<br />组件的默认配置（对于没有设定的项将采用该配置项的默认值）：
		   <br />{
			<br />&nbsp;&nbsp;&nbsp;message: '内容',	//消息框按钮
			<br />&nbsp;&nbsp;&nbsp;width: 300,			//宽
			<br />&nbsp;&nbsp;&nbsp;height: 185,		//高
			<br />&nbsp;&nbsp;&nbsp;title: '标题',		//消息框标题
			<br />&nbsp;&nbsp;&nbsp;handler: function() {}, //回调事件
			<br />&nbsp;&nbsp;&nbsp;maskAlphaColor: '#000',	//遮罩透明色
			<br />&nbsp;&nbsp;&nbsp;maskAlpha: 0.1,		//遮罩透明度
			<br />
			<br />&nbsp;&nbsp;&nbsp;iframe: false,		//iframe模式
			<br />&nbsp;&nbsp;&nbsp;icoCls: '',			//图标的样式
			<br />&nbsp;&nbsp;&nbsp;btn: null,			//按钮配置
			<br />&nbsp;&nbsp;&nbsp;autoClose: true,		//点击关闭、确定等按钮后自动关闭
			<br />&nbsp;&nbsp;&nbsp;fixPosition: true,		//随滚动条滚动
			<br />&nbsp;&nbsp;&nbsp;dragOut: false,			//不允许拖出窗体范围
			<br />&nbsp;&nbsp;&nbsp;titleBar: true,			//显示标题栏
			<br />&nbsp;&nbsp;&nbsp;showMask: true,			//显示遮罩
			<br />&nbsp;&nbsp;&nbsp;winPos: 'c',		//在页面中间显示
			<br />&nbsp;&nbsp;&nbsp;winAlpha:0.8,		//拖动窗体时窗体的透明度
			<br />&nbsp;&nbsp;&nbsp;closeBtn:true,		//是否显示关闭按钮
			<br />&nbsp;&nbsp;&nbsp;showShadow:false,	//不显示阴影，只对IE有效
			<br />&nbsp;&nbsp;&nbsp;useSlide:false,		//不使用淡入淡出
			<br />&nbsp;&nbsp;&nbsp;slideCfg:{increment:0.3,interval:50},	//淡入淡出配置
			<br /><br />&nbsp;&nbsp;&nbsp;//按钮文本，可通过自定义这些属性实现本地化
			<br />&nbsp;&nbsp;&nbsp;closeTxt: '关闭',
			<br />&nbsp;&nbsp;&nbsp;okTxt:' 确 定 ',
			<br />&nbsp;&nbsp;&nbsp;cancelTxt:' 取 消 ',	
			<br />&nbsp;&nbsp;&nbsp;msgCls:'ym-content'		//消息内容的样式
			<br />&nbsp;&nbsp;&nbsp;minBtn:false,			//不显示最小化按钮	
			<br />&nbsp;&nbsp;&nbsp;minTxt:'最小化',
			<br />&nbsp;&nbsp;&nbsp;maxBtn:false,			//不显示最大化按钮
			<br />&nbsp;&nbsp;&nbsp;maxTxt:'最大化'   
			<br />&nbsp;&nbsp;&nbsp;allowSelect:false,		//是否允许选择消息框内容，默认不允许
			<br />&nbsp;&nbsp;&nbsp;allowRightMenu:false	//是否允许在消息框使用右键，默认不允许
		   <br />}
		</li>
		<li>根据您的需要调用相应的消息函数(两种参数传入方式)：
			<ul>
				<li>ymPrompt.alert(参数) //消息提示类型</li>
				<li>ymPrompt.succeedInfo(参数) //成功信息类型</li>
				<li>ymPrompt.errorInfo(参数)  //错误信息类型</li>
				<li>ymPrompt.confirmInfo(参数) //询问消息类型<br /><br /></li>
				<li>ymPrompt.win(参数) //自定义窗口类型 </li>
			</ul>

			<br /><b>参数传入方式包含两种：</b>
			<ul>
				<li>第一种即传统的参数传入，按照顺序传入相应的参数值即可（一定要按照顺序），对于不需要设定的值请传入null。如ymPrompt.alert('内容',null,null,'标题')
				<br />参数顺序上面的默认配置中参数顺序一致<br /><br />
				</li>
				<li>(推荐)第二种即JSON的传入方式，需要指定字段名,没有顺序，根据需要设定相关属性。如ymPrompt.alert({title:'标题',message:'内容'})</li>
			</ul>

			<br /><b>五个方法的参数意义完全相同(所有参数均为可选，不传入则使用默认参数值),具体含义如下： </b>
			<ul>
				<li><strong>message：</strong>消息组件要显示的内容，默认为“内容”。</li>
				<li><strong>width：</strong>消息框的宽度，默认为300。</li>
				<li><strong>height：</strong>消息框的高度，默认为185。</li>
				<li><strong>title：</strong>消息组件标题，默认为“标题”</li>
				<li><strong>handler：</strong>回调函数。当确定/取消/关闭按钮被点击时会触发该函数并传入点击的按钮标识。如ok代表确定，cancel代表取消，close代表关闭</li>
				<li><strong>maskAlphaColor：</strong>遮罩的颜色，默认为黑色。 </li>
				<li><strong>maskAlpha：</strong>遮罩的透明度，默认为0.1。<br/><br/> </li>
				<li><strong>fixPosition：</strong>设定是否弹出框随滚动条一起浮动，保持在屏幕的固定位置，默认为true </li>
				<li><strong>dragOut：</strong>设定是否允许拖出屏幕范围，默认为false。 </li>
				<li><strong>autoClose：</strong>设定用户点击窗口中按钮后自动关闭窗口，默认为true（设定为false后程序中可以通过调用close方法关闭）。</li>
				<li><strong>titleBar：</strong>是否显示标题栏，默认显示。注意，如果没有标题栏需要自己在程序中控制关闭。</li>
				<li><strong>showMask：</strong>是否显示遮罩层，默认为true</li>
				<li><strong>winPos：</strong>弹出窗口的位置，支持8种内置位置（c,l,t,r,b,lt,rt,lb,rb）及自定义窗口坐标,默认为c。
				<br />&nbsp;&nbsp;各参数意义：c:页面中间,l:页面左侧,t:页面顶部,r:页面右侧,b:页面顶部,lt:左上角,rt:右上角,lb:左下角,rb:右下角</li>
				<li><strong>winAlpha：</strong>弹出窗体拖动时的透明度，默认为0.8
				<br /><br />//以下三个参数主要用于win方法（当然你也可以通过设定这些覆盖前面四个消息类型的默认属性）。
				</li>
				<li><strong>iframe：</strong>是否使用iframe方法加载内容，该属性如果为true或者object，组件则尝试将message内容作为url进行加载(<span style='color:#f00'>如果属性值为一个object，则将object的内容添加为iframe的属性，如iframe:{id:'myId',name:'myName',src:'http://www.baidu.com'}则iframe的id为myId,name为myName,src为http://www.baidu.com</span>)。默认为false。 </li>
				<li><strong>icoCls：</strong>图标类型。传入的内容为className，具体写法可以参考ymprompt.css中对图标的定义方式。默认为空。 </li>
				<li><strong>btn：</strong>按钮定义。传入的是数组形式。每个按钮的格式为['按钮文本','按钮标识']，<br>如[['确定','ok'],['取消','cancel'],['关闭','close']]等。
				<br />注意单个按钮应该是这样的：[['确定','ok']]</li>
				<li><strong>closeBtn：</strong>是否显示关闭按钮，默认为true（显示）。<br /><br /></li>
				<li><strong>showShadow：</strong>是否启用弹出框阴影效果（IE Only），默认为false</li>
				<li><strong>useSlide：</strong>f是否启用弹出框的渐显渐隐效果，默认为false</li>
				<li><strong>slideCfg：</strong>渐变效果的配置信息,参数格式为object，属性包括incerment:透明度每次增加的值，interval:变化的速度。例如：{incerment:0.3,interval:50}。该参数仅在useSlide为true时有效
				<br /><br />//以下参数可用于对组件语言本地化，如用于英文等系统中</li>
				<li><strong>okTxt：</strong>确定按钮的文本描述，默认为“确定”</li>
				<li><strong>cancelTxt：</strong>取消按钮的文本描述，默认为“取消”</li>
				<li><strong>closeTxt：</strong>关闭按钮的文本描述（鼠标放在关闭按钮上时显示），默认为“关闭”</li>
	
				<li><strong>minTxt：</strong>最小化按钮的文本描述，默认为“最小化”</li>
				<li><strong>maxTxt：</strong>最大化按钮的文本描述，默认为“最大化”<br /><br /></li>

				<li><strong>minBtn：</strong>是否显示最小化按钮，默认为false</li>
				<li><strong>maxBtn：</strong>是否显示最大化按钮，默认为false<br /><br /></li>

				<li><strong>allowSelect：</strong>是否允许选择消息框内容，默认为false</li>
				<li><strong>allowRightMenu：</strong>是否允许在消息框中使用右键，默认为false</li>
			</ul>
		</li>
		<li><strong>操作接口：</strong>
	<br/><br/>
	<dl>
		<dt><strong>属性：</strong></dt>
	<dd><span class='prop'>version：</span>当前版本号 如：alert(ymPrompt.version)</dd>
	<dd><span class='prop'>pubDate：</span>当前版本的发布日期 如：alert(ymPrompt.pubDate);</dd>
	 <dd><span class='prop'>&nbsp;&nbsp;&nbsp;cfg：</span>组件的当前的默认配置</dd>

	<dt><strong>方法：</strong></dt>
	<dd><span class='prop'>setDefaultCfg(cfg)：</span>设定组件的默认属性，设定后的所有弹出均默认采用cfg中的设置。<br />
		如：ymPrompt.setDefaultCfg({maskAlpha:0.2,maskAlphaColor:'#00f'});	//设定遮罩层颜色为蓝色，透明度0.2<br /><br /><dd>

	<dd><span class='prop'>getPage()：</span>在iframe窗口模式下，获取到iframe的dom对象。<br />
		如：alert(ymPrompt.getPage().contentWindow.document.body.outerHTML);	//获取iframe页面的html内容<br /><br /><dd>

	<dd><span class='prop'>resizeWin(w,h)：</span>通过程序动态修改窗口的大小。参数：w：宽度，h:高度<br />
		如：ymPrompt.resizeWin(400,300);	//修改弹出框宽度为400px，高度为300px<br /><br /><dd>
	
	<dd><span class='prop'>doHandler(sign,autoClose)：</span>模拟触发某个按钮的点击事件。参数sign:传给回调函数的标识，autoClose:是否自动关闭窗口（默认采用全局配置）<br />
		如：ymPrompt.doHandler('ok',false);	//触发确定按钮的点击事件，并且执行完回调函数后不关闭窗口<br /><br /><dd>
	<dd><span class='prop'>getButtons()：</span>获取当前弹出窗口的所有按钮对象，返回结果是一个对象集合(数组)。<br />
		如：var btnID=ymPrompt.getButtons()[0].id;	//获取第一个按钮的id<br /><br /><dd>
	<dd><span class='prop'>close()：</span>关闭当前弹出的窗口
		如：ymPrompt.close()<br /><br /><dd>
	<dd><span class='prop'>max()：</span>最大化弹出窗口。<br /><br /><dd>
	<dd><span class='prop'>min()：</span>最小化弹出窗口<br /><br /><dd>
	<dd><span class='prop'>normal()：</span>窗口普通弹出状态<br /><dd>
	</dl>
	</li>
		<li><strong>其他说明：</strong>如果觉得“对象.方法”的调用方式比较麻烦，可以采用如下方式简化调用：
			<br />在调用之前设定var Alert=ymPrompt.alert。之后就可以使用Alert()的方式进行调用。
		</li>
	</ol>
	<script type="text/javascript">
		function cn(){
			ymPrompt.setDefaultCfg({title:'标题', message:"内容",okTxt:' 确 定 ',cancelTxt:' 取 消 ',closeTxt:'关闭',minTxt:'最小化',maxTxt:'最大化'});
		}
		function en(){
			ymPrompt.setDefaultCfg({title:'Default Title', message:"Default Message",okTxt:' OK ',cancelTxt:' Cancel ',closeTxt:'Close',minTxt:'Minimize',maxTxt:'Maximize'});
		}
		
		function slideHd(){
			ymPrompt.alert('看到效果了吗？');
		}

		function getInput(tp){
			if(tp!='ok') return ymPrompt.close();
			var v=$('myInput').value;
			if(v=='')
				alert('请输入您的名字！')
			else{
				alert('你输入的值为：'+v)
				ymPrompt.close();
			}
		}
		function getButtons(){
			var btnObjs=ymPrompt.getButtons(),arr=[];
			for(var i=0;i<btnObjs.length;i++)
				arr.push('按钮'+(i+1)+"内容:"+document.createElement("DIV").appendChild(btnObjs[i].cloneNode(true)).parentNode.innerHTML);
			alert(arr.join('\n\n'));
			ymPrompt.close();
		}
		function autoClose(){
			alert('三秒钟自动关闭');
			setTimeout(function(){ymPrompt.close()},3000)
		}
		function handlerIframe(){
			alert(ymPrompt.getPage().contentWindow.document.body.innerHTML);
			ymPrompt.close();
		}
		function noTitlebar(){
			alert('提示：除了可以通过增加按钮来控制，还可以在子页面中调用该页面的ymPrompt.close方法来关闭');
		}
		var Alert=ymPrompt.alert;
		function cancelFn(){
			Alert("点击了'取消'按钮");
		}
		function okFn(){
			Alert("点击了'确定'按钮");
		}
		function closeFn(){
			Alert("点击了'关闭'按钮");
		}
		function handler(tp){
			if(tp=='ok'){
				okFn();
			}
			if(tp=='cancel'){
				cancelFn();
			}
			if(tp=='close'){
				closeFn()
			}
		}
		function testHd(tp){
			Alert('你点击的按钮的标志为：'+tp);
		}
		function handler2(tp){
			if(tp=='ok'){
				ymPrompt.confirmInfo("保存成功!是否打印税票？",null,null,"问询提示",function(tp){tp=='ok'?ticketPrevie("print"):loadImposeInfo()})
			}
			if(tp=='cancel'){
				cancelFn();
			}
			if(tp=='close'){
				closeFn()
			}
		}
		function ticketPrevie(xx){
			Alert(xx)
		}
		function loadImposeInfo(){
			Alert("exit")
		}

		function stateHd(tp){
			ymPrompt[tp]();
		}
	</script>
 </body>
</html>
