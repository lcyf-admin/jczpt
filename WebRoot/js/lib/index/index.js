	var audio = document.createElement("audio");
	audio.src = "source/warning.mp3";
	//audio.play();
	//audio.pause();
	//audio.currentTime = 0;
	
	/**退出系统*/
	function logout(){
		if(!window.confirm("确认注销？")) return;
		$.ajax({
			type:"post",
	        url: "logout",
			cache: false,
			async: true,
		    success: function(data){
		    	var location = (window.location+"").split("/"); 
		    	var basePath = location[0]+"//"+location[2]+"/"+location[3]; 
		    	window.location.href = basePath;
			}
	   	});
	};
	
	function manualData(){
		ymPrompt.win({
			message : "person_manualData",
			width : 900,
			height : 400,
			title : '手动录入人员信息',
			maxBtn : false,
			minBtn : false,
			closeBtn : true,
			iframe : true
		});
	}
	
	/**后台管理*/
	function manage(){
		window.location.href = "manage"
	}
	
	/**刷新本页 @*/
	function refresh(){
    	window.location.reload();
	}
	
	/**更多车辆预警 @*/
	function warnVehicleMore(){
		window.open("contrastVehicle_list");
	}
	
	/**更多人员预警 @*/
	function warnPersonMore(){
		window.open("contrastPerson_list");
	}
	
	/**更多车辆历史记录 @*/
	function priorVehicleMore(){
		window.open("vehicle_list");
	}
	
	/**进入统计页面 @*/
	function dataStatistics(){
		window.open("statistics");
	}
	
	/**数据统计 @*/
	function priorPersonMore(){
		window.open("person_list");
	}
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	var frquence1 = 0;//循环次数
	var frquence2 = 0;//循环次数
	var frquence3 = 0;//循环次数
	var frquence4 = 0;//循环次数
	var funs1;//
	var funs2;//
	var funs3;//
	var funs4;//
	/**设备状态边框闪烁 
	 * types : 事件类型；1-前置卡口，2-安检车道-车辆预警，5-安检车道-人员预警，3-数据门，4-车底扫描
	 * param : 参数传递
	 * 调用方式 : flickerbd(4,'id');
	 * @*/
	function flickerbd(types,params){
		var str = '{types: '+types+', params: "'+params+'"}';
		var tmp = eval ("(" + str + ")");
		if(1 == types){
			frquence1 = 0;
			$("#equipment_1").on("click",tmp,clickfun);
			window.clearTimeout(funs1);
			funs1 = window.setInterval(changecolor1,1000);
			
		}else if(2 == types || 5 == types){
			frquence2 = 0;
			$("#equipment_2").on("click",tmp,clickfun);
			window.clearTimeout(funs2);
			funs2 = window.setInterval(changecolor2,1000);
		}else if(3 == types){
			frquence3 = 0;
			$("#equipment_3").on("click",tmp,clickfun);
			window.clearTimeout(funs3);
			funs3 = window.setInterval(changecolor3,1000);
		}else if(4 == types){
			frquence4 = 0;
			$("#equipment_4").on("click",tmp,clickfun);
			window.clearTimeout(funs4);
			funs4 = window.setInterval(changecolor4,1000);
		}	
	}
	
	/**改变设备状态边框颜色 @*/
	function changecolor1(){
		if(frquence1 > 10){		 
			removesets(1);///////////
			return;
		}
		if(0 == frquence1){
			$("#equipment_1").children().eq(0).attr("class", "danger photo"); //设置元素的class
			$("#equipment_1").children().eq(1).attr("class", "dangerImg"); //设置元素的class
		}
		if(0 == (frquence1%2)){
			$("#equipment_1").css('border-color','red');
		}else{
			$("#equipment_1").css('border-color','#ddd');
		}	
		frquence1++;
	}
	
	/**改变设备状态边框颜色 @*/
	function changecolor2(){
		if(frquence2 > 10){		 
			removesets(2);///////////
			return;
		}
		if(0 == frquence2){
			$("#equipment_2").children().eq(0).attr("class", "danger photo"); //设置元素的class
			$("#equipment_2").children().eq(1).attr("class", "dangerImg"); //设置元素的class
		}
		if(0 == (frquence2%2)){
			$("#equipment_2").css('border-color','red');
		}else{
			$("#equipment_2").css('border-color','#ddd');
		}	
		frquence2++;
	}
	
	/**改变设备状态边框颜色 @*/
	function changecolor3(){
		if(frquence3 > 10){		 
			removesets(3);///////////
			return;
		}
		if(0 == frquence3){
			$("#equipment_3").children().eq(0).attr("class", "danger photo"); //设置元素的class
			$("#equipment_3").children().eq(1).attr("class", "dangerImg"); //设置元素的class
		}
		if(0 == (frquence3%2)){
			$("#equipment_3").css('border-color','red');
		}else{
			$("#equipment_3").css('border-color','#ddd');
		}	
		frquence3++;
	}
	
	/**改变设备状态边框颜色 @*/
	function changecolor4(){
		if(frquence4 > 10){		 
			removesets(4);///////////
			return;
		}
		if(0 == frquence4){
			$("#equipment_4").children().eq(0).attr("class", "danger photo"); //设置元素的class
			$("#equipment_4").children().eq(1).attr("class", "dangerImg"); //设置元素的class
		}
		if(0 == (frquence4%2)){
			$("#equipment_4").css('border-color','red');
		}else{
			$("#equipment_4").css('border-color','#ddd');
		}	
		frquence4++;
	}
	
	/**设备状态边框去除闪烁 @*/
	function removesets(types){
		if(1 == types){
			window.clearTimeout(funs1);//去掉定时器
		}else if(2 == types || 5 == types){
			types = 2;
			window.clearTimeout(funs2);//去掉定时器
		}else if(3 == types){
			window.clearTimeout(funs3);//去掉定时器
		}else if(4 == types){
			window.clearTimeout(funs4);//去掉定时器
		}
		$("#equipment_"+types).unbind('click', clickfun);//取消绑定事件
		$("#equipment_"+types).css('border-color','#ddd');
		$("#equipment_"+types).children().eq(0).removeClass(); //移除元素的所有class 
		$("#equipment_"+types).children().eq(1).removeClass(); //移除元素的所有class
		
	}
	
	/**设备状态边框点击事件 @*/
	function clickfun(events){
		var types = events.data.types;
		removesets(events.data.types);//去掉定时器
		if(types == 1 || types == 2){//前值卡口或车道
			dealContrastVehicle(events.data.params);
		}else if(types == 3 || types == 5){//数据门
			dealWarnPerson(events.data.params);
		}
	}
	

$(function(){
	if("admin" == account){
		$.ajax({//加载部门树
			url:"orgzon_ajaxOrgzonZTree",
	 		type:"post",
	 		async:true,
	 		dataType:"json",
	 		success:function(data){
	 			$.fn.zTree.init($("#orgzonZTree"),{
	 				view: {dblClickExpand: true,showLine: false,selectedMulti: false},
					data: {simpleData: {enable: true}},
					callback: {
						onClick: function(e, treeId, treeNode){
							if(!treeNode.isLeaf) return;
							$("#station").val(treeNode.name);
							stationId = treeNode.treeCode;
							loadVehicle(stationId);
							loadPerson(stationId);
							loadContrastVehicle(stationId);
							loadContrastPerson(stationId);
							updateSession(treeNode.id);
							hideMenu("orgzonZTree");
						}
					}
				}, data);
	 		}
		});
	}
	
	function updateSession(orgId){
		$.ajax({
			url:"user_ajaxUpdateSession",
	 		type:"post",
	 		async:true,
	 		data:{"orgId": orgId},
	 		dataType:"json",
	 		success:function(data){}
		});
	}
	
	
	$("body").on("click",function(){
		if(audio.currentTime == 0) return;
		audio.pause();
		audio.currentTime = 0;
	});
});