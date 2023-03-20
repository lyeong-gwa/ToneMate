package com.a603.tonemate.security.auth;

import lombok.Data;
import org.springframework.http.ResponseCookie;


@Data
public class TokenInfo {
    private final String accessToken;
    private final String refreshToken;

    private final String nickname;

    public TokenInfo(String accessToken, String refreshToken, String nickname) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.nickname = nickname;
    }

    public ResponseCookie generateAccessToken() {
        return ResponseCookie
                .from(JwtProperties.ACCESS_TOKEN, this.accessToken)
                .maxAge(JwtProperties.ACCESS_TOKEN_TIME)
                .httpOnly(true)
                .sameSite("STRICT")
                .secure(true) // 문제 발생 예정
                .build();
    }

    public ResponseCookie generateRefreshToken() {
        return ResponseCookie
                .from(JwtProperties.REFRESH_TOKEN, this.refreshToken)
                .maxAge(JwtProperties.REFRESH_TOKEN_TIME)
                .httpOnly(true)
                .sameSite("STRICT")
                .secure(true)
                .build();
    }

}
