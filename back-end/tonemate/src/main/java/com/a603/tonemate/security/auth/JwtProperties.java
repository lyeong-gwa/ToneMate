package com.a603.tonemate.security.auth;

import org.springframework.beans.factory.annotation.Value;

public class JwtProperties {
    public static final int ACCESS_TOKEN_TIME = 1000 * 60 * 30 * 1000;
    public static final int REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000;
    public static final String AUTHORITIES_KEY = "auth";
    public static final String REFRESH_TOKEN = "refresh-token";
    public static final String ACCESS_TOKEN = "access-token";
    public static final String AUTHORIZATION = "Authorization";
    @Value("${jwt.secret}")
    public static String SECRET; // 우리 서버만 알고 있는 비밀값
}
