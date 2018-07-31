package com.bdhanbang.weixin.configuration;

import java.lang.reflect.UndeclaredThrowableException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bdhanbang.base.common.ApiResult;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryPageEditor;
import com.bdhanbang.base.exception.BusinessException;
import com.bdhanbang.base.exception.CurdException;
import com.bdhanbang.base.exception.ValidException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 统一错误处理
 * @author yangxz
 * @date 2018年7月21日 下午12:38:47
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	ObjectMapper mapper = new ObjectMapper();

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(QueryPage.class, new QueryPageEditor());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResult<String> validExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException ex)
			throws JsonProcessingException {
		ApiResult<String> errorResult = new ApiResult<>();

		errorResult.setError(ex.getMessage());// 自定义的错误

		errorResult.setStatus(HttpStatus.BAD_REQUEST.toString());// 自定义的错误码
		errorResult.setData(mapper.writeValueAsString(ex));// 向前台返回详细错误信息

		StringBuilder sb = new StringBuilder("[");
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {

			if (error instanceof FieldError) {
				FieldError fError = (FieldError) error;
				sb.append("{\"");
				sb.append(fError.getField());
				sb.append("\":\"");
				sb.append(fError.getDefaultMessage());
				sb.append("\"},");
			} else {
				sb.append("\"");
				sb.append(error.getDefaultMessage());
				sb.append("\"},");
			}

		}

		errorResult.setMessage(sb.toString());// 系统错误提示
		errorResult.setPath(req.getRequestURL().toString());// 请求路径
		errorResult.setTimestamp(LocalDateTime.now());// 时间戳

		log.error(mapper.writeValueAsString(ex)); // 错误记录日志

		return errorResult;
	}

	/**
	 * @Title: validExceptionHandler
	 * @Description: 客户数据提交错误
	 * @param @param
	 *            req
	 * @param @param
	 *            e
	 * @param @return
	 * @param @throws
	 *            Exception 设定文件
	 * @return ApiResult<String> 返回类型
	 * @throws:
	 */
	@ExceptionHandler(value = ValidException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResult<String> validExceptionHandler(HttpServletRequest req, ValidException e)
			throws JsonProcessingException {

		ApiResult<String> errorResult = new ApiResult<>();

		errorResult.setError(e.getError());// 自定义的错误
		errorResult.setMessage(e.getMessage());// 系统错误提示
		errorResult.setStatus(e.getStatus());// 自定义的错误码
		errorResult.setData(mapper.writeValueAsString(e));// 向前台返回详细错误信息
		errorResult.setPath(req.getRequestURL().toString());// 请求路径
		errorResult.setTimestamp(LocalDateTime.now());// 时间戳

		log.error(mapper.writeValueAsString(e)); // 错误记录日志

		return errorResult;
	}

	/**
	 * @Title: curdExceptionHandler
	 * @Description: 增删改未知错误
	 * @param @param
	 *            req
	 * @param @param
	 *            e
	 * @param @return
	 * @param @throws
	 *            Exception 设定文件
	 * @return ApiResult<String> 返回类型
	 * @throws:
	 */
	@ExceptionHandler(value = CurdException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResult<String> curdExceptionHandler(HttpServletRequest req, CurdException e)
			throws JsonProcessingException {

		ApiResult<String> errorResult = new ApiResult<>();

		errorResult.setError(e.getError());// 自定义的错误
		errorResult.setMessage(e.getMessage());// 系统错误提示
		errorResult.setStatus(e.getStatus());// 自定义的错误码
		errorResult.setData(mapper.writeValueAsString(e));// 向前台返回详细错误信息
		errorResult.setPath(req.getRequestURL().toString());// 请求路径
		errorResult.setTimestamp(LocalDateTime.now());// 时间戳

		log.error(mapper.writeValueAsString(e)); // 错误记录日志

		return errorResult;
	}

	/**
	 * @Title: businessExceptionHandler
	 * @Description: 业务验证错误
	 * @param @param
	 *            req
	 * @param @param
	 *            e
	 * @param @return
	 * @param @throws
	 *            Exception 设定文件
	 * @return ApiResult<String> 返回类型
	 * @throws:
	 */
	@ExceptionHandler(value = BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResult<String> businessExceptionHandler(HttpServletRequest req, BusinessException e)
			throws JsonProcessingException {

		ApiResult<String> errorResult = new ApiResult<>();

		errorResult.setError(e.getError());// 自定义的错误
		errorResult.setMessage(e.getMessage());// 系统错误提示
		errorResult.setStatus(e.getStatus());// 自定义的错误码
		errorResult.setData(mapper.writeValueAsString(e));// 向前台返回详细错误信息
		errorResult.setPath(req.getRequestURL().toString());// 请求路径
		errorResult.setTimestamp(LocalDateTime.now());// 时间戳

		log.error(mapper.writeValueAsString(e)); // 错误记录日志

		return errorResult;
	}

	/**
	 * @Title: uncatchExceptionHandler
	 * @Description: 未抓住的错误
	 * @param @param
	 *            req
	 * @param @param
	 *            ex
	 * @param @return
	 * @param @throws
	 *            JsonProcessingException 设定文件
	 * @return ApiResult<String> 返回类型
	 * @throws:
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ApiResult<String> uncatchExceptionHandler(HttpServletRequest req, Exception ex)
			throws JsonProcessingException {

		ApiResult<String> errorResult = new ApiResult<>();

		errorResult.setError(ex.getMessage());// 自定义的错误
		errorResult.setMessage(ex.getMessage());// 系统错误提示
		errorResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());// 自定义的错误码
		errorResult.setData(mapper.writeValueAsString(ex));// 向前台返回详细错误信息
		errorResult.setPath(req.getRequestURL().toString());// 请求路径
		errorResult.setTimestamp(LocalDateTime.now());// 时间戳

		log.error(mapper.writeValueAsString(ex)); // 错误记录日志

		return errorResult;
	}

	/**
	 * @Title: throwableExceptionHandler
	 * @Description: 动态代理异常捕获
	 * @param @param
	 *            req
	 * @param @param
	 *            ex
	 * @param @return
	 * @param @throws
	 *            JsonProcessingException 设定文件
	 * @return ApiResult<String> 返回类型
	 * @throws:
	 */
	@ExceptionHandler(UndeclaredThrowableException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ApiResult<String> throwableExceptionHandler(HttpServletRequest req, UndeclaredThrowableException ex)
			throws JsonProcessingException {
		ApiResult<String> errorResult = new ApiResult<>();

		errorResult.setError(ex.getMessage());// 自定义的错误
		errorResult.setMessage(ex.getMessage());// 系统错误提示
		errorResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());// 自定义的错误码
		errorResult.setData(mapper.writeValueAsString(ex));// 向前台返回详细错误信息
		errorResult.setPath(req.getRequestURL().toString());// 请求路径
		errorResult.setTimestamp(LocalDateTime.now());// 时间戳

		log.error(mapper.writeValueAsString(ex)); // 错误记录日志

		return errorResult;
	}
}
