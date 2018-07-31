package com.bdhanbang.base.common;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: ApiResult
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yangxz
 * @date 2018年7月21日 上午11:26:43
 * 
 * @param <T>
 */
public class ApiResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @Fields isError : 是否为错误消息
	 */
	boolean isError = false;

	/**
	 * @Fields error : 错误消息 e.g. "Internal Server Error"
	 */
	String error;

	/**
	 * @Fields message : 详细说明
	 */
	String message = "";

	/**
	 * @Fields path : 访问路径 e.g. /log/login/userlib
	 */
	String path;

	/**
	 * @Fields status : 状态码 e.g. 500
	 */
	String status = "200";

	/**
	 * @Fields timestamp : 时间戳 e.g. 2018-08-01 12:06:22.1
	 */
	LocalDateTime timestamp = LocalDateTime.now();

	/**
	 * @Fields data : 数据主体
	 */
	T data;

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
