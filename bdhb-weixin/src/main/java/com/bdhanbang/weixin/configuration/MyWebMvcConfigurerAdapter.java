package com.bdhanbang.weixin.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: MyWebMvcConfigurerAdapter
 * @Description: 获取用户信息
 * @author yangxz
 * @date 2018年9月6日 下午3:22:00
 * 
 */
@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {
	// 关键，将拦截器作为bean写入配置中
	@Bean
	public AuthInterceptor myAuthInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册拦截器
		InterceptorRegistration ir = registry.addInterceptor(myAuthInterceptor());
		// 配置拦截的路径
		ir.addPathPatterns("/**");
		// 配置不拦截的路径
		ir.excludePathPatterns("**/swagger-ui.html");
		// 还可以在这里注册其它的拦截器
		// registry.addInterceptor(new OtherInterceptor()).addPathPatterns("/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserMethodArgumentResolver());
	}

	@Bean
	public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
		return new CurrentUserMethodArgumentResolver();
	}
}
