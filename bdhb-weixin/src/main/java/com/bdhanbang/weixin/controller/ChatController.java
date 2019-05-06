package com.bdhanbang.weixin.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.base.common.ApiResult;
import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.message.CommonMessage;
import com.bdhanbang.weixin.common.AppCommon;
import com.bdhanbang.weixin.entity.Friend;
import com.bdhanbang.weixin.entity.FriendMessage;
import com.bdhanbang.weixin.jooq.tables.QFriend;
import com.bdhanbang.weixin.service.FriendMessageService;
import com.bdhanbang.weixin.service.FriendService;
import com.bdhanbang.weixin.util.Pinyin;

@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private FriendService friendService;

	@Autowired
	private FriendMessageService friendMessageService;

	@RequestMapping(value = "/friends/{userId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<List<Friend>> getUserFriends(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@PathVariable("userId") String userId) {

		String realSchema = tanentId + AppCommon.scheam;
		ApiResult<List<Friend>> apiResult = new ApiResult<>();

		Query query = new Query();

		query.add(new Query("userId", userId));

		List<Friend> friendList = friendService.queryList(realSchema, QFriend.class, Friend.class, query.getQuerys());

		friendList.forEach(x -> {
			x.setUserId(Pinyin.getPinYinHeadChar(x.getFriendName()));
			if (!Objects.isNull(x.getUserId())) {
				if (x.getUserId().length() > 0) {
					x.setUserId(x.getUserId().substring(0, 1));
				}
			}
		});

		apiResult.setData(friendList);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/messages/{userId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<List<FriendMessage>> getMessages(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@PathVariable("userId") String userId) {

		String realSchema = tanentId + AppCommon.scheam;
		ApiResult<List<FriendMessage>> apiResult = new ApiResult<>();

		Query query = new Query();

		query.add(new Query("userId", userId));

		List<FriendMessage> friendMessages = friendMessageService.friendMessages(realSchema, userId);

		apiResult.setData(friendMessages);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}
}
