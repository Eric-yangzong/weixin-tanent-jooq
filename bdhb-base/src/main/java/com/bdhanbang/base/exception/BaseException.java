package com.bdhanbang.base.exception;

/**
 * @ClassName: BaseException
 * @Description: 基础错误类
 * @author yangxz
 * @date 2018年7月21日 上午11:35:51
 * 
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @Fields status : 状态码
	 */
	String status = "";

	/**
	 * @Fields error : div error
	 */
	String error = "";

	public BaseException(Exception e, String status, String error) {
		super(e);
		this.status = status;
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
