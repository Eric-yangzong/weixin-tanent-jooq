package com.bdhanbang.weixin.controller;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.weixin.common.CommonUtil;
import com.bdhanbang.weixin.common.Constant;
import com.bdhanbang.weixin.common.TimeUtils;
import com.bdhanbang.weixin.entity.PayInfo;
import com.bdhanbang.weixin.util.HttpUtil;
import com.bdhanbang.weixin.util.StringUtils;

@RestController
@RequestMapping("/weapp/pay")
public class WeXinPayController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		WeXinPayController w = new WeXinPayController();

		String randomNonceStr = CommonUtil.getRandomOrderId();

		System.out.println(w.unifiedOrder("oXuNZ5E3xXW8Ca3W9uXrYWE-Z23A", "183.198.200.132", randomNonceStr));

	}

	/**
	 * 调用统一下单接口
	 * 
	 * @param openId
	 */
	public String unifiedOrder(String openId, String clientIP, String randomNonceStr) {

		try {

			String url = Constant.URL_UNIFIED_ORDER;

			PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr);
			String md5 = getSign(payInfo);
			payInfo.setSign(md5);

			log.error("md5 value: " + md5);

			String xml = CommonUtil.payInfoToXML(payInfo);
			xml = xml.replace("__", "_").replace("<![CDATA[1]]>", "1");

			String str = HttpUtil.httpsRequest(url, "POST", xml);

			log.error("unifiedOrder request return body: \n" + str.toString());
			Map<String, String> result = CommonUtil.parseXml(str.toString());

			String return_code = result.get("return_code");
			if (StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {

				String return_msg = result.get("return_msg");
				if (StringUtils.isNotBlank(return_msg) && !return_msg.equals("OK")) {
					log.error("统一下单错误！");
					return "";
				}

				String prepay_Id = result.get("prepay_id");
				return prepay_Id;

			} else {
				return "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 对订单信息签名
	 * 
	 * @param payInfo
	 */
	private String getSign(PayInfo payInfo) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("appid=" + payInfo.getAppid()).append("&attach=" + payInfo.getAttach())
				.append("&body=" + payInfo.getBody()).append("&device_info=" + payInfo.getDevice_info())
				.append("&limit_pay=" + payInfo.getLimit_pay()).append("&mch_id=" + payInfo.getMch_id())
				.append("&nonce_str=" + payInfo.getNonce_str()).append("&notify_url=" + payInfo.getNotify_url())
				.append("&openid=" + payInfo.getOpenid()).append("&out_trade_no=" + payInfo.getOut_trade_no())
				.append("&sign_type=" + payInfo.getSign_type())
				.append("&spbill_create_ip=" + payInfo.getSpbill_create_ip())
				.append("&time_expire=" + payInfo.getTime_expire()).append("&time_start=" + payInfo.getTime_start())
				.append("&total_fee=" + payInfo.getTotal_fee()).append("&trade_type=" + payInfo.getTrade_type())
				.append("&key=" + Constant.APP_KEY);

		log.error("排序后的拼接参数：" + sb.toString());

		return CommonUtil.getMD5(sb.toString().trim()).toUpperCase();
	}

	/**
	 * 生成订单信息
	 * 
	 * @param openId
	 * @param clientIP
	 * @param randomNonceStr
	 */
	private PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr) {

		Date date = new Date();
		String timeStart = TimeUtils.getFormatTime(date, Constant.TIME_FORMAT);
		String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, Constant.TIME_EXPIRE), Constant.TIME_FORMAT);

		String randomOrderId = CommonUtil.getRandomOrderId();

		PayInfo payInfo = new PayInfo();

		payInfo.setAppid(Constant.APP_ID);
		payInfo.setMch_id(Constant.MCH_ID);
		payInfo.setDevice_info("WEB");
		payInfo.setNonce_str(randomNonceStr);
		payInfo.setSign_type("MD5"); // 默认即为MD5
		payInfo.setBody("JSAPI-支付测试");
		payInfo.setAttach("支付测试luluteam");
		payInfo.setOut_trade_no(randomOrderId);
		payInfo.setTotal_fee(1);
		payInfo.setSpbill_create_ip(clientIP);
		payInfo.setTime_start(timeStart);
		payInfo.setTime_expire(timeExpire);
		payInfo.setNotify_url(Constant.URL_NOTIFY);
		payInfo.setTrade_type("JSAPI");
		payInfo.setLimit_pay("no_credit");
		payInfo.setOpenid(openId);

		return payInfo;
	}

}
