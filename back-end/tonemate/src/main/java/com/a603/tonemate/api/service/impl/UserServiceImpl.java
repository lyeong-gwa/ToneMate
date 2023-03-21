package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.UserService;
import com.a603.tonemate.api.util.FileUtil;
import com.a603.tonemate.db.entity.User;
import com.a603.tonemate.db.repository.UserRepository;
import com.a603.tonemate.dto.request.TokenReq;
import com.a603.tonemate.dto.request.UserUpdateReq;
import com.a603.tonemate.dto.response.UserResp;
import com.a603.tonemate.security.auth.JwtProperties;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import com.a603.tonemate.security.auth.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileUtil fileUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public void updateUser(String token, MultipartFile multipartFile, UserUpdateReq param) throws IOException {
        User user = userRepository.findById(jwtTokenProvider.getId(token)).orElseThrow();
        if (multipartFile != null) {
            String url = fileUtil.upload(multipartFile, "profile");
            user.updateProfile(url);
        }
        if (param.getNickname() != null) {
            user.updateNickName(param.getNickname());
            //토큰도 변경하기 위해 토큰 복호화 후 재생성
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(jwtTokenProvider.getAuthentication(token));
        }
    }

    @Override
    public UserResp selectOneUser(Long userId) {
        return toDto(userRepository.findById(userId).orElseThrow());
    }

    @Override
    public boolean checkNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    //이게 필요 없을 수 있음
    public TokenInfo reissue(TokenReq tokenReq) {
        //RefreshToken 검증
        if (!jwtTokenProvider.validateToken(tokenReq.getRefreshToken())) {
//            return new ResponseEntity<>("Refresh token 정보가 유효하지 않음", HttpStatus.BAD_REQUEST);
        }

        //받은 토큰에서 유저 정보 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenReq.getAccessToken());

        //Redis에서 User 정보를 기반으로 저장된 RefreshToken 가져오기
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());

        // 로그아웃되어 Redis에 RefreshToken이 존재하지 않는 경우
        if (refreshToken == null) {
//            return new ResponseEntity<>("로그아웃이 된 사람", HttpStatus.BAD_REQUEST);
        }

        //보낸 리프레쉬 토큰과 찾은 리프레쉬 토큰이 다른 경우
        if (!refreshToken.equals(tokenReq.getRefreshToken())) {
//            return new ResponseEntity<>("Refresh 정보가 일치하지 않음", HttpStatus.BAD_REQUEST);
        }

        //새로운 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        //Refresh redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), JwtProperties.REFRESH_TOKEN_TIME, TimeUnit.MILLISECONDS);
        return tokenInfo;
    }

}
