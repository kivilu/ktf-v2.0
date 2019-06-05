package com.kivi.framework.web.jwt;

import java.util.Date;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.DateTimeKit;

public class JwtKit {

	/**
	 * 创建jwt
	 * 
	 * @param identifier 用户标识
	 * @return
	 * @throws Exception
	 */
	public static String create(JwtUserKit jwtUser, String token, Date expiresAt) throws Exception {

		long	nowMillis	= System.currentTimeMillis();

		Builder	builder		= JWT.create().withIssuer("kTF").withAudience(jwtUser.audience()).withExpiresAt(expiresAt)
				.withIssuedAt(DateTimeKit.date(nowMillis));

		return builder.sign(Algorithm.HMAC256(token));
	}

	public static boolean verify(String jwt, String token) {
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(token)).build();

		try {
			jwtVerifier.verify(jwt);
		} catch (TokenExpiredException e) {
			throw new KtfException(KtfError.E_UNAUTHORIZED, "登录状态已过期，请重新登录");
		} catch (JWTVerificationException e) {
			throw new KtfException(KtfError.E_UNAUTHORIZED, "用户未登录");
		}

		return true;
	}

	/**
	 * 解密Jwt，并获取用户标识
	 * 
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static JwtUserDTO getJwtUser(String jwt) throws Exception {
		List<String> auds = JWT.decode(jwt).getAudience();

		return JwtUserKit.audience(auds);
	}

	public static void main(String[] args) {

	}

}
