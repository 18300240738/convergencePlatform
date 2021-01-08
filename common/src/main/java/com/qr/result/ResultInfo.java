package com.qr.result;

import com.qr.domain.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  返回数据
 * @Author wd
 * @Date 15:53 2019/7/31
 **/
@Data
@ApiModel(description= "返回响应数据")
public class ResultInfo<T> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("响应码")
	private Long code;

	@ApiModelProperty("响应信息")
	private String msg;

	@ApiModelProperty("请求id")
	private String requestId;

	@ApiModelProperty("数据")
	private T data;

	/**
	 *  成功
	 * @Author wd
	 * @Date 11:15 2020/8/31
	 **/
	public static <T> ResultInfo<T> setTrueObject(String requestId){
		ResultInfo r = new ResultInfo();
		r.setCode(ResultCode.SUCCESS.getCode());
		r.setMsg(ResultCode.SUCCESS.getMessage());
		r.setData(ResultCode.SUCCESS.getMessage());
		r.setRequestId(requestId);
		return r;
	}

	/**
	 * 成功
	 * @param obj
	 * @return
	 */
	public static ResultInfo setTrueObject(String requestId, Object obj){
		ResultInfo r = new ResultInfo();
		r.setCode(ResultCode.SUCCESS.getCode());
		r.setMsg(ResultCode.SUCCESS.getMessage());
		r.setRequestId(requestId);
		r.setData(obj);
		return r;
	}

	/**
	 * 失败
	 * @return
	 */
	public static <T> ResultInfo<T> setFalse(String requestId){
		ResultInfo r = new ResultInfo();
		r.setCode(ResultCode.FAILED.getCode());
		r.setMsg(ResultCode.FAILED.getMessage());
		r.setData(ResultCode.FAILED.getMessage());
		r.setRequestId(requestId);
		return r;
	}

	/**
	 * 失败
	 * @param obj
	 * @return
	 */
	public static ResultInfo setFalse(String requestId, Object obj){
		ResultInfo r = new ResultInfo();
		r.setCode(ResultCode.FAILED.getCode());
		r.setMsg(ResultCode.FAILED.getMessage());
		r.setRequestId(requestId);
		r.setData(obj);
		return r;
	}

	/**
	 *  返回结果
	 * @Author wd
	 * @Date 16:20 2019/7/31
	 **/
	public static <T> ResultInfo<T> setR(Long code, String msg, String requestId, T obj){
		ResultInfo r = new ResultInfo();
		r.setCode(code);
		r.setMsg(msg);
		r.setRequestId(requestId);
		r.setData(obj);
		return r;
	}
}
