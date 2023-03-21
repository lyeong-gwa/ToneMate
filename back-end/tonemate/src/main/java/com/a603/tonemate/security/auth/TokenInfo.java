package com.a603.tonemate.security.auth;

import lombok.Data;
import org.springframework.http.ResponseCookie;

@Data
public class TokenInfo {
    private final String accessToken;
    private final String refreshToken;

    private final Long userId;

    public TokenInfo(String accessToken, String refreshToken, Long userId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    public ResponseCookie generateAccessToken() {
        return ResponseCookie
                .from(JwtProperties.ACCESS_TOKEN, this.accessToken)
                .path("/")
                .maxAge(JwtProperties.ACCESS_TOKEN_TIME)
//                .httpOnly(true)
//                .sameSite("NONE")
//                .secure(true) // 문제 발생 예정
                .build();
    }

    public ResponseCookie generateRefreshToken() {
        System.out.println("generateRT: " + this.refreshToken);
        System.out.println("----------------------------------------");

        return ResponseCookie
                .from(JwtProperties.REFRESH_TOKEN, this.refreshToken)
                .domain("localhost")
                .path("/")
                .maxAge(JwtProperties.REFRESH_TOKEN_TIME)
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .build();
    }

}
