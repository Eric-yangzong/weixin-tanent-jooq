package bdhb.usershiro.vo;

import java.io.Serializable;

import com.generator.tables.pojos.WxUserInfoEntity;

/**
 * @ClassName: WeXinDataVo
 * @Description: 微信返回数据格式
 * @author yangxz
 * @date 2018年8月4日 上午11:56:41
 * 
 */
public class WeXinDataVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String token = "";

	String skey = "";

	String error = "";

	String message = "";

	WxUserInfoEntity userinfo;

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public WxUserInfoEntity getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(WxUserInfoEntity userinfo) {
		this.userinfo = userinfo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
