package com.bdhanbang.weixin.service;

import java.util.List;

import com.bdhanbang.base.service.BaseService;
import com.bdhanbang.weixin.entity.FriendMessage;
import com.bdhanbang.weixin.jooq.tables.QFriendMessage;

public interface FriendMessageService extends BaseService<QFriendMessage, FriendMessage> {

	List<FriendMessage> friendMessages(String realSchema, String userId);

}
