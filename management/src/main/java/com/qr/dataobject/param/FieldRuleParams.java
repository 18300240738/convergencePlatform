package com.qr.dataobject.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 字段校验规则参数
 * @Author wd
 * @since 10:53 2020/9/25
 **/
@Data
@EqualsAndHashCode
@ApiModel(description="字段校验规则参数")
public class FieldRuleParams {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "规则code",required = true)
	@NotNull
	private String ruleCode;

	@ApiModelProperty(value = "规则名称",required = true)
	@NotNull
	private String ruleName;

	@ApiModelProperty(value = "规则关键字")
	private String ruleKey;

	@ApiModelProperty(value = "规则校验位置(1-前置 2-后置)",required = true)
	private Integer position;

}
