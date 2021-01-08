package com.qr.enums;

import org.checkerframework.checker.units.qual.A;

/**
 *  基础校验类型枚举类
 * @Author wd
 * @Description //TODO
 * @Date 14:23 2020/9/10
 **/
public enum RegularVerifyEnum {

	/**
	 * 正则枚举
	 */
	EMAIL("20000001","^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"),
	NAME("20000002","^(?:[\u4e00-\uFAD9]+)(?:·[\u4e00-\uFAD9]+)*$|^[a-zA-Z]+\\s?[\\.·\\-()a-zA-Z]*[a-zA-Z]+$"),
	IDCARD("20000003","(^\\\\d{18}$)|(^\\\\d{15}$)"),
	POSTALCODE("20000004","[1-9]\\d{5}"),
	MOBILE("20000005","(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$");

	private final String code;

	private final String value;

	RegularVerifyEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}

	public static String getName(String value) {
		for (RegularVerifyEnum ele : values()) {
			if(ele.getValue().equals(value)) {
				return ele.name();
			}
		}
		return null;
	}

	public static String getValue(String code) {
		for (RegularVerifyEnum ele : values()) {
			if(ele.getCode().equals(code)) {
				return ele.getValue();
			}
		}
		return null;
	}

	public static RegularVerifyEnum getByValue(String value){
		for(RegularVerifyEnum basicVerifyEnum : values()){
			if (basicVerifyEnum.getValue().equals(value)) {
				return basicVerifyEnum;
			}
		}
		return null;
	}
}
