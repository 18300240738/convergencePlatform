package com.qr.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Bean
	public RequestInterceptor getAccessInterceptor(){
		return new RequestInterceptor();
	}

	/**
	 *  添加拦截器
	 * @Author wd
	 * @Description //TODO
	 * @Date 13:25 2020/8/31
	 **/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//添加登录处理拦截器，拦截所有请求，登录请求除外
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(getAccessInterceptor());
		//排除配置
		interceptorRegistration.excludePathPatterns("/converge/401");
		interceptorRegistration.excludePathPatterns("/converge/501");
		interceptorRegistration.excludePathPatterns("/converge/reload");
		//配置拦截策略
		interceptorRegistration.addPathPatterns("/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//设置允许跨域的路径
		registry.addMapping("/**")
				//设置允许跨域请求的域名
				.allowedOrigins("*")
				.allowedHeaders("*")
//                .allowCredentials(true)//是否允许证书 不再默认开启
				//设置允许的方法
				.allowedMethods("GET", "POST", "PUT", "DELETE");
//                .maxAge(3600);//跨域允许时间
	}
}
