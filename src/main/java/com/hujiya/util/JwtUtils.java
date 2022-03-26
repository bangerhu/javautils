package com.hujiya.util;

import com.google.gson.Gson;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * jwt 工具类
 *
 * @author Json
 * @date 2021/11/15 16:38
 */
@Slf4j
public class JwtUtils {
    /**
     * jwt 载荷信息 key
     */
    public static final String JWT_PAYLOAD_USER_KEY = "user";

    /**
     * 刷新token次数 默认为0起始
     */
    public static final String REFRESH_TOKEN_NUMBER = "refreshTokenNumber";

    /**
     * access-token
     */
    public static final String ACCESS_TOKEN = "access-token";

    /**
     * 加密token
     *
     * @param userInfo  载荷中的数据
     * @param jwtConfig jwt 配置
     * @return JWT
     */
    public static String createAccessToken(Object userInfo, JwtConfig jwtConfig) {
        Map<String, Object> map = new HashMap<>();
        map.put(JWT_PAYLOAD_USER_KEY, userInfo);
        map.put(REFRESH_TOKEN_NUMBER, 0);
        return Jwts.builder()
                .setClaims(map)
                .setId(createJTI())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpire() * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getAccessTokenSecret())
                .compact();
    }


    /**
     * 生成 RefreshToken
     *
     * @return refreshToken
     */
    public static String createRefreshToken(Object userInfo, JwtConfig jwtConfig) {
        return createRefreshToken(userInfo, jwtConfig, 0);
    }


    /**
     * 生成 RefreshToken
     *
     * @return refreshToken
     */
    public static String createRefreshToken(Object userInfo, JwtConfig jwtConfig, int refreshTokenNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put(JWT_PAYLOAD_USER_KEY, userInfo);
        map.put(REFRESH_TOKEN_NUMBER, refreshTokenNumber);
        return Jwts.builder()
                .setClaims(map)
                .setId(createJTI())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshTokenExpire() * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getRefreshTokenSecret())
                .compact();
    }


    /**
     * 解析 refreshToken
     *
     * @param token     token
     * @param jwtConfig 配置项
     * @return 载荷信息
     */
    public static Claims parserAccessToken(String token, JwtConfig jwtConfig) {
        return parserToken(token, jwtConfig.getAccessTokenSecret());
    }

    /**
     * 解析  token
     *
     * @param token     token
     * @param jwtConfig 配置项
     * @return 载荷信息
     */
    public static Claims parserRefreshToken(String token, JwtConfig jwtConfig) {
        return parserToken(token, jwtConfig.getRefreshTokenSecret());
    }

    /**
     * 获取token中的载荷信息
     *
     * @param token 用户请求中的令牌
     * @return 用户信息
     */
    public static Claims parserToken(String token, String secretKey) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("token{}过期", token, e);
            throw e;
        }
    }


    private static String createJTI() {
        return new String(java.util.Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }


    public static void main(String[] args) {

        TUser tUser = new TUser();
        tUser.setId(UUID.randomUUID().toString());
        tUser.setName("com/hujiya/test");
        tUser.setPhone("11111111111");
        String token = createAccessToken(tUser, new JwtConfig());
        System.out.println(token);

        System.out.println(Base64.base64Decode("eyJhbGciOiJIUzI1NiJ9"));

        Claims claims = parserAccessToken(token, new JwtConfig());
        System.out.println(new Gson().toJson(claims));
    }

    @Data
    static class TUser {

        private String id;
        private String name;
        private String phone;

    }

    @Data
    static class JwtConfig {
        private long refreshTokenExpire = 1000;
        private long accessTokenExpire = 1000;


        private String refreshTokenSecret = "12345678909";
        private String accessTokenSecret = "12345678909";
    }
}
