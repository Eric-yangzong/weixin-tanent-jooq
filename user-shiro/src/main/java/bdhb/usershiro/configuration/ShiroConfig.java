package bdhb.usershiro.configuration;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bdhb.usershiro.filter.AnyRolesAuthorizationFilter;
import bdhb.usershiro.filter.JwtAuthFilter;
import bdhb.usershiro.service.SysUserService;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

	@Bean
	public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager,
			SysUserService userService) throws Exception {
		FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
		filterRegistration.setFilter((Filter) shiroFilter(securityManager, userService).getObject());
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setAsyncSupported(true);
		filterRegistration.setEnabled(true);
		filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);

		return filterRegistration;
	}

	@Bean
	public Authenticator authenticator(SysUserService userService) {
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		authenticator.setRealms(Arrays.asList(jwtShiroRealm(userService), dbShiroRealm(userService)));
		authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
		return authenticator;
	}

	@Bean
	protected SessionStorageEvaluator sessionStorageEvaluator() {
		DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
		sessionStorageEvaluator.setSessionStorageEnabled(false);
		return sessionStorageEvaluator;
	}

	@Bean("dbRealm")
	public Realm dbShiroRealm(SysUserService userService) {
		DbShiroRealm myShiroRealm = new DbShiroRealm(userService);
		return myShiroRealm;
	}

	@Bean("jwtRealm")
	public Realm jwtShiroRealm(SysUserService userService) {
		JWTShiroRealm myShiroRealm = new JWTShiroRealm(userService);
		return myShiroRealm;
	}

	/**
	 * 设置过滤器
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, SysUserService userService) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		Map<String, Filter> filterMap = factoryBean.getFilters();
		filterMap.put("authcToken", createAuthFilter(userService));
		filterMap.put("anyRole", createRolesFilter());
		factoryBean.setFilters(filterMap);
		factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());

		return factoryBean;
	}

	@Bean
	protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		chainDefinition.addPathDefinition("/login", "noSessionCreation,anon");
		chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]");
		chainDefinition.addPathDefinition("/image/**", "anon");
		chainDefinition.addPathDefinition("/static/**", "anon");
		chainDefinition.addPathDefinition("/webjars/**", "anon");
		chainDefinition.addPathDefinition("/swagger-ui.html", "anon");
		chainDefinition.addPathDefinition("/swagger-resources", "anon");
		chainDefinition.addPathDefinition("/configuration/ui", "anon");
		chainDefinition.addPathDefinition("/v2/api-docs", "anon");
		chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authcToken,anyRole[admin,manager]"); // 只允许admin或manager角色的用户访问
		chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken,anyRole[admin,manager]");
		return chainDefinition;
	}

	protected JwtAuthFilter createAuthFilter(SysUserService userService) {
		return new JwtAuthFilter(userService);
	}

	protected AnyRolesAuthorizationFilter createRolesFilter() {
		return new AnyRolesAuthorizationFilter();
	}

}
