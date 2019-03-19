package bdhb.weixin_pay.util;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bdhb.weixin_pay.common.CommonUtil;
import bdhb.weixin_pay.common.Constant;
import bdhb.weixin_pay.common.TimeUtils;
import bdhb.weixin_pay.entity.AppMchKey;
import bdhb.weixin_pay.entity.PackageInfo;
import bdhb.weixin_pay.entity.PayInfo;

public class WXPay {

	private static Logger log = LoggerFactory.getLogger("WXPay");

	/**
	 * @Title: unifiedOrder
	 * @param @param
	 *            openId 每个人对应小程序的唯一id
	 * @param @param
	 *            clientIP 客户ip
	 * @param @param
	 *            randomNonceStr 随机码
	 * @param @param
	 *            totalFee 总费用（单位：分）
	 * @param @param
	 *            body 商品-名称
	 * @param @param
	 *            attach 附加信息
	 * @param @return
	 *            设定文件
	 * @return PackageInfo 返回类型
	 * @throws:
	 */
	public static PackageInfo unifiedOrder(String openId, String clientIP, String randomNonceStr, Integer totalFee,
			String body, String attach) {

		AppMchKey appMchKey = new AppMchKey();

		return unifiedOrder(openId, clientIP, randomNonceStr, totalFee, body, attach, appMchKey);

	}

	/**
	 * @Title: unifiedOrder
	 * @param @param
	 *            openId-每个人对应小程序的唯一id
	 * @param @param
	 *            clientIP 客户ip
	 * @param @param
	 *            randomNonceStr 随机码
	 * @param @param
	 *            totalFee 总费用（单位：分）
	 * @param @param
	 *            body 商品-名称
	 * @param @param
	 *            attach 附加信息
	 * @param @return
	 *            设定文件
	 * @return PackageInfo 返回类型
	 * @throws:
	 */
	public static PackageInfo unifiedOrder(String openId, String clientIP, String randomNonceStr, Integer totalFee,
			String body, String attach, AppMchKey appMchKey) {

		try {

			String url = Constant.URL_UNIFIED_ORDER;
			PackageInfo pageInfi = new PackageInfo();

			PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr, totalFee, body, attach, appMchKey);
			payInfo.getSign(appMchKey.getAppKey());// 返回并生成签名

			String xml = CommonUtil.payInfoToXML(payInfo);
			xml = xml.replace("__", "_").replace("<![CDATA[1]]>", "1");

			String str = HttpUtil.httpsRequest(url, "POST", xml);

			log.info("unifiedOrder request return body: \n" + str.toString());
			Map<String, String> result = CommonUtil.parseXml(str.toString());

			String return_code = result.get("return_code");
			if (StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {

				String return_msg = result.get("return_msg");
				if (StringUtils.isNotBlank(return_msg) && !return_msg.equals("OK")) {
					throw new RuntimeException(return_code);
				}

				String prepayId = result.get("prepay_id");
				String nonceStr = result.get("nonce_str");

				pageInfi.setPrepayId(prepayId);
				pageInfi.setNonceStr(nonceStr);

				return pageInfi;

			} else {
				throw new RuntimeException(return_code);
			}

		} catch (Exception e) {
			log.error("获取支付信息错误", e);
			throw new RuntimeException("获取支付信息错误", e);
		}

	}

	/**
	 * 生成订单信息
	 * 
	 * @param openId:用户在商户appid下的唯一标识
	 * @param clientIP:终端IP
	 * @param randomNonceStr:随机字符串
	 * @param totalFee:总费用
	 * @param body:商品简单描述
	 *            e.g. JSAPI-支付测试
	 * @param attach:附加数据
	 *            e.g. 支付测试luluteam
	 * @return PayInfo 返回类型
	 * @throws:
	 */
	public static PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr, Integer totalFee,
			String body, String attach, AppMchKey appMchKey) {

		Date date = new Date();
		String timeStart = TimeUtils.getFormatTime(date, Constant.TIME_FORMAT);
		String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, Constant.TIME_EXPIRE), Constant.TIME_FORMAT);

		String randomOrderId = CommonUtil.getRandomOrderId();

		PayInfo payInfo = new PayInfo();

		payInfo.setAppid("".equals(Constant.APP_ID) ? appMchKey.getAppId() : Constant.APP_ID);// 微信分配的小程序ID：wxd678efh567hg6787（必填）
		payInfo.setMch_id("".equals(Constant.MCH_ID) ? appMchKey.getMchId() : Constant.MCH_ID);// 微信支付分配的商户号：1230000109（必填）
		payInfo.setDevice_info("WEB");// 设备号：自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
		payInfo.setNonce_str(randomNonceStr);// 随机字符串：随机字符串，长度要求在32位以内。（必填）
		payInfo.setSign_type("MD5"); // 签名类型:签名类型，默认为MD5，支持HMAC-SHA256和MD5。（必填）
		payInfo.setBody(body);// 商品描述:商品简单描述，该字段请按照规范传递，具体请见（必填）
		payInfo.setAttach(attach);// 附加数据
		payInfo.setOut_trade_no(randomOrderId);// 商户订单号:商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
		payInfo.setTotal_fee(totalFee);// 标价金额:订单总金额，单位为分
		payInfo.setSpbill_create_ip(clientIP);// 终端IP
		payInfo.setTime_start(timeStart);// 交易起始时间:订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
		payInfo.setTime_expire(timeExpire);// 交易结束时间:订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。其他详见
		payInfo.setNotify_url(Constant.URL_NOTIFY);// 通知地址:异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
		payInfo.setTrade_type("JSAPI");// 交易类型:小程序取值如下：JSAPI，
		payInfo.setLimit_pay("no_credit");// 指定支付方式:上传此参数no_credit--可限制用户不能使用信用卡支付
		payInfo.setOpenid(openId);// 用户标识:trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识

		return payInfo;
	}

}
