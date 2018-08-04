package com.bdhanbang.weixin.entity;

import java.io.Serializable;
import java.util.Date;

public class WeiXinWatermark implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Date timestamp;

	String appid;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
