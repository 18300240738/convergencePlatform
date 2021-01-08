package com.qr.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 数据流向信息表
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@Data
@ApiModel(value="DataFlowInfo对象", description="数据流向信息表")
public class DataFlowInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据流向code")
    private String dataFlowCode;

    @ApiModelProperty(value = "数据流向名称")
    private String dataFlowName;

    @ApiModelProperty(value = "数据流向类型(1-mysql 2-oracle 3-redis 4-kafka 5-es)")
    private Integer dataFlowType;

    @ApiModelProperty(value = "数据流向地址")
    private String databaseAddress;

    @ApiModelProperty(value = "数据流向账户")
    private String databaseUsername;

    @ApiModelProperty(value = "数据流向密码")
    private String databasePassword;

    @ApiModelProperty(value = "数据流向单位")
    private String dataUser;


}
