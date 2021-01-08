package com.qr.signature.service;


import com.qr.signature.entity.DeveloperInfo;

/**
 *  token service
 * @Author wd
 * @Date 10:08 2020/9/16
 **/
public interface ITokenInfoService {

	/**
	 *  生成token信息
	 * @Author wd
	 * @Date 10:12 2020/9/16
	 * @param appId
	 * @param appSecret
	 * @param developerInfo
	 * @return string
	 **/
	String getToken(DeveloperInfo developerInfo, String appId, String appSecret);

}
