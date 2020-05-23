package com.itembankmanagement.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {

    // 过期时间12小时
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String openId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("openId", openId)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户id
     */
    public static String getOpenid(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("openId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,12小时后过期
     *
     * @param openId 用户id
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String openId, String secret) {
        return sign(openId, secret, EXPIRE_TIME);
    }

    /**
     * 提供自定义过期时间的签名生成
     * @param openId
     * @param secret
     * @param EXPIRE_TIME 过期时间，单位 秒
     * @return
     */
    public static String sign(String openId, String secret, long EXPIRE_TIME) {
        try {
            // 这里乘1000，所以参数单位变成秒
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带userId信息
            return JWT.create()
                    .withClaim("openId", openId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
