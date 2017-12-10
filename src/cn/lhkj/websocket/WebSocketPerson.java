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
import cn.lhkj.project.person.entity.Person;

@ServerEndpoint("/websocketPerson")
public class WebSocketPerson {
	
	private static final Logger logger = Logger.getLogger(WebSocketPerson.class);
	
	@OnMessage
	public void onMessage(String message, Session session) throws Exception{
		try{
			logger.debug("WebSocketPerson Received: " + message);
			if(StringUtil.isNull(message)) return;
			if(message.length() > 13){
				message = message.substring(0,13);
			}
			Person p = BaseDataCode.getPersonMap().get(message);
			if(p == null) return;
			session.getBasicRemote().sendText(StringUtil.obj2json(p));
		}catch (Exception e){
			logger.error("WebSocketPerson ERROR:"+e.getMessage());
		}
	}
	
	@OnOpen
	public void onOpen(){
		logger.debug("WebSocketPerson Client 连接成功！");
	}
	
	@OnClose
	public void onClose(){
		logger.debug("WebSocketPerson Client 连接断开！");
	}
	
	@OnError
	public void onError(Throwable e, Session session){}
}