package com.qr.domain;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
 *  结果返回枚举类
 * @Author wd
 * @since 10:46 2020/9/29
 **/
public enum ResultCode implements IErrorCode {

	/**
	 * 操作成功
	 */
	SUCCESS(200, "操作成功"),
	/**
	 * 无token或token已经过期
	 */
	UNAUTHORIZED(401, "无token或token已经过期"),
	/**
	 * 没有相关权限
	 */
	FORBIDDEN(403, "没有相关权限"),
	/**
	 * 参数检验失败
	 */
	VALIDATE_FAILED(501, "参数检验失败"),
	/**
	 * 无效参数
	 */
	INVALID_PARAM(502, "无效参数"),
	/**
	 * 操作失败
	 */
	FAILED(500, "操作失败");

	/** 返回码 */
	private long code;
	/** 返回信息 */
	private String message;

	private ResultCode(long code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public long getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return message;
	}

	public String getMessage() {
		return message;
	}
}
