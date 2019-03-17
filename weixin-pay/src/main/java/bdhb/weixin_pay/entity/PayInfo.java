package bdhb.weixin_pay.entity;

import bdhb.weixin_pay.common.Constant;
import bdhb.weixin_pay.util.MD5Utils;

/**
 * @author Eric
 *
 */
public class PayInfo {

	private String appid;// 微信分配的小程序ID：wxd678efh567hg6787（必填）
	private String mch_id;// 微信支付分配的商户号：1230000109（必填）
	private String device_info;// 设备号：自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
	private String nonce_str;// 随机字符串：随机字符串，长度要求在32位以内。（必填）
	private String sign_type;// 签名类型:签名类型，默认为MD5，支持HMAC-SHA256和MD5。（必填）
	private String sign;
	private String body;// 商品描述:商品简单描述，该字段请按照规范传递，具体请见（必填）
	private String attach;// 附加数据
	private String out_trade_no;// 商户订单号:商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
	private Integer total_fee;// 标价金额:订单总金额，单位为分
	private String spbill_create_ip;// 终端IP
	private String time_start;// 交易起始时间:订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
	private String time_expire;// 交易结束时间:订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。其他详见
	private String notify_url;// 通知地址:异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
	private String trade_type;// 交易类型:小程序取值如下：JSAPI，
	private String limit_pay;// 指定支付方式:上传此参数no_credit--可限制用户不能使用信用卡支付
	private String openid;// 用户标识:trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
	private String appKey;// 支付密钥

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * 对订单信息签名
	 * 
	 * @param payInfo
	 */
	public String getSign() {

		StringBuffer buf = new StringBuffer();

		buf.append("appid=" + this.getAppid());
		buf.append("&attach=" + this.getAttach());
		buf.append("&body=" + this.getBody());
		buf.append("&device_info=" + this.getDevice_info());
		buf.append("&limit_pay=" + this.getLimit_pay());
		buf.append("&mch_id=" + this.getMch_id());
		buf.append("&nonce_str=" + this.getNonce_str());
		buf.append("&notify_url=" + this.getNotify_url());
		buf.append("&openid=" + this.getOpenid());
		buf.append("&out_trade_no=" + this.getOut_trade_no());
		buf.append("&sign_type=" + this.getSign_type());
		buf.append("&spbill_create_ip=" + this.getSpbill_create_ip());
		buf.append("&time_expire=" + this.getTime_expire());
		buf.append("&time_start=" + this.getTime_start());
		buf.append("&total_fee=" + this.getTotal_fee());
		buf.append("&trade_type=" + this.getTrade_type());
		buf.append("&key=" + ("".equals(Constant.APP_KEY) ? this.appKey : Constant.APP_KEY));
		this.sign = MD5Utils.getMD5(buf.toString().trim()).toUpperCase();

		return this.sign;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

}
