package com.a603.tonemate.api.controller;


import com.a603.tonemate.api.service.KaraokeService;
import com.a603.tonemate.api.service.UserService;
import com.a603.tonemate.dto.request.UserUpdateReq;
import com.a603.tonemate.dto.response.KaraokeResp;
import com.a603.tonemate.dto.response.UserResp;
import com.a603.tonemate.exception.NoFileException;
import com.a603.tonemate.security.auth.JwtProperties;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api(tags = {"사용자 관련 API"})
public class UserController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final KaraokeService karaokeService;

    //users/{userId}
    @ApiOperation(value = "사용자 정보 수정", notes = "사용자 정보 수정")
    @ExceptionHandler(NoFileException.class)
    @PutMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserInfo(@CookieValue(value = JwtProperties.ACCESS_TOKEN) String token,
                                            @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile,
                                            @RequestPart(required = false) String nickname) throws IOException {


        userService.updateUser(token, multipartFile, new UserUpdateReq(nickname));
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "본인 정보 불러오기", notes = "본인의 이름, 프로필 정보")
    @GetMapping("/users")
    public ResponseEntity<UserResp> selectOneUser(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken) {
        System.out.println("쿠키 확인: " + accessToken);
        Long userId = jwtTokenProvider.getId(accessToken);
        System.out.println("본인 정보 확인 가넝");
        return new ResponseEntity<>(userService.selectOneUser(userId), HttpStatus.OK);
    }

    @ApiOperation(value = "닉네임 중복 검사", notes = "프로필 변경 시 닉네임 중복 검사하기")
    @GetMapping("/duplicate")
    public ResponseEntity<?> checkNickname(String nickname) {
        System.out.println("중복검사 : " + nickname);
        if (userService.checkNickname(nickname)) {
            return new ResponseEntity<>(FAIL, HttpStatus.OK);
        }
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자가 찜한 노래 목록 불러오기", notes = "노래들의 기본 정보들 보여줌")
    @GetMapping("/users/like")
    public ResponseEntity<KaraokeResp> likeSongs(@CookieValue(name = JwtProperties.ACCESS_TOKEN) String accessToken, @PageableDefault(size = 15) Pageable pageable) {
        Long userId = jwtTokenProvider.getId(accessToken);
        return new ResponseEntity<>(karaokeService.findLikeList(userId, pageable), HttpStatus.OK);
    }

}