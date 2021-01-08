package com.qr.dataobject.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  数据对象属性表
 * @Author wd
 * @since 10:53 2020/9/25
 **/
@Data
@EqualsAndHashCode
@ApiModel(description="数据对象属性表")
public class DataFieldsParams {

	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "数据对象code",required = true)
	@NotNull
	private String dataCode;

	@ApiModelProperty(value = "数据对象名称",required = true)
	@NotNull
	private String dataName;

	@ApiModelProperty(value = "数据对象字段code",required = true)
	@NotNull
	private String fieldCode;

	@ApiModelProperty(value = "字段名称",required = true)
	@NotNull
	private String fieldName;

	@ApiModelProperty(value = "字段类型",required = true)
	@NotNull
	private String fieldType;

	@ApiModelProperty(value = "字段长度",required = true)
	@NotNull
	private Integer fieldLength;

	@ApiModelProperty(value = "小数点")
	private Integer fieldDecimals;

	@ApiModelProperty(value = "字段非空(0-否 1-是)")
	@NotNull
	private Integer isNotNull;

	@ApiModelProperty(value = "主键设置(0-否 1-是)")
	@NotNull
	private Integer isKey;

	@ApiModelProperty(value = "操作标示(1-新增 2-删除 3-编辑)")
	private int action;

	@ApiModelProperty(value = "属性校验规则")
	private List<FieldRuleParams> fieldRuleParams;
}
