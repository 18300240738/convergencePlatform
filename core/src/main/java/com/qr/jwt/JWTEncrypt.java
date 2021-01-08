package com.qr.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import java.util.Date;


/**
 *  JWT生成
 * @Author wd
 * @Description //TODO
 * @Date 18:16 2020/9/15
 **/
public class JWTEncrypt {

	/**
	 * 生成加密后的token
	 * @return 加密后的token
	 */
	public static String getToken(String PayloadID, String iss,String openid,Date iatDate,String appSecret) {
		String token = null;
		try {
			//两小时有效期
			Date expiresAt = new Date(System.currentTimeMillis() + 2L * 3600L * 1000L);

			token = JWT.create()
					// 签发服务器
					.withIssuer(iss)
					.withClaim("PayloadID", PayloadID)
					// 开发方身份标识OpenId
					.withClaim("openid", openid)
					// 签发时间 2020-08-15 00:00:00
					.withIssuedAt(iatDate)
					// 过期时间 2020-08-15 02:00:00
					.withExpiresAt(expiresAt)
					// 使用了HMAC256加密算法。
					// appSecret是用来加密数字签名的密钥。
					.sign(Algorithm.HMAC256(appSecret));



		} catch (JWTCreationException exception){
			//Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;
	}
}
