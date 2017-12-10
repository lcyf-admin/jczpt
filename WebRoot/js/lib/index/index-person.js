/**时时人员////////////////////////////////////////////////////////*/

	/**加载人员历史数据 @*/
	function loadPerson(stationId){
		resetPerson();
		if(stationId == "") return;
		$.ajax({
	 		url: "person_ajaxPersonList",
	 		type: "post",
	 		data: { "records": 4, "stationId": stationId},
	 		async: true,
	 		dataType: "json",
	 		success: function(person){
	 			for(var i=0;i<person.length;i++){
	 				if(i==0){
	 					curentPersonId = person[i].id;
	 					$("#person_names").empty().append("<a class='lk-index-link' href=\"javascript: personDetail('"+person[i].id+"')\";>"+person[i].names+"</a>");
	 					$("#person_idcard").text(person[i].idcard);
	 					$("#person_location").text(person[i].location);
	 					$("#person_captureTime").text(person[i].captureTimeView);
	 					$("#person_isAPerson").text(person[i].isAPerson);
	 					if(person[0].localImgUrl == ""){
	 						$("#person_img").attr("src",person[0].imgUrl); 
	 					}else{
	 						$("#person_img").attr("src",person[0].localImgUrl);
	 					}
	 				}
	 				$("#personList_"+i+"_0").empty().append("<a class='lk-index-link' href=\"javascript: personDetail('"+person[i].id+"')\";>"+person[i].names+"</a>");
	 				$("#personList_"+i+"_1").text(person[i].idcard);
	 				$("#personList_"+i+"_2").text(person[i].age);
	 				$("#personList_"+i+"_3").text(person[i].location);
	 				$("#personList_"+i+"_4").text(person[i].captureTimeView);
	 			}
	 		}
		});
	};
	
	/**重置时时人员信息 @*/
	function resetPerson(){
		curentPersonId = "";
		$("#person_names").empty().append("-");
		$("#person_idcard").text("-");
		$("#person_location").text("-");
		$("#person_captureTime").text("-");
		$("#person_isAPerson").text("-");
		$("#person_img").attr("src","");
		for(var i=0;i<4;i++){
			$("#personList_"+i+"_0").empty().append("-");
			$("#personList_"+i+"_1").text("-");
			$("#personList_"+i+"_2").text("-");
			$("#personList_"+i+"_3").text("-");
			$("#personList_"+i+"_4").text("-");
		}
	}
	
	function createPersonSocket(){
		var location = (window.location+"").split("/"); 
		var basePath = location[0]+"//"+location[2]+"/"+location[3]; 
		basePath = basePath.replace("http://", "ws://")
		var webSocket = new WebSocket(basePath+"/websocketPerson");
	  	webSocket.onerror = function(event){ }
	  	webSocket.onopen = function(event){ }
	  	webSocket.onmessage = function(event){
	  		var data = event.data;
	  		if(data != ""){
	  			var person = eval("("+data+")");
	  			if(person.id == curentPersonId) return;
	  			curentPersonId = person.id;
	  			addPerson(person);
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
	
	function addPerson(person){
		for(var i=3;i>0;i--){
			$("#personList_"+i+"_0").empty().append($("#personList_"+(i-1)+"_0").children());
			$("#personList_"+i+"_1").text($("#personList_"+(i-1)+"_1").text());
			$("#personList_"+i+"_2").text($("#personList_"+(i-1)+"_2").text());
			$("#personList_"+i+"_3").text($("#personList_"+(i-1)+"_3").text());
			$("#personList_"+i+"_4").text($("#personList_"+(i-1)+"_4").text());
		}
		$("#personList_0_0").empty().append("<a class='lk-index-link' href=\"javascript: personDetail('"+person.id+"')\";>"+person.names+"</a>");
		$("#personList_0_1").text(person.idcard);
		$("#personList_0_2").text(person.age);
		$("#personList_0_3").text(person.location);
		$("#personList_0_4").text(person.captureTimeView);
			
		$("#person_names").empty().append("<a class='lk-index-link' href=\"javascript: personDetail('"+person.id+"')\";>"+person.names+"</a>");
		$("#person_idcard").text(person.idcard);
		$("#person_location").text(person.location);
		$("#person_captureTime").text(person.captureTimeView);
		$("#person_isAPerson").text(person.isAPerson);
		if(person.localImgUrl == ""){
			$("#person_img").attr("src",person.imgUrl); 
		}else{
			$("#person_img").attr("src",person.localImgUrl);
		}
	}
	
	function personDetail(id){
		ymPrompt.win({
			message : "person_detail?person.id="+id,
			width : 900,
			height : 800,
			title : '人员详细',
			maxBtn : false,
			minBtn : false,
			closeBtn : true,
			iframe : true
		});
	}

	var curentPersonId = "";
	
$(function(){
	loadPerson(stationId);
	createPersonSocket();
});
