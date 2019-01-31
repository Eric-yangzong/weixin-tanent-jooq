package com.bdhanbang.weixin.controller;

import java.util.Objects;

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
import com.bdhanbang.base.exception.BusinessException;
import com.bdhanbang.base.message.CommonMessage;
import com.bdhanbang.weixin.common.AppCommon;
import com.bdhanbang.weixin.entity.WeXinUserinfo;
import com.bdhanbang.weixin.jooq.tables.QWeXinUserinfo;
import com.bdhanbang.weixin.service.WeXinUserinfoService;

/**
 * @ClassName: WeXinUserinfoController
 * @Description: 微信用户信息CURD
 * @author yangxz
 * @date 2018年8月4日 下午2:39:44
 * 
 */
@RestController
@RequestMapping("/weapp/userinfo")
public class WeXinUserinfoController {

	@Autowired
	private WeXinUserinfoService weXinUserinfoService;

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	public ApiResult<WeXinUserinfo> update(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@RequestBody WeXinUserinfo weXinUserinfo) {

		ApiResult<WeXinUserinfo> apiResult = new ApiResult<>();
		String realSchema = tanentId + AppCommon.scheam;

		if (Objects.isNull(weXinUserinfo) || Objects.isNull(weXinUserinfo.getId())
				|| Objects.isNull(weXinUserinfo.getNickName())) {

			throw new BusinessException("100001", "数据不能为空");
		}

		WeXinUserinfo oldEntity = weXinUserinfoService.getEntity(realSchema, QWeXinUserinfo.class, WeXinUserinfo.class,
				weXinUserinfo.getId());

		oldEntity.setNickName(weXinUserinfo.getNickName());// 只更新呢称v
		oldEntity.setJsonb(weXinUserinfo.getJsonb());// 更新jsonb

		weXinUserinfoService.updateEntity(realSchema, QWeXinUserinfo.class, oldEntity);

		apiResult.setData(oldEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<WeXinUserinfo> getEntity(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@PathVariable("id") String id) {
		String realSchema = tanentId + AppCommon.scheam;
		ApiResult<WeXinUserinfo> apiResult = new ApiResult<>();

		WeXinUserinfo weXinUserinfo = weXinUserinfoService.getEntity(realSchema, QWeXinUserinfo.class,
				WeXinUserinfo.class, id);

		apiResult.setData(weXinUserinfo);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<WeXinUserinfo>> query(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@RequestParam("queryPage") QueryPage queryPage) {
		String realSchema = tanentId + AppCommon.scheam;
		ApiResult<QueryResults<WeXinUserinfo>> apiResult = new ApiResult<>();

		QueryResults<WeXinUserinfo> queryResults = weXinUserinfoService.queryPage(realSchema, QWeXinUserinfo.class,
				WeXinUserinfo.class, queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}
}
