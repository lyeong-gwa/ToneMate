package com.a603.tonemate.api.service.impl;

import com.a603.tonemate.api.service.UserService;
import com.a603.tonemate.api.util.FileUtil;
import com.a603.tonemate.db.entity.User;
import com.a603.tonemate.db.repository.UserRepository;
import com.a603.tonemate.dto.request.UserUpdateParam;
import com.a603.tonemate.dto.response.UserResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileUtil fileUtil;

    @Override
    public void updateUser(Long userId, MultipartFile multipartFile, UserUpdateParam param) throws IOException {
        User user = userRepository.findById(userId).orElseThrow();
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

}
