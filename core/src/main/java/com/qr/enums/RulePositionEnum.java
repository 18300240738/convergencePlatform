package com.qr.enums;

/**
 *  校验规则类
 * @Author wd
 * @Description //TODO
 * @Date 14:29 2020/9/8
 **/
public enum RulePositionEnum {

	/**
	 * 校验规则位置类
	 */
	BEFORE(1), AFTER(2);


	private final int value;

	RulePositionEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String getName(int value) {
		for (RulePositionEnum ele : values()) {
			if(ele.getValue() == value) {
				return ele.name();
			}
		}
		return null;
	}

	public static RulePositionEnum getByValue(Integer value){
		for(RulePositionEnum dataSourceTypeEnum : values()){
			if (dataSourceTypeEnum.getValue() == value) {
				return dataSourceTypeEnum;
			}
		}
		return null;
	}
}
