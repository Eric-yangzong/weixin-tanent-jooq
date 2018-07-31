package com.bdhanbang.weixin.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.base.common.ApiResult;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryResults;
import com.bdhanbang.base.message.CommonMessage;
import com.bdhanbang.weixin.entity.TMyLogin;
import com.bdhanbang.weixin.jooq.tables.QMyLogin;
import com.bdhanbang.weixin.service.MyLoginService;

@RestController
@RequestMapping("/my/login")
public class MyLoginController {

	@Autowired
	private MyLoginService myLoginService;

	@RequestMapping(value = "/{schema}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<TMyLogin> insert(@PathVariable String schema, @Valid @RequestBody TMyLogin logLogin) {

		ApiResult<TMyLogin> apiResult = new ApiResult<>();

		logLogin.setId(UUID.randomUUID());// 设置系统的UUID
		logLogin.setLoginTime(LocalDateTime.now()); // 设置系统时间

		myLoginService.insertEntity(schema, QMyLogin.class, logLogin);

		apiResult.setData(logLogin);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{schema}", method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<TMyLogin> update(@PathVariable String schema, @RequestBody TMyLogin logLogin) {

		ApiResult<TMyLogin> apiResult = new ApiResult<>();

		logLogin.setLoginTime(LocalDateTime.now());
		myLoginService.updateEntity(schema, QMyLogin.class, logLogin);

		apiResult.setData(logLogin);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{schema}/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String schema, @PathVariable("id") String id) {

		myLoginService.deleteEntity(schema, QMyLogin.class, id);

	}

	@RequestMapping(value = "/{schema}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<TMyLogin>> query(@PathVariable String schema,
			@RequestParam("queryPage") QueryPage queryPage) {

		ApiResult<QueryResults<TMyLogin>> apiResult = new ApiResult<>();

		QueryResults<TMyLogin> queryResults = myLoginService.queryPage(schema, QMyLogin.class, TMyLogin.class,
				queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
