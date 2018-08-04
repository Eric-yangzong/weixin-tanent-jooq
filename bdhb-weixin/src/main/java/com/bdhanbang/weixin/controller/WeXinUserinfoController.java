package com.bdhanbang.weixin.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.base.common.ApiResult;
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

		WeXinUserinfo oldEntity = weXinUserinfoService.selectEntity(realSchema, QWeXinUserinfo.class,
				WeXinUserinfo.class, weXinUserinfo.getId());

		// 只更新呢称
		oldEntity.setNickName(weXinUserinfo.getNickName());

		weXinUserinfoService.updateEntity(realSchema, QWeXinUserinfo.class, oldEntity);

		apiResult.setData(oldEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}
}
