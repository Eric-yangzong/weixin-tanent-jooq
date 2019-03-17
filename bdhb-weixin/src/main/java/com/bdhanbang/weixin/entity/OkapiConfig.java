package com.bdhanbang.weixin.entity;

import java.io.Serializable;

/**
 * @ClassName: OkapiConfig
 * @Description: 根据租户设置okapi
 * @author yangxz
 * @date 2018年8月4日 上午10:45:57
 * 
 */
public class OkapiConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	public class Login implements Serializable {

		private static final long serialVersionUID = 1L;

		// 用户名
		String username;

		// 密码
		String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

	public Login getLogin() {
		return new Login();
	}

	// 地址信息
	private String host = "";

	// 文件访问接口
	private String file = "";

	// 商户号
	private String mchId = "";

	// 支付密钥
	private String appKey = "";

	// ip地址
	private String ip = "";

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
