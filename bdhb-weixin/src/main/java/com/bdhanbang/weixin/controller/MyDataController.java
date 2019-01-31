package com.bdhanbang.weixin.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.bdhanbang.weixin.common.AppCommon;
import com.bdhanbang.weixin.entity.MyData;
import com.bdhanbang.weixin.jooq.tables.QMyData;
import com.bdhanbang.weixin.service.MyDataService;

@RestController
@RequestMapping("/my/data")
public class MyDataController {

	@Autowired
	private MyDataService myDataService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<MyData> insert(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@Valid @RequestBody MyData myData) {
		String realSchema = tanentId + AppCommon.scheam;

		ApiResult<MyData> apiResult = new ApiResult<>();

		myData.setId(UUID.randomUUID());// 设置系统的UUID

		myDataService.insertEntity(realSchema, QMyData.class, myData);

		apiResult.setData(myData);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<MyData> update(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId, @RequestBody MyData myData) {

		String realSchema = tanentId + AppCommon.scheam;
		ApiResult<MyData> apiResult = new ApiResult<>();

		myDataService.updateEntity(realSchema, QMyData.class, myData);

		apiResult.setData(myData);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId, @PathVariable("id") String id) {
		String realSchema = tanentId + AppCommon.scheam;
		myDataService.deleteEntity(realSchema, QMyData.class, id);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<MyData> getEntity(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@PathVariable("id") String id) {

		String realSchema = tanentId + AppCommon.scheam;
		ApiResult<MyData> apiResult = new ApiResult<>();

		MyData myData = myDataService.getEntity(realSchema, QMyData.class, MyData.class, id);

		apiResult.setData(myData);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<MyData>> query(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@RequestParam("queryPage") QueryPage queryPage) {
		String realSchema = tanentId + AppCommon.scheam;

		ApiResult<QueryResults<MyData>> apiResult = new ApiResult<>();

		QueryResults<MyData> queryResults = myDataService.queryPage(realSchema, QMyData.class, MyData.class, queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
