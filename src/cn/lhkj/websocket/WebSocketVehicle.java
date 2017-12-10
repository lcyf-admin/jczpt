package cn.lhkj.websocket;


import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.vehicle.entity.Vehicle;

@ServerEndpoint("/websocketVehicle")
public class WebSocketVehicle {
	
	private static final Logger logger = Logger.getLogger(WebSocketVehicle.class);
	
	@OnMessage
	public void onMessage(String message, Session session){
		try{
			logger.debug("WebSocketVehicle Received: " + message);
			if(StringUtil.isNull(message)) return;
			Vehicle vehicle = BaseDataCode.vehicleMap.get(message);
			if(vehicle == null) return;
			session.getBasicRemote().sendText(StringUtil.obj2json(vehicle));
		}catch (Exception e){
			logger.error("WebSocketVehicle ERROR:"+e.getMessage());
		}
	}
	
	@OnOpen
	public void onOpen(){
		logger.debug("WebSocketVehicle Client 连接成功！");
	}
	
	@OnClose
	public void onClose(){
		logger.debug("WebSocketVehicle Client 连接断开！");
	}
	
	@OnError
	public void onError(Throwable e, Session session){}
}