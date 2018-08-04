package com.bdhanbang.weixin.entity;

import java.io.Serializable;

public class WeXinResoult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer code = 0;

	WeXinData data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public WeXinData getData() {
		return data;
	}

	public void setData(WeXinData data) {
		this.data = data;
	}

}
