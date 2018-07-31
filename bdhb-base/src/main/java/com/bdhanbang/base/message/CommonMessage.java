package com.bdhanbang.base.message;

/**
 * @ClassName: CommonMessage
 * @Description: 公用返回信息
 * @author yangxz
 * @date 2018年7月21日 下午3:33:51
 * 
 */
public enum CommonMessage {

	SUCCESS("200", "请求成功"), CREATE("200", "保存成功"), UPDATE("200", "更新成功"), DELETE("200", "删除成功");

	// 成员变量
	private String status;
	private String message;

	private CommonMessage(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
