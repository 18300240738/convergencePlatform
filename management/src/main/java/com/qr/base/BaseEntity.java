package com.qr.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 *  通用entity
 * @Author wd
 * @Date 16:27 2020/8/28
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEntity {

	@ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty(value = "修改时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;

	@ApiModelProperty(value = "用户状态(0-默认)")
	private Integer status;

	@ApiModelProperty(value = "是否删除(0-否 1-是)")
	@TableLogic
	private Integer isDel;

	@ApiModelProperty(value = "保留字段")
	private String res;

	@ApiModelProperty(value = "保留字段")
	private String res2;

	@ApiModelProperty(value = "保留字段")
	private String res3;
}
