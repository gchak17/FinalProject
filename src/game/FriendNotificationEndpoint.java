package game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import org.json.*;
import dao.Account;
import message.*;

@ServerEndpoint(value = "/notifications", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class },
											configurator = Configuration.class)		
public class FriendNotificationEndpoint {

	private static Map<Long, Session> online = new HashMap<Long, Session>();
	
	@OnOpen
	public void onOpen(Session session) {
		HttpSession httpSession = (HttpSession) session.getUserProperties().get("HttpSession");
		Account user = (Account)httpSession.getAttribute("user");
		online.put(user.getID(), session);
	}
	
	@OnClose
	public void onClose(Session session){
		HttpSession httpSession = (HttpSession) session.getUserProperties().get("HttpSession");
		Account user = (Account)httpSession.getAttribute("user");
		online.remove(user.getID());
	}
	
	
	@OnMessage
	public void onMessage(Message message, Session session) throws IOException, EncodeException {
		JSONObject request = message.getJson();
		HttpSession user = (HttpSession)session.getUserProperties().get("HttpSession");
		Long senderID = request.getLong("senderID");
		Long receiverID = request.getLong("receiverID");
		
		JSONObject notification = new JSONObject();
		
		notification.put("sender", senderID);
		
		if(online.containsKey(receiverID)) {
			Session reciever = online.get(receiverID);
			Message notificationMessage = new Message(notification);
			reciever.getBasicRemote().sendObject(notificationMessage);
		}
		
	}
	
	@OnError
	    public void onError(Session session, Throwable t) {
	        t.printStackTrace();
	    }
	
	
	
		
}
