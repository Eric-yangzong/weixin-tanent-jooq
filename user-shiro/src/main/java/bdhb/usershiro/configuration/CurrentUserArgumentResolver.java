package bdhb.usershiro.configuration;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.CurrentUser;

/**
 * @ClassName: CurrentUserArgumentResolver
 * @Description: 获取用户信息
 * @author yangxz
 * @date 2018年9月6日 下午3:21:42
 * 
 */
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		SysUserEntity sysUserEntity = (SysUserEntity) webRequest.getAttribute("currentUser",
				RequestAttributes.SCOPE_REQUEST);
		return sysUserEntity;
	}
}
