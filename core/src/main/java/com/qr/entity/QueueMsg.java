package com.qr.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "数据对象队列数据")
public class QueueMsg {

	@ApiModelProperty(value = "数据对象名称")
	private String dataName;

	@ApiModelProperty(value = "数据对象数据")
	private String data;

	@ApiModelProperty(value = "数据入库时间")
	private String date;

}
