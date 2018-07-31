package com.bdhanbang.base.exception;

/**
 * @ClassName: CurdException
 * @Description: 增删改查操作错误<BR/>
 *               默前台返回 500 Internal Server Error<BR/>
 *               说明：通用错误消息，服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理。没有给出具体错误信息。
 * @author yangxz
 * @date 2018年7月21日 上午11:43:26
 * 
 */
public class CurdException extends BaseException {

	private static final long serialVersionUID = 1L;

	public CurdException(Exception e, String status, String error) {
		super(e, status, error);
	}
}
