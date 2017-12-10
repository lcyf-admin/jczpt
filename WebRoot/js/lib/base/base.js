
	/**退出系统*/
	function logout(){
		if(!window.confirm("确认注销？")) return;
		$.ajax({
			type:"post",
	        url: "logout",
			cache: false,
			async: false,
		    success: function(data){
		    	var location = (window.location+'').split('/'); 
		    	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
		    	window.location.href = basePath;
			}
	   	});
	};
	
	/**返回前台*/
	function index(){
		window.location.href = "index"
	}
	
	var feedbackIcons = { valid: "glyphicon glyphicon-ok", invalid: "glyphicon glyphicon-remove", validating: "glyphicon glyphicon-refresh" };
	
$(function(){
    /**所有的输入框清掉缓存*/
	$("input[type='text']").attr("autocomplete","off");
	
	/**所有输入框 点击回车 触发搜索函数*/
	$("body").on("keypress","input[type='text']",function(data){
		try{
			if(event.keyCode==13) mySearch();
		}catch (e) {}
	});
	
	$("a").each(function(){
		var href = $(this).attr("href");
		if(!href){
			$(this).attr("href","javascript:void(0);");
		}
	});
});

	/**js生成UUID @*/
	function generateUUID() {
		var d = new Date().getTime();
		var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		  var r = (d + Math.random()*16)%16 | 0;
		  d = Math.floor(d/16);
		  return (c=='x' ? r : (r&0x3|0x8)).toString(16);
		});
		return uuid;
	};
	
	/**
	 * text框下显示ztree
	 * @param inputId 点击框的id
	 * @param ztreeId ztree的id
	 */
	function showMenu(inputId,ztreeId) {
		var obj = $("#"+inputId);
		var cityOffset = obj.offset();
		$("#"+ztreeId).css("width",obj.width()+45);
		$("#"+ztreeId).css({left:cityOffset.left + "px", top:cityOffset.top + obj.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown", function(event){
			if($(event.target).parents("#"+ztreeId).length == 0){
				hideMenu(ztreeId);
			}
		});
	}
	
	/**隐藏ztree框@*/
	function hideMenu(id) {
		$("#"+id).fadeOut("fast");
		$("body").unbind("mousedown");
	}
	
	/**关闭页面@*/
	function quiet(){
		try{window.opener.refreshGrid();}catch(e){
			try{window.parent.refreshGrid();}catch(e){}
		}
		window.opener = null;
		window.open("","_self");
		window.close();
	};
	
	/**清空table里input的值*/
	function resetInput(tableId){
		var $table = $("#"+tableId);
		var text = $("input[type='text']",$table);
	    for(var i=0;i<text.length;i++){
	    	$(text.eq(i)).val("");
	    }
	    var select = $("select",$table);
	    for(var i=0;i<select.length;i++){
	    	$(text.eq(i)).val("");
	    }
	    var text = $("input[type='hidden']",$table);
	    for(var i=0;i<text.length;i++){
	    	$(text.eq(i)).val("");
	    }
	    var text = $("input[type='checkbox']",$table);
	    for(var i=0;i<text.length;i++){
	    	text.eq(i).attr("checked",false);
	    }
	    var text = $("input[type='radio']",$table);
	    for(var i=0;i<text.length;i++){
	    	text.eq(i).attr("checked",false);
	    }
	}
