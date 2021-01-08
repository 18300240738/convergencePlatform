package com.qr.dataobject.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 *  数据对象属性
 * @Author wd
 * @since 10:53 2020/9/25
 **/
@Data
@EqualsAndHashCode
@ApiModel(value = "数据对象属性",description = "数据对象属性")
public class DataFieldVO {

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
	private String isKey;

	@ApiModelProperty(value = "属性校验规则")
	private List<FieldRuleVO> fieldRuleVos;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
