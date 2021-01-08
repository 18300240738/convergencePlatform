package com.qr.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 *  获取上下文信息工具类
 * @Author wd
 * @since 16:01 2020/9/25
 **/
@Configuration
public class SpringContextUtil implements ApplicationContextAware {

	/**
	 *  Spring应用上下文环境
	 */
	private static ApplicationContext applicationContext;


	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取对象 这里重写了bean方法，起主要作用
	 */
	public static Object getBean(String beanId) throws BeansException {
		return applicationContext.getBean(beanId);
	}
}
