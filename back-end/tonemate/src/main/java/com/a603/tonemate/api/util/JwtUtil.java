package com.a603.tonemate.api.util;

import com.a603.tonemate.db.repository.UserRepository;
import com.a603.tonemate.security.auth.JwtProperties;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import com.a603.tonemate.security.auth.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    public TokenInfo reissue(ServletRequest request) {

        String accessToken = ((HttpServletRequest) request).getHeader(JwtProperties.ACCESS_TOKEN);
        String refreshToken = ((HttpServletRequest) request).getHeader(JwtProperties.REFRESH_TOKEN);

        //RefreshToken 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return null;
//            return new ResponseEntity<>("Refresh token 정보가 유효하지 않음", HttpStatus.BAD_REQUEST);
        }

        //받은 토큰에서 유저 정보 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        //유저 정보를 통해 DB에서 유저 ID 가져오기
        Long userId = userRepository.findByNickname(authentication.getName()).get().getUserId();
        //Redis에서 User 정보를 기반으로 저장된 RefreshToken 가져오기
        String saveRefreshToken = (String) redisTemplate.opsForValue().get(userId);

        // 로그아웃되어 Redis에 RefreshToken이 존재하지 않는 경우
        if (saveRefreshToken == null) {
            return null;
//            return new ResponseEntity<>("로그아웃이 된 사람", HttpStatus.BAD_REQUEST);
        }

        //보낸 리프레쉬 토큰과 찾은 리프레쉬 토큰이 다른 경우
        if (!saveRefreshToken.equals(refreshToken)) {
            return null;
//            return new ResponseEntity<>("Refresh 정보가 일치하지 않음", HttpStatus.BAD_REQUEST);
        }

        //새로운 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        //Refresh redis 업데이트
        redisTemplate.opsForValue()
                .set(userId.toString(), tokenInfo.getRefreshToken(), JwtProperties.REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);
        return tokenInfo;
    }


}
