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
import cn.lhkj.project.contrast.entity.ContrastPerson;

@ServerEndpoint("/websocketContrastPerson")
public class WebSocketContrastPerson {
	
	private static final Logger logger = Logger.getLogger(WebSocketContrastPerson.class);
	
	@OnMessage
	public void onMessage(String message, Session session) throws Exception{
		try{
			logger.debug("WebSocketContrastPerson Received: " + message);
			if(StringUtil.isNull(message)) return;
			JSONObject jsonObject = JSONObject.fromObject(message);
			String stationId = jsonObject.getString("stationId");
			String contrastPersonId = jsonObject.getString("contrastPersonId");
			ContrastPerson contrastPerson = BaseDataCode.getContrastPersonMap().get(stationId);
			if(contrastPerson == null) return;
			if(contrastPerson.getId().equals(contrastPersonId)) return;
			session.getBasicRemote().sendText(StringUtil.obj2json(contrastPerson));
		}catch (Exception e){
			logger.error("WebSocketContrastPerson ERROR:"+ e.getMessage());
		}
	}
	
	@OnOpen
	public void onOpen(){
		logger.debug("WebSocketContrastPerson Client 连接成功！");
	}
	
	@OnClose
	public void onClose(){
		logger.debug("WebSocketContrastPerson Client 连接断开！");
	}
	
	@OnError
	public void onError(Throwable e, Session session){}
}