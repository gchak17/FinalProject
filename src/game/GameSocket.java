package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import message.*;

@ServerEndpoint(value = "/client.html/web", encoders = { MessageEncoder.class }, decoders = {
		MessageDecoder.class }, configurator = Configuration.class)
public class GameSocket {
	private static ConcurrentHashMap<String, List<Session>> sessions = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(Session curS, EndpointConfig config) {
		HttpSession httpSession = (HttpSession) curS.getUserProperties().get("HttpSession");
		httpSession.setAttribute("session", curS);
		String id = (String) httpSession.getAttribute("gameId");
		Room r = (Room) GameManager.getInstance().getRoomById(id);

		List<Session> ses;
		if (!sessions.containsKey(id)) {
			ses = Collections.synchronizedList(new ArrayList<Session>());
		} else {
			ses = sessions.get(id);
		}

		ses.add(curS);
		sessions.put(id, ses);

		if (ses.size() == r.getPlayers().size()) {
			Game g = new Game(r.getPlayers(), r.getRounds(), r.getTime(), id);
			GameManager.getInstance().addGame(g);
		}

	}

	@OnClose
	public void onClose(Session peer) throws IOException, EncodeException {
		
		HttpSession httpSession = (HttpSession) peer.getUserProperties().get("HttpSession");
		String id = (String) httpSession.getAttribute("gameId");
		List<Session> peers = sessions.get(id);
		peers.remove(peer);
		
		Game g = GameManager.getInstance().getGame(id);
		g.removePlayer((Player) httpSession.getAttribute("player"));

		JSONObject json = new JSONObject();
		json.put("command", "showplayers");
		for (Player p : g.getPoints().keySet()) {
			json.put(p.toString(), g.getPoints().get(p));
		}

		Message message = new Message(json);
		for (Session s : peers) {
				s.getBasicRemote().sendObject(message);
		}
		
	}

	@OnMessage
	public static void onMessage(Message message, Session session) throws IOException, EncodeException {
		HttpSession httpSession = (HttpSession) session.getUserProperties().get("HttpSession");

		String command = message.getJson().getString("command");
		JSONObject json = message.getJson();

		if (command.equals("checkStatus")) {

			json.put("answer", false);

			Player user = (Player) httpSession.getAttribute("player");

			if (user.isArtist()) {
				json.put("answer", true);
			}

			message.setJson(json);
			session.getBasicRemote().sendObject(message);

		} else if (command.equals("clear")) {
			String id = (String) httpSession.getAttribute("gameId");
			sendToEveryone(message, id);
		} else {
			sendToEveryoneButMe(message, httpSession, session);
		}
	}

	private static void sendToEveryoneButMe(Message message, HttpSession httpSession, Session session)
			throws IOException, EncodeException {
		String id = (String) httpSession.getAttribute("gameId");
		List<Session> peers = sessions.get(id);
		for (Session peer : peers) {
			if (!peer.equals(session)) {
				peer.getBasicRemote().sendObject(message);
			}
		}
	}

	private static void sendToEveryone(Message message, String id) throws IOException, EncodeException {
		List<Session> peers = sessions.get(id);
		for (Session peer : peers) {
			peer.getBasicRemote().sendObject(message);
		}
	}

	public static void sendMessage(String gameId, Message message) {

		JSONObject json = message.getJson();
		String command = json.getString("command");

		if (command.equals("addcanvaslisteners") || command.equals("removecanvaslisteners")
				|| command.contentEquals("chooseWord")) {

			sendOnlyToArtist(gameId, message);
			
		} else {
			try {
				sendToEveryone(message, gameId);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (EncodeException e) {
				e.printStackTrace();
			}
		}

//		JSONObject json = message.getJson();
//		String command = json.getString("command");
//
//		if (command.contentEquals("chooseWord")) {
//			HttpSession httpSession ;
//			for (Session peer : peers) {
//				httpSession = (HttpSession) peer.getUserProperties().get("HttpSession");
//				Player user = (Player) httpSession.getAttribute("player");
//				
//				if (!user.isArtist()) {
//					json.put("command", "dont choose");
//				} else {
//					json.put("command", "chooseWord");
//				}
//				try {
//					peer.getBasicRemote().sendObject(message);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (EncodeException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			return;
//		}

	}

	private static void sendOnlyToArtist(String gameId, Message message) {
		List<Session> peers = sessions.get(gameId);
		for (Session peer : peers) {
			if (((Player) ((HttpSession) peer.getUserProperties().get("HttpSession")).getAttribute("player"))
					.isArtist()) {
				try {
					peer.getBasicRemote().sendObject(message);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		}

	}

}