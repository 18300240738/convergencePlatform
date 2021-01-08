package com.qr.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : wangdong
 * create at:  2020/9/14  15:57
 * @description: 数据入库队列信息
 */
@Data
@ApiModel(value = "保存数据源信息")
public class DataInfoMsg {

    @ApiModelProperty(value = "数据源code")
    private String flowCode;

    @ApiModelProperty(value = "构建语句")
    private String createSql;
}
