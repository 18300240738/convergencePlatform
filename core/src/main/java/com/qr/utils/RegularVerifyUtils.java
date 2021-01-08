package com.qr.utils;

import com.qr.enums.RegularVerifyEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 *  正则校验类型
 * @Author wd
 * @Description //TODO
 * @Date 13:23 2020/9/11
 **/
@Slf4j
public class RegularVerifyUtils {

	public void verify(Object value,Object regex){
		boolean b = Pattern.matches(String.valueOf(regex), String.valueOf(value));
		if (!b) {
			throw new IllegalArgumentException("检验失败");
		}
	}

	public static Boolean excute(Object value,String ruleCode) {
		Class<RegularVerifyUtils> regularVerifyUtilsClass = RegularVerifyUtils.class;
		Object obj = null;
		try {
			obj = regularVerifyUtilsClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			log.error ("反射构造异常:{}",e);
			return false;
		}

		Method method = null;
		try {
			method = regularVerifyUtilsClass.getMethod("verify", Object.class,Object.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			log.error("反射获取方法异常:{}",e);
		}

		try {
			//获取code对应的正则式
			String regex = RegularVerifyEnum.getValue(ruleCode);
			Object resultValue = method.invoke(obj, value , regex);
		} catch (Exception e) {
//			log.error ("{}--->正则 校验失败--->校验值为:{}",fieldName,value);
			return false;
		}
//		log.info ("{}--->正则 校验成功--->校验值为:{}",fieldName,value);
		return true;
	}
}
