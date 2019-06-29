package bdhb.usershiro.configuration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.exception.AuthenticationException;
import com.bdhanbang.base.util.query.Relation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generator.tables.SysUser;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.service.SysUserService;
import bdhb.usershiro.util.JwtUtils;

/**
 * @ClassName: AuthInterceptor
 * @Description: 获取用户信息
 * @author yangxz
 * @date 2018年9月6日 下午1:49:24
 * 
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	SysUserService sysUserService;

	final Base64.Decoder decoder = Base64.getDecoder();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		String token = request.getHeader(AppCommon.TOKEN);

		String tenantId = request.getHeader(AppCommon.TENANT_ID);

		JwtUtils.verifyToken(token);// 此处做鉴权

		SysUserEntity sysUserEntity = this.getCurrentUser(tenantId, token);// 得到用户信息

		request.setAttribute("currentUser", sysUserEntity);

		return true;
	}

	private SysUserEntity getCurrentUser(String tenantId, String token) {

		String schema = String.format("%s%s", tenantId, AppCommon.scheam);

		String[] tokens = token.split("\\.");
		String payload = tokens[1];

		SysUserEntity sysUserEntity = new SysUserEntity();

		try {
			String json = new String(decoder.decode(payload), "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(json);

			String userName = jsonNode.path(AppCommon.PAYLOAD_NAME).asText();

			Query query = new Query();

			query.add("userName", userName, Relation.or);
			query.add("openId", userName);

			List<SysUserEntity> sysUserEntitys = sysUserService.queryList(schema, SysUser.class, SysUserEntity.class,
					query.getQuerys());

			if (Objects.isNull(sysUserEntitys) || sysUserEntitys.size() == 0) {
				throw new AuthenticationException(String.format("【%s】存在问题", userName));
			}

			sysUserEntity = sysUserEntitys.get(0);

		} catch (UnsupportedEncodingException e) {
			throw new AuthenticationException("token编码问题", e);
		} catch (IOException e) {
			throw new AuthenticationException("payload存在问题", e);
		}

		return sysUserEntity;

	}

	private Boolean httpCoressDomain = true;

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
		corsConfiguration.addAllowedHeader("*"); // 2允许任何头
		corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		if (httpCoressDomain) {
			source.registerCorsConfiguration("/**", buildConfig()); // 4
		}
		return new CorsFilter(source);
	}

}
