/**时时车辆////////////////////////////////////////////////////////*/

	/**加载车辆历史数据 @*/
	function loadVehicle(stationId){
		resetVehicle();
		if(stationId == "") return;
		$.ajax({
	 		url: "vehicle_ajaxVehicleList.action",
	 		type: "post",
	 		data: { "records": 5, "stationId": stationId},
	 		async: true,
	 		dataType: "json",
	 		success: function(vehicle){
	 			for(var i=0;i<vehicle.length;i++){
	 				if(i==0){
	 					curentVehicleId = vehicle[0].id;
	 					$("#vehicle_carNum").empty().append("<a class='lk-index-link' href=\"javascript: vehicleDetail('"+vehicle[0].id+"')\";>"+vehicle[0].carNum+"</a>");
	 					$("#vehicle_plateColor").text(vehicle[0].plateColor);
	 					$("#vehicle_passdate").text(vehicle[0].passdateView);
	 					$("#vehicle_laneName").text(vehicle[0].laneName);
	 					$("#driver_name").text(vehicle[0].driverName);
	 					$("#driver_idcard").text(vehicle[0].driverIdcard);
	 					
	 					$("#vehicle_carImg").attr("src",vehicle[0].carImg);
	 					$("#vehicle_driverImg").attr("src",vehicle[0].cardImg);
	 				}
	 				$("#vehicleList_"+i+"_0").empty().append("<a class='lk-index-link' href=\"javascript: vehicleDetail('"+vehicle[i].id+"')\";>"+vehicle[i].carNum+"</a>");
	 				$("#vehicleList_"+i+"_1").text(vehicle[i].plateColor);
	 				$("#vehicleList_"+i+"_2").text(vehicle[i].driverIdcard);
	 				$("#vehicleList_"+i+"_3").text(vehicle[i].passdateView);
	 				$("#vehicleList_"+i+"_4").text(vehicle[i].laneName);
	 			}
	 		}
		});
	};
	
	/**重置时时车辆信息 @*/
	function resetVehicle(){
		curentVehicleId = "";
		$("#vehicle_carNum").empty().append("-");
		$("#vehicle_plateColor").text("-");
		$("#vehicle_passdate").text("-");
		$("#vehicle_laneName").text("-");
		$("#driver_name").text("-");
		$("#driver_idcard").text("-");
		$("#vehicle_carImg").attr("src","");
		$("#vehicle_driverImg").attr("src","");
		for(var i=0;i<4;i++){
			$("#vehicleList_"+i+"_0").empty().append("-");
			$("#vehicleList_"+i+"_1").text("-");
			$("#vehicleList_"+i+"_2").text("-");
			$("#vehicleList_"+i+"_3").text("-");
			$("#vehicleList_"+i+"_4").text("-");
		}
	}
	
	function createVehicleSocket(){
		var location = (window.location+"").split("/"); 
		var basePath = location[0]+"//"+location[2]+"/"+location[3]; 
		basePath = basePath.replace("http://", "ws://")
		var webSocket = new WebSocket(basePath+"/websocketVehicle");
	  	webSocket.onerror = function(event){ }
	  	webSocket.onopen = function(event){ }
	  	webSocket.onmessage = function(event){
	  		var data = event.data;
	  		if(data != ""){
	  			var vehicle = eval("("+data+")");
	  			if(vehicle.id == curentVehicleId){
	  				freshVehicle(vehicle);
	  			}else{
	  				curentVehicleId = vehicle.id;
		  			addVehicle(vehicle);
	  			}
	  		}
	  	}
	  	function start(){
	  		window.setInterval(send,500);
	  		function send(){
	  			webSocket.send(stationId);
	  		}
	  	}
	  	setTimeout(start,1000);
	}
	
	/**刷新车辆信息*/
	function freshVehicle(data){
		$("#vehicleList_0_2").text(data.driverIdcard);
		$("#driver_name").text(data.driverName);
		$("#driver_idcard").text(data.driverIdcard);
		if(data.driverName != "-"){
			$("#vehicle_driverImg").attr("src",data.cardImg);
		}
	}
	
	function addVehicle(data){
		for(var i=4;i>0;i--){
			$("#vehicleList_"+i+"_0").empty().append($("#vehicleList_"+(i-1)+"_0").children());
			$("#vehicleList_"+i+"_1").text($("#vehicleList_"+(i-1)+"_1").text());
			$("#vehicleList_"+i+"_2").text($("#vehicleList_"+(i-1)+"_2").text());
			$("#vehicleList_"+i+"_3").text($("#vehicleList_"+(i-1)+"_3").text());
			$("#vehicleList_"+i+"_4").text($("#vehicleList_"+(i-1)+"_4").text());
		}
		$("#vehicleList_0_0").empty().append("<a class='lk-index-link' href=\"javascript: vehicleDetail('"+data.id+"')\";>"+data.carNum+"</a>");
		$("#vehicleList_0_1").text(data.plateColor);
		$("#vehicleList_0_2").text(data.driverIdcard);
		$("#vehicleList_0_3").text(data.passdateView);
		$("#vehicleList_0_4").text(data.laneName);
		
		$("#vehicle_carNum").empty().append("<a class='lk-index-link' href=\"javascript: vehicleDetail('"+data.id+"')\";>"+data.carNum+"</a>");
		$("#vehicle_plateColor").text(data.plateColor);
		$("#vehicle_passdate").text(data.passdateView);
		$("#vehicle_laneName").text(data.laneName);
		$("#driver_name").text(data.driverName);
		$("#driver_idcard").text(data.driverIdcard);
			
		$("#vehicle_carImg").attr("src",data.carImg);
		$("#vehicle_driverImg").attr("src",data.cardImg);
	}
	
	/**车辆详细 @*/
	function vehicleDetail(id){
		ymPrompt.win({
			message : "vehicle_detail.action?vehicle.id="+id,
			width : 1050,
			height : 850,
			title : '车辆详情',
			maxBtn : false,
			minBtn : false,
			closeBtn : true,
			iframe : true
		});
	}
	
	var curentVehicleId = "";
$(function(){
	loadVehicle(stationId);
	createVehicleSocket();
});
