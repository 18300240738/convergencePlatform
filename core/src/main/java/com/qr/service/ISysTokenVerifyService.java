package com.qr.service;

/**
 *  系统token校验
 * @Author wd
 * @Date 11:29 2020/9/16
 **/
public interface ISysTokenVerifyService {

	/**
	 *  token 校验
	 * @Author wd
	 * @Date 14:07 2020/9/16
	 **/
	Boolean tokenVerify(String token);

	/**
	 *  获取SM4密钥
	 * @Author wd
	 * @Date 13:33 2020/9/17
	 **/
	String getSm4Key(String token);

}
