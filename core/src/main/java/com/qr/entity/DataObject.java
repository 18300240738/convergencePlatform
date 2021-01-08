package com.qr.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "数据对象名称")
public class DataObject {

	@ApiModelProperty(value = "数据对象名称")
	private String dataName;

	@ApiModelProperty(value = "数据属性 (key属性 vaule值)")
	private Map<String,Object> data;

}
