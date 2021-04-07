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
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateUtil;

public class JwtKit {
    private final static int DAYS_OF_30_YEAR = 30 * 365;

    /**
     * 创建jwt，有效期默认：为30年
     * 
     * @param jwtUser 用户信息
     * @param token token
     * @return
     * @throws Exception
     */
    public static String create(JwtUserKit jwtUser, String token) throws Exception {
        Date expiresAt = DateUtil.addDays(DateTimeKit.now(), DAYS_OF_30_YEAR);
        return create(jwtUser, token, expiresAt);
    }

    /**
     * 创建jwt
     * 
     * @param jwtUser 用户信息
     * @param token token
     * @param expiresAt 有效期
     * @return
     * @throws Exception
     */
    public static String create(JwtUserKit jwtUser, String token, Date expiresAt) throws Exception {
        Builder builder =
            JWT.create().withIssuer("kTF").withAudience(jwtUser.audience()).withIssuedAt(DateTimeKit.now());

        if (expiresAt != null)
            builder = builder.withExpiresAt(expiresAt);

        return builder.sign(Algorithm.HMAC256(token));
    }

    public static boolean verify(String jwt, String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(token)).build();

        try {
            jwtVerifier.verify(jwt);
        } catch (TokenExpiredException e) {
            throw new KtfException(KtfError.E_UNAUTHORIZED, "登录状态已过期，请重新登录", e);
        } catch (JWTVerificationException e) {
            throw new KtfException(KtfError.E_UNAUTHORIZED, "Token验证失败，请重新登录", e);
        }

        return true;
    }

    public static boolean isExpired(String jwtToken) {
        Date now = ClockUtil.currentDate();
        Date expiresAt = JWT.decode(jwtToken).getExpiresAt();

        return expiresAt.before(now);
    }

    public static Date getIssuedAt(String jwtToken) {

        return JWT.decode(jwtToken).getIssuedAt();
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
