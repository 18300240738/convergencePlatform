package com.qr.base;

import com.qr.result.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 *  通用controller
 * @Author wd
 * @Date 16:27 2020/8/28
 **/
public abstract class BaseController {

	/**
	 *  获取全局请求id
	 * @Author wd
	 * @Date 10:51 2020/8/31
	 **/
	protected String getRequestId(HttpServletRequest request){
		return String.valueOf(request.getAttribute("requestId"));
	}

	/**
	 *  返回正确结果
	 * @Author wd
	 * @Date 10:53 2020/8/31
	 **/
	protected <T> ResultInfo<T> trueResult(HttpServletRequest request){
		return ResultInfo.setTrueObject(getRequestId(request));
	}

	protected <T> ResultInfo<T> trueResult(T o,HttpServletRequest request){
		return ResultInfo.setTrueObject(getRequestId(request),o);
	}

	/**
	 *  返回错误结果
	 * @Author wd
	 * @Date 10:54 2020/8/31
	 **/
	protected <T> ResultInfo<T> falseResult(HttpServletRequest request){
		return ResultInfo.setFalse(getRequestId(request));
	}

	protected <T> ResultInfo<T> falseResult(T o,HttpServletRequest request){
		return ResultInfo.setFalse(getRequestId(request),o);
	}

	/**
	 *  自定义返回结果
	 * @Author wd
	 * @Date 10:55 2020/8/31
	 **/
	protected <T> ResultInfo<T> customizeResult(Long code, String msg, T o,HttpServletRequest request){
		return ResultInfo.setR(code,msg,getRequestId(request),o);
	}

}
