package com.qr.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 数据对象名称表
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@Data
@ApiModel(value="DataNames对象", description="数据对象名称表")
public class DataNames {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据对象code",required = true)
    @NotNull
    private String dataCode;

    @ApiModelProperty(value = "数据对象名称",required = true)
    @NotNull
    private String dataName;


}
