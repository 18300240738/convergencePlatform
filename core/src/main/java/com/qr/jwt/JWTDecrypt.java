package com.qr.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


/**
 *  JWT解密token
 * @Author wd
 * @Description //TODO
 * @Date 18:37 2020/9/15
 **/
public class JWTDecrypt {

	/**
	 * 先验证token是否被伪造，然后解码token。
	 * @param token 字符串token
	 * @return 解密后的DecodedJWT对象，可以读取token中的数据。
	 */
	public static DecodedJWT deToken(String token,String appSecret,String iss) {
		DecodedJWT jwt = null;
		// 使用了HMAC256加密算法。
		// mysecret是用来加密数字签名的密钥。
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(appSecret))
				.withIssuer(iss)
				.build();
		jwt = verifier.verify(token);
		return jwt;
	}
}
