package com.qr.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 数据对象属性表
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@Data
@ApiModel(value="DataFields对象", description="数据对象属性表")
public class DataFields {

    private static final long serialVersionUID = 1L;

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
    private Integer isNotNull;

    @ApiModelProperty(value = "主键设置(0-否 1-是)")
    private Integer isKey;


}
