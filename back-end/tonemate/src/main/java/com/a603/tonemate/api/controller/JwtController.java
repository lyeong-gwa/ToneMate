package com.a603.tonemate.api.controller;

import com.a603.tonemate.dto.request.TokenReq;
import com.a603.tonemate.security.auth.JwtProperties;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import com.a603.tonemate.security.auth.TokenInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@Api(tags = {"토큰 재발급"})
@RequestMapping("/tokens")
public class JwtController {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "토큰 재발급", notes = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(TokenReq tokenReq) {

        //RefreshToken 검증
        if (!jwtTokenProvider.validateToken(tokenReq.getRefreshToken())) {
            return new ResponseEntity<>("Refresh token 정보가 유효하지 않음", HttpStatus.BAD_REQUEST);
        }

        //받은 토큰에서 유저 정보 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenReq.getAccessToken());

        //Redis에서 User 정보를 기반으로 저장된 RefreshToken 가져오기
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());

        // 로그아웃되어 Redis에 RefreshToken이 존재하지 않는 경우
        if (!ObjectUtils.isEmpty(refreshToken)) {
            return new ResponseEntity<>("로그아웃이 된 사람", HttpStatus.BAD_REQUEST);
        }

        //보낸 리프레쉬 토큰과 찾은 리프레쉬 토큰이 다른 경우
        assert refreshToken != null;
        if (!refreshToken.equals(tokenReq.getRefreshToken())) {
            return new ResponseEntity<>("Refresh 정보가 일치하지 않음", HttpStatus.BAD_REQUEST);
        }

        //새로운 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        //Refresh redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), JwtProperties.REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);

        return new ResponseEntity<>(tokenInfo, HttpStatus.OK);
    }

}
