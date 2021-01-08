package com.qr.enums;

/**
 *  校验规则类
 * @Author wd
 * @Description //TODO
 * @Date 14:29 2020/9/8
 **/
public enum RuleTypeEnum {

	/**
	 * 规则方式类
	 */
	BASIC(1), REGULAR(2),TYPELIMITVALUE(3),CUSTOMIZE(4);


	private final int value;

	RuleTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String getName(int value) {
		for (RuleTypeEnum ele : values()) {
			if(ele.getValue() == value) {
				return ele.name();
			}
		}
		return null;
	}

	public static RuleTypeEnum getByValue(Integer value){
		for(RuleTypeEnum dataSourceTypeEnum : values()){
			if (dataSourceTypeEnum.getValue() == value) {
				return dataSourceTypeEnum;
			}
		}
		return null;
	}
}
