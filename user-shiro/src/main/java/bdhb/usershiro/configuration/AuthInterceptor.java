package bdhb.usershiro.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @ClassName: AuthInterceptor
 * @Description: 获取用户信息
 * @author yangxz
 * @date 2018年9月6日 下午1:49:24
 * 
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 如果不是映射到方法直接通过
		if ("/_/tenant".equals(request.getRequestURI())) {
			return true;
		}
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		String requestPath = request.getRequestURI();

		if (requestPath.contains("/v2/api-docs") || requestPath.contains("/swagger")
				|| requestPath.contains("/configuration/ui")) {
			return true;
		}

		if (requestPath.contains("/error")) {
			return true;
		}

		String currentUser = "";

		request.setAttribute("currentUser", currentUser);

		return true;
	}

}
