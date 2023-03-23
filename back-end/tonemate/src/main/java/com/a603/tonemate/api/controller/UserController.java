package com.a603.tonemate.api.controller;


import com.a603.tonemate.api.service.UserService;
import com.a603.tonemate.dto.request.TokenReq;
import com.a603.tonemate.dto.request.UserUpdateReq;
import com.a603.tonemate.dto.response.UserResp;
import com.a603.tonemate.exception.NoFileException;
import com.a603.tonemate.security.auth.JwtProperties;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "사용자 정보 수정", notes = "사용자 정보 수정")
    @ExceptionHandler(NoFileException.class)
    @PutMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserInfo(@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token,
                                            @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile,
                                            @RequestPart(required = false) UserUpdateReq param) throws IOException {
        userService.updateUser(token, multipartFile, param);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "본인 정보 불러오기", notes = "본인의 이름, 프로필, 팔로워/팔로잉 수 정보")
    @GetMapping("/user")
    public ResponseEntity<UserResp> selectOneUser(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String token) {
        System.out.println("쿠키 확인: " + token);
        Long userId = jwtTokenProvider.getId(token);
        System.out.println("본인 정보 확인 가넝");
        return new ResponseEntity<>(userService.selectOneUser(userId), HttpStatus.OK);
    }

    @ApiOperation(value = "닉네임 중복 검사", notes = "프로필 변경 시 닉네임 중복 검사하기")
    @GetMapping("/duplicate/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        if (nickname.isEmpty())
            return new ResponseEntity<>("닉네임을 입력해주세요", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(userService.checkNickname(nickname), HttpStatus.OK);
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃 진행")
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken,
                                    @CookieValue(name = JwtProperties.REFRESH_TOKEN) String refreshToken) {
        //access token 검증
        if (!jwtTokenProvider.validateToken(accessToken)) {
            return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
        }
        TokenReq tokenReq = TokenReq.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        if (userService.logout(tokenReq)) {
            return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그아웃 안됨", HttpStatus.NO_CONTENT);
        }
    }

}