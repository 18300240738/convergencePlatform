package com.qr.service.impl;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qr.encryption.SM3Digest;
import com.qr.entity.DeveloperInfo;
import com.qr.exception.RRException;
import com.qr.jwt.JWTDecrypt;
import com.qr.service.ISysTokenVerifyService;
import com.qr.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 *  token校验实现类
 * @Author wd
 **/
@Service
@Slf4j
public class SysTokenVerifyServiceImpl implements ISysTokenVerifyService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public Boolean tokenVerify(String token) {
		if (StringUtils.isBlank(token)) {
			return false;
		}
		String developerStr = RedisUtils.get(stringRedisTemplate, token);
		if (StringUtils.isBlank(developerStr)) {
			return false;
		}
		DeveloperInfo developerInfo = JSON.parseObject(developerStr, DeveloperInfo.class);

		try {
			DecodedJWT decodedJwt = JWTDecrypt.deToken(token, developerInfo.getAppSecret(), "");
			//************************ 验证token ******************************
//			String payload = decodedJWT.getPayload();
//
//			payload = new String(Base64.getUrlDecoder().decode(payload));
//			JSONObject jsonObject = JSON.parseObject(payload);
//			String payloadId = RedisUtils.get(stringRedisTemplate, "openid_"+developerInfo.getId());
//			log.info("payloadId: {}",payloadId);
//			String encrypt = SM2Utils.encrypt(Util.hexToByte(developerInfo.getPublicKey()), Util.hexToByte(payloadId));
//			log.info("签名:{}",encrypt);
//			log.info("被验证签名:{}",jsonObject.getString("payloadId"));
//			if (!jsonObject.getString("payloadId").equals(encrypt))
//				return false;
		}catch (Exception e){
			log.error("token解析异常:{}",e);
			return false;
		}


		return true;
	}

	public String getSm4Key1(String token) {
		/**
		 *	SM4密钥由接口授权的AppID、TOKEN和验签盐值生成。生成步骤如下：
		 *	1）	将AppID、payloadId、验签盐值进行字符串拼接。各部分之间使用“~”符号隔开；
		 *	2）	对拼接获得的字符串计算SM3摘要，该摘要结果即为SM4密钥；
		 *	3）	SM4密钥跟随TOKEN的过期而过期，重新申请TOKEN后，SM4密钥也需要重新生成。
		 **/
		String developerStr = RedisUtils.get(stringRedisTemplate, token);
		if (StringUtils.isBlank(developerStr)) {
			throw new RRException("TOKEN无效!");
		}
		DeveloperInfo developerInfo = JSON.parseObject(developerStr, DeveloperInfo.class);

		String payloadId = RedisUtils.get(stringRedisTemplate, "openid_"+developerInfo.getId());
		if (StringUtils.isBlank(payloadId)) {
			throw new RRException("TOKEN无效!");
		}
		String key = developerInfo.getAppid() + "~" + payloadId + "~" + developerInfo.getSalt();

		//生成SM4 密钥
		byte[] md = new byte[32];
		byte[] msg1 = key.getBytes();
		SM3Digest sm3 = new SM3Digest();
		sm3.update(msg1, 0, msg1.length);
		sm3.doFinal(md, 0);
		String s = new String(Hex.encode(md));

		key = s.toUpperCase();
		log.info("sm4Key: {}",key);
		return key;
	}

	@Override
	public String getSm4Key(String token) {

		String developerStr = RedisUtils.get(stringRedisTemplate, token);
		if (StringUtils.isBlank(developerStr)) {
			throw new RRException("TOKEN无效!");
		}
		DeveloperInfo developerInfo = JSON.parseObject(developerStr, DeveloperInfo.class);

		return developerInfo.getSm4Key();
	}
}
