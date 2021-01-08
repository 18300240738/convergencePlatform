package com.qr.signature.service.impl;

import com.alibaba.fastjson.JSON;
import com.qr.encryption.SM2Utils;
import com.qr.encryption.SM3Digest;
import com.qr.encryption.Util;
import com.qr.exception.RRException;
import com.qr.signature.entity.DeveloperInfo;
import com.qr.signature.jwt.JwtEncrypt;
import com.qr.signature.service.ITokenInfoService;
import com.qr.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *  TOKEN 信息实现类
 * @Author wd
 * @since 10:54 2020/9/25
 **/
@Service
@Slf4j
public class TokenInfoServiceImpl implements ITokenInfoService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public String getToken(DeveloperInfo developerInfo,String appId, String appSecret) {
		//生成payloadId
		String payloadId = UUID.randomUUID().toString().replace("-", "");
		log.info("生成业务id: {}",payloadId);

		//*************************** 生成SM4密钥 ***************************
		/**
		 *	SM4密钥由接口授权的AppID、TOKEN和验签盐值生成。生成步骤如下：
		 *	1）	将AppID、payloadId、验签盐值进行字符串拼接。各部分之间使用“~”符号隔开；
		 *	2）	对拼接获得的字符串计算SM3摘要，该摘要结果即为SM4密钥；
		 *	3）	SM4密钥跟随TOKEN的过期而过期，重新申请TOKEN后，SM4密钥也需要重新生成。
		 **/
		String key = developerInfo.getAppid() + "~" + payloadId + "~" + developerInfo.getSalt();
		byte[] md = new byte[32];
		byte[] msg1 = key.getBytes();
		SM3Digest sm3 = new SM3Digest();
		sm3.update(msg1, 0, msg1.length);
		sm3.doFinal(md, 0);
		String s = new String(Hex.encode(md));

		key = s.toUpperCase();
		log.info("sm4Key: {}",key);
		developerInfo.setSm4Key(key);

		String token;
		try {
			//*************************** payloadId加密 ***************************
			String enPayloadId = SM2Utils.encrypt(Util.hexToByte(developerInfo.getPublicKey()),Util.hexToByte(payloadId));

			token = JwtEncrypt.getToken(developerInfo.getAppSecret(), enPayloadId, "openid_" + developerInfo.getId(),
					"");
		} catch (IOException e) {
			log.error("生成TOKEN失败: {}",e);
			throw new RRException("生成TOKEN失败!");
		}

		//*************************** 保存token信息 ***************************
		RedisUtils.add(stringRedisTemplate,token, JSON.toJSONString(developerInfo),2, TimeUnit.HOURS);
		return token;
	}
}
