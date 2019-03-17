package com.bdhanbang.weixin.ws;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.bdhanbang.weixin.entity.FriendMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class MyWebSocket {

	// ConcurrentHashMap是线程安全的，而HashMap是线程不安全的。
	private static ConcurrentHashMap<String, Session> mapUS = new ConcurrentHashMap<String, Session>();
	private static ConcurrentHashMap<Session, String> mapSU = new ConcurrentHashMap<Session, String>();

	// 连接建立成功调用的方法
	@OnOpen
	public void onOpen(Session session, @PathParam("userId") String userId) {
		String jsonString = "{'content':'online','id':" + userId + ",'type':'onlineStatus'}";
		for (Session s : session.getOpenSessions()) { // 循环发给所有在线的人
			s.getAsyncRemote().sendText(jsonString); // 上线通知
		}
		mapUS.put(userId + "", session);
		mapSU.put(session, userId + "");
		// 更新redis中的用户在线状态
		// RedisUtils.set(userId+"_status","online");
		// logger.info("用户" + userId + "进入llws,当前在线人数为" + mapUS.size());
		sendDelayedMessage(userId, session);

	}

	private void sendDelayedMessage(String userId, Session session) {
		// String id = userId + "_msg";

		String buf = new String();

		session.getAsyncRemote().sendText(buf.toString());

	}

	// 连接关闭调用的方法
	@OnClose
	public void onClose(Session session) {
		String userId = mapSU.get(session);
		if (userId != null && userId != "") {
			// 更新redis中的用户在线状态
			// RedisUtils.set(userId + "_status", "offline");
			String jsonString = "{'content':'offline','id':" + userId + ",'type':'onlineStatus'}";
			for (Session s : session.getOpenSessions()) { // 循环发给所有在线的人
				s.getAsyncRemote().sendText(jsonString); // 下线通知
			}
			mapUS.remove(userId);
			mapSU.remove(session);
			// logger.info("用户" + userId + "退出llws,当前在线人数为" + mapUS.size());
		}
	}

	// 收到客户端消息后调用的方法
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonObject = mapper.readTree(message);

		String type = jsonObject.path("to").path("type").textValue();
		if (type.equals("onlineStatus")) {
			for (Session s : session.getOpenSessions()) { // 循环发给所有在线的人
				ObjectNode toMessage = mapper.createObjectNode();
				toMessage.put("id", jsonObject.path("mine").path("id").textValue());
				toMessage.put("content", jsonObject.path("mine").path("content").textValue());
				toMessage.put("type", type);
				s.getAsyncRemote().sendText(toMessage.toString());
			}
		} else {
			String toId = jsonObject.path("to").path("id").asText();
			SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			Date date = new Date();
			String time = df.format(date);
			((ObjectNode) jsonObject).put("time", time);
			ObjectNode toMessage = mapper.createObjectNode();
			toMessage.put("avatar", jsonObject.path("mine").path("avatar").textValue());
			toMessage.put("type", type);
			toMessage.put("content", jsonObject.path("mine").path("content").textValue());
			toMessage.put("timestamp", date.getTime());
			toMessage.put("time", time);
			toMessage.put("mine", false);
			toMessage.put("username", jsonObject.path("mine").path("username").textValue());
			if (type.equals("friend") || type.equals("fankui")) {
				toMessage.put("id", jsonObject.path("mine").path("id").textValue());
			} else {
				toMessage.put("id", jsonObject.path("to").path("id").textValue());
			}
			switch (type) {
			case "friend": // 单聊,记录到mongo
				if (mapUS.containsKey(toId + "")) { // 如果在线，及时推送

					FriendMessage friendMessage = new FriendMessage();

					friendMessage.setFromUserId(jsonObject.path("mine").path("id").textValue());
					friendMessage.setToUserId(jsonObject.path("to").path("id").textValue());
					friendMessage.setIsDel(0);
					friendMessage.setIsBack(0);
					friendMessage.setIsSend(1);
					friendMessage.setId(null);
					friendMessage.setContent(toMessage.toString());

					if (friendMessage.getId() == null || friendMessage.getId().trim().equals("")) {
						String id = "";// sysCodeBiz.getVoucherId("friendmessage");
						friendMessage.setId(id);
					}

					// friendMessageService.insert(friendMessage);
					mapUS.get(toId + "").getAsyncRemote().sendText(toMessage.toString()); // 发送消息给对方
					// logger.info("单聊-来自客户端的消息:" + toMessage.toString());
				} else { // 如果不在线 就记录到数据库，下次对方上线时推送给对方。

					FriendMessage friendMessage = new FriendMessage();
					friendMessage.setFromUserId(jsonObject.path("mine").path("id").textValue());
					friendMessage.setToUserId(jsonObject.path("to").path("id").textValue());
					friendMessage.setIsDel(0);
					friendMessage.setIsBack(0);
					friendMessage.setIsSend(0);
					friendMessage.setSendTime(null);
					friendMessage.setId(null);
					friendMessage.setContent(toMessage.toString());

					if (friendMessage.getId() == null || friendMessage.getId().trim().equals("")) {
						String id = "";
						friendMessage.setId(id);
					}

					try {
						// friendMessageService.insert(friendMessage);
					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println(String.format("toId:%s\n%s:%s", toId, "_msg", toMessage.toString()));
					// logger.info("单聊-对方不在线，消息已存入redis:" + toMessage.toString());
				}
				break;
			default:
				break;
			}
		}

	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		String userId = mapSU.get(session);
		if (userId != null && userId != "") {
			// 更新redis中的用户在线状态
			// RedisUtils.set(userId+"_status","offline");
			String jsonString = "{'content':'offline','id':" + userId + ",'type':'onlineStatus'}";
			for (Session s : session.getOpenSessions()) { // 循环发给所有在线的人
				s.getAsyncRemote().sendText(jsonString); // 下线通知
			}
			mapUS.remove(userId);
			mapSU.remove(session);
			// logger.info("用户" + userId + "退出llws！当前在线人数为" + mapUS.size());
		}
		// logger.error("llws发生错误!");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 */
	public void sendMessage(Session session, String message) {
		session.getAsyncRemote().sendText(message);
	}

}