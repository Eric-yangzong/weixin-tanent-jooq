package com.bdhanbang.weixin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.bdhanbang.base.jooq.GenSchema;
import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.weixin.entity.FriendMessage;
import com.bdhanbang.weixin.jooq.tables.QFriendMessage;
import com.bdhanbang.weixin.service.FriendMessageService;

@Service("friendMessageService")
public class FriendMessageServiceImpl extends BaseServiceImpl<QFriendMessage, FriendMessage>
		implements FriendMessageService {

	public List<FriendMessage> friendMessages(String realSchema, String userId) {

		QFriendMessage friendMessageQ = new QFriendMessage();

		friendMessageQ.setSchema(new GenSchema(realSchema));

		Result<Record2<String, Integer>> result = dsl
				.select(friendMessageQ.FROM_USER_ID, friendMessageQ.FROM_USER_ID.count()).from(friendMessageQ)
				.where(friendMessageQ.IS_SEND.eq((short) 0).and(friendMessageQ.TO_USER_ID.eq(userId)))
				.groupBy(friendMessageQ.FROM_USER_ID).fetch();

		List<FriendMessage> friendMessages = new ArrayList<>();

		result.forEach(x -> {
			FriendMessage friendMessage = new FriendMessage();

			friendMessage.setFromUserId(x.get(friendMessageQ.FROM_USER_ID));
			friendMessage.setIsSend(Short.valueOf(x.get(1).toString()));

			friendMessages.add(friendMessage);

		});

		return friendMessages;

	}

}