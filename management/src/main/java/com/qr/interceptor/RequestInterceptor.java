package com.qr.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *  生成请求id拦截器
 * @Author wd
 * @Description //TODO
 * @Date 13:16 2020/8/31
 **/
@Configuration
@Slf4j
public class RequestInterceptor implements HandlerInterceptor, WebMvcConfigurer {

	/**
	 *  方法执行前
	 * @Author wd
	 * @Description //TODO
	 * @Date 13:17 2020/8/31
	 **/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestId = UUID.randomUUID().toString().replaceAll("-", "");
		MDC.put("requestId", requestId);

		request.setAttribute("requestId",requestId);
		return true;
	}

	/**
	 *  方法执行后
	 * @Author wd
	 * @Description //TODO
	 * @Date 13:17 2020/8/31
	 **/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	/**
	 *  页面渲染前
	 * @Author wd
	 * @Description //TODO
	 * @Date 13:17 2020/8/31
	 **/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

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
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(new RequestInterceptor());
		//排除配置
//		interceptorRegistration.excludePathPatterns("/sys/login.json")
		//配置拦截策略
		interceptorRegistration.addPathPatterns("/**");
	}
}
