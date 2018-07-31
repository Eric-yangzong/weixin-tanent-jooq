package com.bdhanbang.base.message;

/**
 * @ClassName: ErrorMessage
 * @Description: 存放系统的错误信息
 * @author yangxz
 * @date 2018年7月21日 下午12:53:47
 * 
 */
public enum ErrorMessage {

	DATE_TYPE("2000001", "日期格式不正确"), NUMBER_TYPE("2000002", "数字格式不正确"), FIELD_NOT_EXIST("2000003",
			"字段不存在"), CURD_ERROR("2000004", "增删改操作错误"), UNEXPECTED_VALUE("2000005", "不是期望的值");

	// 成员变量
	private String status;
	private String message;

	private ErrorMessage(String status, String message) {
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
