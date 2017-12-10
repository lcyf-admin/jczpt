
	/**加载车辆预警数据 @*/
	function loadContrastPerson(stationId){
		resetContrastPerson();
		if(stationId == "") return;
		$.ajax({
			type: "POST",
			url: "contrastPerson_ajaxContrastPersonList",
			data: { "records": 3,"stationId": stationId},
	 		async: true,
	 		dataType: "json",
			success: function(data){
				contrastPersonIdMap[stationId] = "";
				for(var i=0; i<data.length; i++){
					if(i==0){ contrastPersonIdMap[stationId] = data[i].id }
					$("#warnPersonList_"+i+"_0").empty().append("<a class='lk-index-link' href=\"javascript: dealWarnPerson('"+data[i].id+"')\";>"+data[i].namesOmit+"</a>");
					$("#warnPersonList_"+i+"_1").text(data[i].captureTimeView);
					$("#warnPersonList_"+i+"_2").text(data[i].actionOmit);
					$("#warnPersonList_"+i+"_3").text(data[i].locationOmit);
					$("#warnPersonList_"+i+"_4").text(data[i].isCheckedView);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){}
		});
	}
	
	/**重置预警人员信息 @*/
	function resetContrastPerson(){
		for(var i=0;i<3;i++){
			$("#warnPersonList_"+i+"_0").empty().append("-");
			$("#warnPersonList_"+i+"_1").text("-");
			$("#warnPersonList_"+i+"_2").text("-");
			$("#warnPersonList_"+i+"_3").text("-");
			$("#warnPersonList_"+i+"_4").text("-");
		}
	}
	
	function createContrastPsrsonSocket(){
		var location = (window.location+"").split("/"); 
		var basePath = location[0]+"//"+location[2]+"/"+location[3]; 
		basePath = basePath.replace("http://", "ws://")
		var webSocket = new WebSocket(basePath + "/websocketContrastPerson");
		webSocket.onerror = function(event){}
		webSocket.onopen = function(event){}
		webSocket.onmessage = function(event){
			var data = event.data;
			if(data != ""){
				var contrastPerson = eval("(" + data + ")");
				if(contrastPerson.id == contrastPersonIdMap[stationId]) return;
				contrastPersonIdMap[stationId] = contrastPerson.id;
				addContrastPerson(contrastPerson);
			}
		}
		function start(){
	  		window.setInterval(send,1000);
	  		function send(){
	  			webSocket.send("{\"stationId\":\""+stationId+"\",\"contrastPersonId\":\""+contrastPersonIdMap[stationId]+"\"}");
	  		}
	  	}
		setTimeout(start, 2000);
	}
	
	function addContrastPerson(data){
		for(var i=2;i>0;i--){
			$("#warnPersonList_"+i+"_0").empty().append($("#warnPersonList_"+(i-1)+"_0").children());
			$("#warnPersonList_"+i+"_1").text($("#warnPersonList_"+(i-1)+"_1").text());
			$("#warnPersonList_"+i+"_2").text($("#warnPersonList_"+(i-1)+"_2").text());
			$("#warnPersonList_"+i+"_3").text($("#warnPersonList_"+(i-1)+"_3").text());
			$("#warnPersonList_"+i+"_4").text($("#warnPersonList_"+(i-1)+"_4").text());
		}
		$("#warnPersonList_"+i+"_0").empty().append("<a class='lk-index-link' href=\"javascript: dealWarnPerson('"+data.id+"')\";>"+data.namesOmit+"</a>");
		$("#warnPersonList_"+i+"_1").text(data.captureTimeView);
		$("#warnPersonList_"+i+"_2").text(data.actionOmit);
		$("#warnPersonList_"+i+"_3").text(data.locationOmit);
		$("#warnPersonList_"+i+"_4").text(data.isCheckedView);
		
		var texts = "<p class='lk-p'>姓名："+data.names+"</p>"+
				"<p class='lk-p'>身份证号："+data.idcard+"</p>"+
				"<p class='lk-p'>时间："+data.captureTimeView+"</p>"+
				"<p class='lk-p'>位置："+data.location+"</p>";
		personWarningwin(data.id,texts);
		if(data.location.indexOf("车道") != -1){
			flickerbd( 5, data.id);
		}else if(data.location.indexOf("数据门") != -1){
			flickerbd( 3, data.id);
		}
	}
	
	
	/**重点人员预警提示弹出窗 
	 * titles : 标题
	 * par : id
	 * texts : 正文
	 * 调用方式 : personWarningwin('id','测试');
	 * @*/
	function personWarningwin(par,texts){
		try{
			audio.pause();
			audio.currentTime = 0;
			audio.play();//播放音效
		}catch(e){}
		try{
			swal({
				title: "<a href=\"javascript:dealWarnPerson('"+par+"');\" class=\"lk-win-link\">发现预警人员</a>",
				html: true,
				text: texts,
				imageUrl: "images/index/timg.gif",
				timer: 10000,
				showConfirmButton: true,
				confirmButtonText: "关闭"
			});
		}catch(e){}
	}
	
	/**处理人员预警信息 @*/
	function dealWarnPerson(id){
		ymPrompt.win({
			message : "contrastPerson_detail?contrastPerson.id=" + id,
			width : 1300,
			height : 780,
			title : '预警人员详细信息',
			maxBtn : true,
			minBtn : true,
			closeBtn : true,
			iframe : true
		});
	}
	var contrastPersonIdMap = {};
	
	
$(function(){
	loadContrastPerson(stationId);
	createContrastPsrsonSocket();
});
