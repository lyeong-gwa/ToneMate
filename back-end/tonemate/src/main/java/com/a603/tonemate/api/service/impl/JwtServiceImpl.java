package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.JwtService;
import com.a603.tonemate.db.repository.UserRepository;
import com.a603.tonemate.dto.request.TokenReq;
import com.a603.tonemate.security.auth.JwtProperties;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import com.a603.tonemate.security.auth.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    @Override
    public TokenInfo reissue(HttpServletRequest request) {
        TokenReq tokenReq = getToken(request);
        String accessToken = tokenReq.getAccessToken();
        String refreshToken = tokenReq.getRefreshToken();

        //RefreshToken 검증
        try {
            jwtTokenProvider.validateToken(refreshToken);
        } catch (Exception e) {
            System.out.println("refresh 토큰 문제 있음");
            return null;
        }
        System.out.println("refresh 검증 끝");

        //받은 토큰에서 유저 정보 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        System.out.println("받은 토큰에서 유저정보 가져오기 끝");
        //Redis에서 User 정보를 기반으로 저장된 RefreshToken 가져오기
        System.out.println("레디스 토큰 : " + redisTemplate.opsForValue().get(userId.toString()));
        String saveRefreshToken = (String) redisTemplate.opsForValue().get(userId.toString());

        // 로그아웃되어 Redis에 RefreshToken이 존재하지 않는 경우
        if (saveRefreshToken == null) {
            System.out.println("로그아웃함");
            return null;
//            return new ResponseEntity<>("로그아웃이 된 사람", HttpStatus.BAD_REQUEST);
        }

        //보낸 리프레쉬 토큰과 찾은 리프레쉬 토큰이 다른 경우
        if (!saveRefreshToken.equals(refreshToken)) {
            System.out.println("리프레쉬 비교했는데 다름");
            return null;
//            return new ResponseEntity<>("Refresh 정보가 일치하지 않음", HttpStatus.BAD_REQUEST);
        }

        System.out.println("오,,,,토큰 발급해줄게");
        //새로운 토큰 생성
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        //Refresh redis 업데이트
        redisTemplate.opsForValue()
                .set(userId.toString(), tokenInfo.getRefreshToken(), JwtProperties.REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);

        return tokenInfo;
    }

    private TokenReq getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String accessToken = "";
        String refreshToken = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("accessToken")) {
                accessToken = cookie.getValue();
            } else if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        return TokenReq.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }
}
