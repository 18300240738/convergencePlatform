package com.qr.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *  基本校验类型
 * @Author wd
 * @Date 13:23 2020/9/11
 **/
@Slf4j
public class BasicVerifyUtils {

	public void AssertFalse(Object value, Object param1 ,Object param2){
		Assert.isTrue((Boolean) value,"校验失败");
		throw new IllegalArgumentException("检验失败");
	}

	public void AssertTrue(Object value, Object param1 ,Object param2){
		Assert.isTrue((Boolean) value,"校验失败");
	}

	public void DecimalMax(Object value, Object param1 ,Object param2){
		if (BigDecimal.valueOf((Double) value).compareTo(BigDecimal.valueOf((Double) param1)) >= 0) {
			throw new IllegalArgumentException("检验失败");
		}
	}

	public void DecimalMin(Object value, Object param1 ,Object param2){
		if (BigDecimal.valueOf((Double) value).compareTo(BigDecimal.valueOf((Double) param1)) <= 0) {
			throw new IllegalArgumentException("检验失败");
		}
	}

	/**
	 * 	验证 number 和 string 的构成是否合法
	 */
	public void Digits(Object value, Object param1 ,Object param2){
		String[] split = String.valueOf(value).split(".");
		if (split[0].length() > (int)param1) {
			throw new IllegalArgumentException("检验失败");
		}
		if (split.length == 2 && split[1].length() > (int) param2){
			throw new IllegalArgumentException("检验失败");
		}
	}

	/**
	 *  被注释的元素必须是一个将来的日期
	 * @Author wd
	 * @since 11:37 2020/9/25
	 **/
	public void Future(Object value, Object param1 ,Object param2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(String.valueOf(value), formatter);
		if (dateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("检验失败");
		}
	}

	/**
	 *  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
	 */
	public void Max(Object value, Object param1 ,Object param2){
		if (BigDecimal.valueOf((Double) value).compareTo(BigDecimal.valueOf((Double) param1)) >= 0) {
			throw new IllegalArgumentException("检验失败");
		}
	}

	/**
	 * 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
	 * @param value
	 * @param param1
	 * @param param2
	 */
	public void Min(Object value, Object param1 ,Object param2){
		if (BigDecimal.valueOf((Double) value).compareTo(BigDecimal.valueOf((Double) param1)) <= 0) {
			throw new IllegalArgumentException("检验失败");
		}
	}

	/**
	 * 被注释的元素必须不为 null
	 * @param value
	 * @param param1
	 * @param param2
	 */
	public void NotNull(Object value, Object param1 ,Object param2){
		Assert.notNull(value,"校验失败");
	}

	/**
	 * 被注释的元素必须为 null
	 * @param value
	 * @param param1
	 * @param param2
	 */
	public void Null(Object value, Object param1 ,Object param2){
		Assert.notNull(value,"校验失败");
		throw new IllegalArgumentException("检验失败");
	}

	/**
	 * 被注释的元素必须是一个过去的日期
	 * @param value
	 * @param param1
	 * @param param2
	 */
	public void Past(Object value, Object param1 ,Object param2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(String.valueOf(value), formatter);
		if (dateTime.isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("检验失败");
		}
	}

	/**
	 * 被注释的元素的大小必须在指定的范围内
	 * @param value
	 * @param max
	 * @param min
	 */
	public void Size(Object value, Object max ,Object min){
		List list = (List) value;
		if (list.size() > (int)max || list.size() < (int)min) {
			throw new IllegalArgumentException("检验失败");
		}
	}

//	CreditCardNumber ("00000013"),

	//被注释的字符串的必须非空
//	public void NotEmpty(Object value, Object max ,Object min){
//		if (String.valueOf(value))
//	}
//	Range("00000015"),
//	SafeHtml("00000016"),
//	Length("00000017"),
//	NotBlank("00000018");

	public static Boolean excute(String ruleName,Object value,Object param1,Object param2) {
		Class<BasicVerifyUtils> basicVerifyUtilsClass = BasicVerifyUtils.class;
		Object obj = null;
		try {
			obj = basicVerifyUtilsClass.getDeclaredConstructor().newInstance();
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
			log.error ("反射构造权限异常:{}",e);
			return false;
		}

		Method method = null;
		try {
			method = basicVerifyUtilsClass.getMethod(ruleName, Object.class,Object.class,Object.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			log.error("反射获取方法异常:{}",e);
		}

		try {
			method.invoke(obj, value , param1 ,param2);
		} catch (Exception e) {
//			log.error ("{}--->基本 校验失败--->校验值为:{}",fieldName,value)
			return false;
		}
//		log.info ("{}--->基本 校验成功--->校验值为:{}",fieldName,value)
		return true;
	}
}
