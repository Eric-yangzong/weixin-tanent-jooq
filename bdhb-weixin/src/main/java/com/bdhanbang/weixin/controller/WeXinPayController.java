package com.bdhanbang.weixin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.base.common.ApiResult;
import com.bdhanbang.base.message.CommonMessage;
import com.bdhanbang.weixin.common.AppCommon;
import com.bdhanbang.weixin.common.WeXinOkapi;
import com.bdhanbang.weixin.entity.TWeXinOkapi;

import bdhb.weixin_pay.common.CommonUtil;
import bdhb.weixin_pay.entity.AppMchKey;
import bdhb.weixin_pay.entity.PackageInfo;
import bdhb.weixin_pay.util.WXPay;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/weapp/pay")
public class WeXinPayController {

	public static String notifyMessage = "";

	@RequestMapping(value = "/notify", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<String> getEntity(HttpServletRequest request) {

		// String realSchema = tanentId + AppCommon.scheam;
		ApiResult<String> apiResult = new ApiResult<>();

		if (notifyMessage.length() > 8000) {
			notifyMessage = String.valueOf(request.getContentLength());
		} else {
			notifyMessage = notifyMessage + String.valueOf(request.getContentLength());
		}

		return apiResult;

	}

	@RequestMapping(value = "/{openId}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<PackageInfo> getEntity(@RequestHeader(AppCommon.X_WX_TENANT) String tanentId,
			@PathVariable("openId") String openId, @ApiIgnore @WeXinOkapi TWeXinOkapi weXinOkapi) {

		// String realSchema = tanentId + AppCommon.scheam;
		ApiResult<PackageInfo> apiResult = new ApiResult<>();

		String ip = weXinOkapi.getJsonb().getIp();
		String randomNonceStr = CommonUtil.getRandomOrderId();

		// 可以设置 bdhb.weixin_pay.common.Constant就可以不用此段代码

		AppMchKey appMchKey = new AppMchKey();
		appMchKey.setAppId(weXinOkapi.getAppId());
		appMchKey.setMchId(weXinOkapi.getJsonb().getMchId());
		appMchKey.setAppKey(weXinOkapi.getJsonb().getAppKey());

		// -----------到此结束

		PackageInfo myData = WXPay.unifiedOrder(openId, ip, randomNonceStr, 1, "商品-名称", "附加信息", appMchKey);

		apiResult.setData(myData);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
