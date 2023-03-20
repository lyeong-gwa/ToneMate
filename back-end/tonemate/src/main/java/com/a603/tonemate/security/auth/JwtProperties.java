package com.a603.tonemate.security.auth;

//@Configuration

public interface JwtProperties {
    //    public static String SECRET; // 우리 서버만 알고 있는 비밀값
    int ACCESS_TOKEN_TIME = 1000 * 60 * 30 * 1000;
    int REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000;
    String AUTHORITIES_KEY = "auth";
    String REFRESH_TOKEN = "refresh-token";
    String ACCESS_TOKEN = "access-token";
}
