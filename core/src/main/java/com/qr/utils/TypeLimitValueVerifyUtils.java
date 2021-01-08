package com.qr.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *  特定类型值校验
 * @Author wd
 * @Date 13:23 2020/9/11
 **/
@Slf4j
public class TypeLimitValueVerifyUtils {

	/**
	 *  自定义数据(证件类型等)校验(数据类型校验) value为验证数据 params为验证数据类
	 * @Author wd
	 * @Date 14:08 2020/9/11
	 **/
	public void verify(Object value,Object params){
		List<String> strings = JSON.parseArray(String.valueOf(params), String.class);
		if (!strings.contains(value)) {
			throw new IllegalArgumentException("检验失败");
		}
	}


	public static Boolean excute(Object value,Object param1) {
		Class<TypeLimitValueVerifyUtils> customizeVerifyUtilsClass = TypeLimitValueVerifyUtils.class;
		Object obj = null;

		try {
			obj = customizeVerifyUtilsClass.getDeclaredConstructor().newInstance();
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			log.error ("反射构造异常:{}",e);
			return false;
		}

		Method method = null;
		try {
			method = customizeVerifyUtilsClass.getMethod("verify", Object.class,Object.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			log.error("反射获取方法异常:{}",e);
			return false;
		}

		try {
			method.invoke(obj, value , param1);
		} catch (Exception e) {
//			log.error ("{}---> 自定义 校验失败 --->校验值为:{}",fieldName,value)
			return false;
		}
//		log.info ("{}---> 自定义 校验成功 --->校验值为:{}",fieldName,value)
		return true;
	}

}
