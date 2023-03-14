package com.a603.tonemate.api.controller;


import com.a603.tonemate.api.service.UserService;
import com.a603.tonemate.dto.request.LogoutReq;
import com.a603.tonemate.dto.request.UserUpdateParam;
import com.a603.tonemate.dto.response.UserResp;
import com.a603.tonemate.exception.NoFileException;
import com.a603.tonemate.security.auth.JwtProperties;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api(tags = {"사용자 관련 API"})
@RequestMapping("/users")
public class UserController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "사용자 정보 수정", notes = "사용자 정보 수정")
    @ExceptionHandler(NoFileException.class)
    @PutMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserInfo(@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token,
                                            @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile,
                                            @RequestPart(required = false) UserUpdateParam param) throws IOException {
        Long userId = jwtTokenProvider.getId(token);
        userService.updateUser(userId, multipartFile, param);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "본인 정보 불러오기", notes = "본인의 이름, 프로필, 팔로워/팔로잉 수 정보")
    @GetMapping("/user")
    public ResponseEntity<UserResp> selectOneUser(@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token) {
        Long userId = jwtTokenProvider.getId(token);
        return new ResponseEntity<>(userService.selectOneUser(userId), HttpStatus.OK);
    }

    @ApiOperation(value = "닉네임 중복 검사", notes = "프로필 변경 시 닉네임 중복 검사하기")
    @GetMapping("/duplicate/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        if (nickname.isEmpty())
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        return new ResponseEntity<>(userService.checkNickname(nickname), HttpStatus.OK);
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃 진행")
    @PostMapping("/user")
    public ResponseEntity<?> logout(LogoutReq logoutReq) {
        //access token 검증
        if (!jwtTokenProvider.validateToken(logoutReq.getAccessToken())) {
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        }

        //Accesstoken에서 유저 정보 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(logoutReq.getAccessToken());

        //redis 에 해당 정보로 저장된 Refresh token이 있는지 여부를 확인후 있다면 삭제
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}