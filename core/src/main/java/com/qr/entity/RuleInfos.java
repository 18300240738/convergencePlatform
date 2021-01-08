package com.qr.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 校验规则表
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@Data
@ApiModel(value="RuleInfos对象", description="校验规则表")
public class RuleInfos {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规则code")
    private String ruleCode;

    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "校验类型")
    private Integer checkType;

    @ApiModelProperty(value = "规则类型(1-基本校验类型 2-正则式校验类型 3-自定义校验类型)")
    private Integer ruleType;

    @ApiModelProperty(value = "规则关键字")
    private String ruleKey;

    @ApiModelProperty(value = "规则详情")
    private String ruleDetails;

    @ApiModelProperty(value = "自定义校验jar存放路径")
    private String rulePath;


}
