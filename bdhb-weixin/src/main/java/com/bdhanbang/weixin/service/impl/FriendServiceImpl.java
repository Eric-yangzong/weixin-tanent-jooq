package com.bdhanbang.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.weixin.entity.Friend;
import com.bdhanbang.weixin.jooq.tables.QFriend;
import com.bdhanbang.weixin.service.FriendService;

@Service("friendService")
public class FriendServiceImpl extends BaseServiceImpl<QFriend, Friend> implements FriendService {

}