package com.bdhanbang.weixin.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * @ClassName: WeXinOkapi
 * @Description: 微信和okapi相关联
 * @author yangxz
 * @date 2018年8月4日 上午9:46:42
 * 
 */
public class TWeXinOkapi implements Serializable {

	private static final long serialVersionUID = 241028554;

	// ID
	private UUID id;

	// 租客ID
	private String tanentId;

	// 微信小程序 App ID
	private String appId;

	// 微信小程序 App Secret
	private String appSecret;

	// 租户下的用户用
	private String userName;

	// 租户下的密码
	private String password;

	// 记事本
	private String note;

	// 用于扩展
	private OkapiConfig jsonb;

	public TWeXinOkapi() {
	}

	public TWeXinOkapi(TWeXinOkapi value) {
		this.id = value.id;
		this.tanentId = value.tanentId;
		this.appId = value.appId;
		this.appSecret = value.appSecret;
		this.userName = value.userName;
		this.password = value.password;
		this.note = value.note;
		this.jsonb = value.jsonb;
	}

	public TWeXinOkapi(UUID id, String tanentId, String appId, String appSecret, String userName, String password,
			String note, OkapiConfig jsonb) {
		this.id = id;
		this.tanentId = tanentId;
		this.appId = appId;
		this.appSecret = appSecret;
		this.userName = userName;
		this.password = password;
		this.note = note;
		this.jsonb = jsonb;
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTanentId() {
		return this.tanentId;
	}

	public void setTanentId(String tanentId) {
		this.tanentId = tanentId;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public OkapiConfig getJsonb() {
		return this.jsonb;
	}

	public void setJsonb(OkapiConfig jsonb) {
		this.jsonb = jsonb;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("TWeXinOkapi (");

		sb.append(id);
		sb.append(", ").append(tanentId);
		sb.append(", ").append(appId);
		sb.append(", ").append(appSecret);
		sb.append(", ").append(userName);
		sb.append(", ").append(password);
		sb.append(", ").append(note);
		sb.append(", ").append(jsonb);

		sb.append(")");
		return sb.toString();
	}
}
