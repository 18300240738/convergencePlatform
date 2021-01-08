package com.qr.enums;

/**
 *  数据源类型枚举类
 * @Author wd
 * @Description //TODO
 * @Date 14:29 2020/9/8
 **/
public enum DataSourceTypeEnum {

	/**
	 * 	数据源枚举类
	 */
	SQL(1), REDIS(2),KAFKA(3), ES(4);

	private final int value;

	DataSourceTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String getName(int value) {
		for (DataSourceTypeEnum ele : values()) {
			if(ele.getValue() == value) {
				return ele.name();
			}
		}
		return null;
	}

	public static DataSourceTypeEnum getByValue(Integer value){
		for(DataSourceTypeEnum dataSourceTypeEnum : values()){
			if (dataSourceTypeEnum.getValue() == value) {
				return dataSourceTypeEnum;
			}
		}
		return null;
	}
}
