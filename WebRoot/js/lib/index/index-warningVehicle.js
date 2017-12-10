
	/**加载车辆预警数据 @*/
	function loadContrastVehicle(stationId){
		resetContrastVehicle();
		if(stationId == "") return;
		$.ajax({
			type: "POST",
			url: "contrastVehicle_ajaxContrastVehicleList",
			data: { "records": 3,"stationId": stationId},
	 		async: true,
	 		dataType: "json",
			success: function(data){
				contrastVehicleIdMap[stationId] = "";
				for(var i=0;i<data.length;i++){
					if(i==0){ contrastVehicleIdMap[stationId] = data[i].id }
					$("#warnVehicleList_"+i+"_0").empty().append("<a class='lk-index-link' href=\"javascript: dealContrastVehicle('"+data[i].id+"')\";>"+data[i].carNum+"</a>");
					$("#warnVehicleList_"+i+"_1").text(data[i].namesOmit);
					$("#warnVehicleList_"+i+"_2").text(data[i].passdateView);
					$("#warnVehicleList_"+i+"_3").text(data[i].locationOmit);
					$("#warnVehicleList_"+i+"_4").text(data[i].isCheckedView);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){}
		});
	}
	
	/**重置预警车辆信息 @*/
	function resetContrastVehicle(){
		for(var i=0;i<3;i++){
			$("#warnVehicleList_"+i+"_0").empty().append("-");
			$("#warnVehicleList_"+i+"_1").text("-");
			$("#warnVehicleList_"+i+"_2").text("-");
			$("#warnVehicleList_"+i+"_3").text("-");
			$("#warnVehicleList_"+i+"_4").text("-");
		}
	}
	
	function createContrastVehicleSocket(){
		var location = (window.location+"").split("/"); 
		var basePath = location[0]+"//"+location[2]+"/"+location[3]; 
		basePath = basePath.replace("http://", "ws://")
		var webSocket = new WebSocket(basePath + "/websocketContrastVehicle");
		webSocket.onerror = function(event){}
		webSocket.onopen = function(event){}
		webSocket.onmessage = function(event){
			var data = event.data;
			if(data != ""){
				var contrastVehicle = eval("(" + data + ")");
				if(contrastVehicle.id == contrastVehicleIdMap[stationId]) return;
				contrastVehicleIdMap[stationId] = contrastVehicle.id;
				addContrastVehicle(contrastVehicle);
			}
		}
		function start(){
	  		window.setInterval(send,1000);
	  		function send(){
	  			webSocket.send("{\"stationId\":\""+stationId+"\",\"contrastVehicleId\":\""+contrastVehicleIdMap[stationId]+"\"}");
	  		}
	  	}
		setTimeout(start, 2000);
	}
	
	function addContrastVehicle(data){
		for(var i=2;i>0;i--){
			$("#warnVehicleList_"+i+"_0").empty().append($("#warnVehicleList_"+(i-1)+"_0").children());
			$("#warnVehicleList_"+i+"_1").text($("#warnVehicleList_"+(i-1)+"_1").text());
			$("#warnVehicleList_"+i+"_2").text($("#warnVehicleList_"+(i-1)+"_2").text());
			$("#warnVehicleList_"+i+"_3").text($("#warnVehicleList_"+(i-1)+"_3").text());
			$("#warnVehicleList_"+i+"_4").text($("#warnVehicleList_"+(i-1)+"_4").text());
		}
		$("#warnVehicleList_0_0").empty().append("<a class='lk-index-link' href=\"javascript: dealContrastVehicle('"+data.id+"')\";>"+data.carNum+"</a>");
		$("#warnVehicleList_0_1").text(data.namesOmit);
		$("#warnVehicleList_0_2").text(data.passdateView);
		$("#warnVehicleList_0_3").text(data.locationOmit);
		$("#warnVehicleList_0_4").text(data.isCheckedView);
		
		var texts = "<p class='lk-p'>车牌号："+data.carNum+"</p>"+
			"<p class='lk-p'>时间："+data.passdateView+"</p>"+
			"<p class='lk-p'>位置："+data.location+"</p>";
		vehicleWarningwin(data.id , texts);
			
		if(data.location.indexOf("前置卡口") != -1){
			flickerbd( 1, data.id);
		}else{
			flickerbd( 2, data.id);
		}
	}
	
	/**重点车辆预警提示弹出窗 
	 * titles : 标题
	 * par : id
	 * texts : 正文
	 * 调用方式 : vehicleWarningwin('重点车辆','id','测试');
	 * @*/
	function vehicleWarningwin(id,texts){
		try{
			audio.pause();
			audio.currentTime = 0;
			audio.play();//播放音效
		}catch(e){}
		try{
			swal({
				title: "<a href=\"javascript: dealContrastVehicle('"+id+"');\" class=\"lk-win-link\">发现预警车辆</a>",
				html: true,
				text: texts,
				imageUrl: "images/index/timg.gif",
				timer: 10000,
				showConfirmButton: true,
				confirmButtonText: "关闭"
			});
		}catch(e){}
	}
	
	/**处理车辆预警信息 @*/
	function dealContrastVehicle(id){
		ymPrompt.win({
			message : "contrastVehicle_detail?contrastVehicle.id=" + id,
			width : 1300,
			height : 800,
			title : '预警车辆详细信息',
			maxBtn : true,
			minBtn : true,
			closeBtn : true,
			iframe : true
		});
	}
	
	var contrastVehicleIdMap = {};
$(function(){
	loadContrastVehicle(stationId);
	createContrastVehicleSocket();	
});
