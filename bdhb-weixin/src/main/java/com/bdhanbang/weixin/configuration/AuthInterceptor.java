package com.bdhanbang.weixin.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.weixin.common.AppCommon;
import com.bdhanbang.weixin.entity.TWeXinOkapi;
import com.bdhanbang.weixin.jooq.tables.QWeXinOkapi;
import com.bdhanbang.weixin.service.WeXinOkapiService;

/**
 * @ClassName: AuthInterceptor
 * @Description: 获取用户信息
 * @author yangxz
 * @date 2018年9月6日 下午1:49:24
 * 
 */
//@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private WeXinOkapiService weXinOkapiService;

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

		TWeXinOkapi weXinOkapi = this.getTWeXinOkapi(request.getHeader(AppCommon.X_WX_TENANT));

		String ip = getIpAdrress(request);

		weXinOkapi.getJsonb().setIp(ip);// 设置ip地址

		request.setAttribute("weXinOkapi", weXinOkapi);

		return true;
	}

	/**
	 * @Title: getTWeXinOkapi
	 * @Description: 得到数据库设计信息，有微信和okapi的
	 * @param @param
	 *            tanentId
	 * @param @return
	 *            设定文件
	 * @return TWeXinOkapi 返回类型
	 * @throws:
	 */
	private TWeXinOkapi getTWeXinOkapi(String tanentId) {
		List<Query> queryList = new ArrayList<>();

		Query query = new Query();

		query.setField("tanentId");
		query.setValue(tanentId);

		queryList.add(query);

		List<TWeXinOkapi> listData = weXinOkapiService.queryList(tanentId + AppCommon.scheam, QWeXinOkapi.class,
				TWeXinOkapi.class, queryList);

		if (Objects.isNull(listData) || listData.size() == 0) {
			return new TWeXinOkapi();
		} else {
			return listData.get(0);
		}

	}

	public static String getIpAdrress(HttpServletRequest request) {
		String ip = null;

		// X-Forwarded-For：Squid 服务代理
		String ipAddresses = request.getHeader("X-Forwarded-For");

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// Proxy-Client-IP：apache 服务代理
			ipAddresses = request.getHeader("Proxy-Client-IP");
		}

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// WL-Proxy-Client-IP：weblogic 服务代理
			ipAddresses = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// HTTP_CLIENT_IP：有些代理服务器
			ipAddresses = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// X-Real-IP：nginx服务代理
			ipAddresses = request.getHeader("X-Real-IP");
		}

		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (ipAddresses != null && ipAddresses.length() != 0) {
			ip = ipAddresses.split(",")[0];
		}

		// 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
