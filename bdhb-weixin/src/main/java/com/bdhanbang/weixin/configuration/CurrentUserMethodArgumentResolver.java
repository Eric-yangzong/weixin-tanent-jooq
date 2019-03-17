package com.bdhanbang.weixin.configuration;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bdhanbang.weixin.common.WeXinOkapi;
import com.bdhanbang.weixin.entity.TWeXinOkapi;

/**
 * @ClassName: CurrentUserMethodArgumentResolver
 * @Description: 获取用户信息
 * @author yangxz
 * @date 2018年9月6日 下午3:21:42
 * 
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(WeXinOkapi.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		TWeXinOkapi weXinOkapi = (TWeXinOkapi) webRequest.getAttribute("weXinOkapi", RequestAttributes.SCOPE_REQUEST);
		return weXinOkapi;
	}
}
