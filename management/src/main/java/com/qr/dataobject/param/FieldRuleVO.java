package com.qr.dataobject.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 *  字段校验规则数据
 * @Author wd
 * @since 10:53 2020/9/25
 **/
@Data
@EqualsAndHashCode
@ApiModel(description="字段校验规则数据")
public class FieldRuleVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "规则code",required = true)
	@NotNull
	private String ruleCode;

	@ApiModelProperty(value = "规则名称",required = true)
	@NotNull
	private String ruleName;

	@ApiModelProperty(value = "数据校验字段参数1",required = true)
	private String ruleValue1;

	@ApiModelProperty(value = "数据校验字段参数2",required = true)
	private String ruleValue2;
}
