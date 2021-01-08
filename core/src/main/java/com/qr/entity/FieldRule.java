package com.qr.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 字段校验规则中间表
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@Data
@ApiModel(value="FieldRule对象", description="字段校验规则中间表")
public class FieldRule {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据对象code",required = true)
    @NotNull
    private String dataCode;

    @ApiModelProperty(value = "数据对象名称",required = true)
    @NotNull
    private String dataName;

    @ApiModelProperty(value = "字段code",required = true)
    @NotNull
    private String fieldCode;

    @ApiModelProperty(value = "字段名称",required = true)
    @NotNull
    private String filedName;

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

    @ApiModelProperty(value = "规则校验位置(1-前置 2-后置)",required = true)
    private Integer position;


}
