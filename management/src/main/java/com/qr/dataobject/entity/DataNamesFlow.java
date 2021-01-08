package com.qr.dataobject.entity;

import com.qr.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author wd
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="DataNamesFlow对象", description="数据流向中间表")
public class DataNamesFlow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据对象code")
    @NotNull
    private String dataCode;

    @ApiModelProperty(value = "数据对象名称")
    @NotNull
    private String dataName;

    @ApiModelProperty(value = "数据流向code")
    @NotNull
    private String flowCode;

    @ApiModelProperty(value = "数据流向名称")
    @NotNull
    private String flowName;


    @ApiModelProperty(value = "数据流向类型(1-sql 2-redis 3-kafka 4-es)")
    @NotNull
    private Integer flowType;

}
