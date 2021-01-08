package com.qr.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageParams {

	@ApiModelProperty(value = "页码")
	private int page = 1 ;

	@ApiModelProperty(value = "页数")
	private int limit = 10;

	@ApiModelProperty(value = "排序字段")
	private String sidx;

	@ApiModelProperty(value = "排序方式(DESC AES)")
	private String order;
}
