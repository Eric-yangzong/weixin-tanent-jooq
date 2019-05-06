package com.bdhanbang.weixin.ws;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.weixin.common.AppCommon;
import com.bdhanbang.weixin.configuration.MyApplicationContextAware;
import com.bdhanbang.weixin.entity.Chat;
import com.bdhanbang.weixin.entity.Friend;
import com.bdhanbang.weixin.entity.FriendMessage;
import com.bdhanbang.weixin.jooq.tables.QFriend;
import com.bdhanbang.weixin.jooq.tables.QFriendMessage;
import com.bdhanbang.weixin.service.FriendMessageService;
import com.bdhanbang.weixin.service.FriendService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ServerEndpoint(value = "/websocket/{userId}/{tenantId}")
@Component
public class MyWebSocket {

	// ConcurrentHashMap是线程安全的，而HashMap是线程不安全的。
	private static ConcurrentHashMap<String, Session> mapUS = new ConcurrentHashMap<String, Session>();
	private static ConcurrentHashMap<Session, String> mapSU = new ConcurrentHashMap<Session, String>();

	private FriendMessageService friendMessageService = (FriendMessageService) MyApplicationContextAware
			.getApplicationContext().getBean("friendMessageService");
	private FriendService friendService = (FriendService) MyApplicationContextAware.getApplicationContext()
			.getBean("friendService");

	private void buildFriend(Chat chat, String tenantId) {
		String realSchema = tenantId + AppCommon.scheam;

		String userId = chat.getMine().getId();
		String myAvatar = chat.getMine().getAvatar();
		String myName = chat.getMine().getUsername();

		String friendId = chat.getTo().getId();
		String friendAvatar = chat.getTo().getAvatar();
		String friendName = "";

		Query query = new Query();

		query.add(new Query("userId", userId));
		query.add(new Query("friendId", friendId));

		List<Friend> friends = friendService.queryList(realSchema, QFriend.class, Friend.class, query.getQuerys());

		if (Objects.isNull(friends) || friends.size() == 0) {
			Friend myFriend = new Friend();

			myFriend.setId(UUID.randomUUID());
			myFriend.setUserId(userId);
			myFriend.setFriendId(friendId);
			myFriend.setFriendName(friendName);
			myFriend.setAvatar(friendAvatar);
			myFriend.setBuildTime(LocalDateTime.now());

			// user表中写一条记录
			friendService.insertEntity(realSchema, QFriend.class, myFriend);

			Friend friend = new Friend();

			friend.setId(UUID.randomUUID());
			friend.setUserId(friendId);
			friend.setFriendId(userId);
			friend.setFriendName(myName);
			friend.setAvatar(myAvatar);
			friend.setBuildTime(LocalDateTime.now());

			friendService.insertEntity(realSchema, QFriend.class, friend);

		}
	}

	// 连接建立成功调用的方法
	@OnOpen
	public void onOpen(Session session, @PathParam("userId") String userId, @PathParam("tenantId") String tenantId) {

		String realSchema = tenantId + AppCommon.scheam;

		mapUS.put(userId, session);
		mapSU.put(session, userId);

		sendDelayedMessage(realSchema, userId, session);

	}

	private void sendDelayedMessage(String realSchema, String userId, Session session) {
		StringBuffer buf = new StringBuffer();

		Query query = new Query();
		query.add(new Query("toUserId", userId));
		query.add(new Query("isSend", 0));

		List<FriendMessage> friends = friendMessageService.queryList(realSchema, QFriendMessage.class,
				FriendMessage.class, query.getQuerys());

		friends.sort((x, y) -> x.getSendTime().compareTo(y.getSendTime()));

		friends.forEach(x -> {
			buf.append(x.getContent());
			buf.append(",");
		});

		if (buf.length() > 0) {
			buf.setLength(buf.length() - 1);
			session.getAsyncRemote().sendText(String.format("[%s]", buf.toString()));

			friends.forEach(x -> {
				x.setIsSend((short) 1);
				friendMessageService.updateEntity(realSchema, QFriendMessage.class, x);
			});

		}

	}

	// 连接关闭调用的方法
	@OnClose
	public void onClose(Session session) {
		String userId = mapSU.get(session);
		if (userId != null && userId != "") {
			mapUS.remove(userId);
			mapSU.remove(session);
		}
	}

	// 收到客户端消息后调用的方法
	@OnMessage
	public void onMessage(String message, Session session, @PathParam("tenantId") String tenantId) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		String realSchema = tenantId + AppCommon.scheam;

		Chat chat = mapper.readValue(message, Chat.class);

		this.buildFriend(chat, tenantId);

		String type = chat.getTo().getType();
		String toId = chat.getTo().getId();

		Chat.SendMessage toMessage = new Chat.SendMessage();

		toMessage.setSendId(chat.getMine().getId());
		toMessage.setToId(toId);
		toMessage.setAvatar(chat.getMine().getAvatar());
		toMessage.setType(type);
		toMessage.setContent(chat.getMine().getContent());
		toMessage.setTimestamp((new Date()).getTime());
		toMessage.setMine(false);
		toMessage.setUsername(chat.getMine().getUsername());

		switch (type) {
		case "friend": // 单聊,记录到db
			if (mapUS.containsKey(toId)) { // 如果在线，及时推送

				FriendMessage friendMessage = new FriendMessage();

				friendMessage.setFromUserId(chat.getMine().getId());
				friendMessage.setToUserId(chat.getTo().getId());
				friendMessage.setIsDel((short) 0);
				friendMessage.setIsBack((short) 0);
				friendMessage.setIsSend((short) 1);
				friendMessage.setId(UUID.randomUUID());
				friendMessage.setContent(toMessage.toString());

				friendMessageService.insertEntity(realSchema, QFriendMessage.class, friendMessage);
				mapUS.get(toId).getAsyncRemote().sendText(toMessage.toString()); // 发送消息给对方
			} else { // 如果不在线 就记录到数据库，下次对方上线时推送给对方。

				FriendMessage friendMessage = new FriendMessage();
				friendMessage.setFromUserId(chat.getMine().getId());
				friendMessage.setToUserId(chat.getTo().getId());
				friendMessage.setIsDel((short) 0);
				friendMessage.setIsBack((short) 0);
				friendMessage.setIsSend((short) 0);
				friendMessage.setSendTime(LocalDateTime.now());
				friendMessage.setId(UUID.randomUUID());
				friendMessage.setContent(toMessage.toString());

				friendMessageService.insertEntity(realSchema, QFriendMessage.class, friendMessage);

			}
			break;
		default:
			break;
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
			mapUS.remove(userId);
			mapSU.remove(session);
		}
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 */
	public void sendMessage(Session session, String message) {
		session.getAsyncRemote().sendText(message);
	}

}