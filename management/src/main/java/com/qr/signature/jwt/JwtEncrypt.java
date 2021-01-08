package com.qr.signature.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Date;


/**
 *  JWT生成
 * @Author wd
 * @Date 18:16 2020/9/15
 **/
@Slf4j
public class JwtEncrypt {

	private JwtEncrypt(){

	}

	/**
	 * 生成加密后的token
	 * @return 加密后的token
	 */
	public static String getToken(String appSecret,String payloadId,String openid,String iss) throws IOException {
		String token = null;
		//两小时有效期
		Date expiresAt = new Date(System.currentTimeMillis() + 2L * 3600L * 1000L);

		token = JWT.create()
				// 签发服务器
				.withIssuer(iss)
				.withClaim("payloadId", payloadId)
				// 开发方身份标识OpenId
				.withClaim("openid", openid)
				// 签发时间 2020-08-15 00:00:00
				.withIssuedAt(new Date())
				// 过期时间 2020-08-15 02:00:00
				.withExpiresAt(expiresAt)
				// 使用了HMAC256加密算法。
				// appSecret是用来加密数字签名的密钥。
				.sign(Algorithm.HMAC256(appSecret));

		log.info("TOKEN信息: {}",token);
		return token;
	}
}
