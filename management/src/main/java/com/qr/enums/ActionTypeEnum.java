package com.qr.enums;

/**
 *  数据字段属性编辑类
 * @Author wd
 * @Date 14:29 2020/9/8
 **/
public enum ActionTypeEnum {

	/**
	 * 	数据源枚举类
	 */
	ADD(1), DEL(2),UPD(3);

	private final int value;

	ActionTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String getName(int value) {
		for (ActionTypeEnum ele : values()) {
			if(ele.getValue() == value) {
				return ele.name();
			}
		}
		return null;
	}

	public static ActionTypeEnum getByValue(Integer value){
		for(ActionTypeEnum dataSourceTypeEnum : values()){
			if (dataSourceTypeEnum.getValue() == value) {
				return dataSourceTypeEnum;
			}
		}
		return null;
	}
}
