package bdhb.weixin_pay.entity;

/**
 * @ClassName: AppMchKey
 * @Description: 支付相关信息
 * @author yangxz
 * @date 2019年3月17日 下午3:49:36
 * 
 */
public class AppMchKey {

	public String appId = "";
	public String mchId = "";
	public String appKey = "";

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

}
