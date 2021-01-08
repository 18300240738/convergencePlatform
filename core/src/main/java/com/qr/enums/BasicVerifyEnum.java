package com.qr.enums;

/**
 *  基础校验类型枚举类
 * @Author wd
 * @Description //TODO
 * @Date 14:23 2020/9/10
 **/
public enum BasicVerifyEnum {

	/**
	 *	基础校验枚举对应code信息
	 */
	AssertFalse("00000001"),
	AssertTrue("00000002"),
	DecimalMax("00000003"),
	DecimalMin("00000004"),
	Digits("00000005"),
	Future("00000006"),
	Max("00000007"),
	Min("00000008"),
	NotNull("00000009"),
	Null("00000010"),
	Past("00000011"),
	Size("00000012"),
	CreditCardNumber ("00000013"),
	NotEmpty("00000014"),
	Range("00000015"),
	SafeHtml("00000016"),
	Length("00000017"),
	NotBlank("00000018");

	private final String value;

	BasicVerifyEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static String getName(String value) {
		for (BasicVerifyEnum ele : values()) {
			if(ele.getValue().equals(value)) {
				return ele.name();
			}
		}
		return null;
	}

	public static BasicVerifyEnum getByValue(String value){
		for(BasicVerifyEnum basicVerifyEnum : values()){
			if (basicVerifyEnum.getValue().equals(value)) {
				return basicVerifyEnum;
			}
		}
		return null;
	}

}
