package cn.lhkj.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastVehicle;

@ServerEndpoint("/websocketContrastVehicle")
public class WebSocketContrastVehicle {
	
	private static final Logger logger = Logger.getLogger(WebSocketContrastVehicle.class);
	
	@OnMessage
	public void onMessage(String message, Session session) throws Exception{
		try{
			logger.debug("WebSocketContrastVehicle Received: " + message);
			if(StringUtil.isNull(message)) return;
			JSONObject jsonObject = JSONObject.fromObject(message);
			String stationId = jsonObject.getString("stationId");
			String contrastVehicleId = jsonObject.getString("contrastVehicleId");
			ContrastVehicle contrastVehicle = BaseDataCode.getContrastVehicleMap().get(stationId);
			if(contrastVehicle == null) return;
			if(contrastVehicle.getId().equals(contrastVehicleId)) return;
			String text = StringUtil.obj2json(contrastVehicle);
			session.getBasicRemote().sendText(text);
		}catch (Exception e){
			logger.error("WebSocketContrastVehicle ERROR:"+ e.getMessage());
		}
	}
	
	@OnOpen
	public void onOpen(){
		logger.debug("WebSocketContrastVehicle Client 连接成功！");
	}
	
	@OnClose
	public void onClose(){
		logger.debug("WebSocketContrastVehicle Client 连接断开！");
	}
	
	@OnError
	public void onError(Throwable e, Session session){}
}