package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.UserService;
import com.a603.tonemate.api.util.FileUtil;
import com.a603.tonemate.db.entity.User;
import com.a603.tonemate.db.repository.UserRepository;
import com.a603.tonemate.dto.request.TokenReq;
import com.a603.tonemate.dto.request.UserUpdateReq;
import com.a603.tonemate.dto.response.UserResp;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public boolean logout(TokenReq tokenReq) {
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenReq.getAccessToken());
        Long userId = userRepository.findByNickname(authentication.getName()).get().getUserId();
        //redis 에 해당 정보로 저장된 Refresh token이 있는지 여부를 확인후 있다면 삭제
        if (redisTemplate.opsForValue().get(userId.toString()) != null) {
            redisTemplate.delete(userId.toString());
            return true;
        } else {
            return false;
        }
    }


}
