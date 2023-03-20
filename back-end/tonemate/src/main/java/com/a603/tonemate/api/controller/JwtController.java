//package com.a603.tonemate.api.controller;
//
//import com.a603.tonemate.api.service.UserService;
//import com.a603.tonemate.dto.request.TokenReq;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@Api(tags = {"토큰 재발급"})
//@RequestMapping("/tokens")
//public class JwtController {
//
//    //이 controller가 필요 없을 수 있음
//    private final UserService userService;
//
//    @ApiOperation(value = "토큰 재발급", notes = "토큰 재발급")
//    @PostMapping("/reissue")
//    public ResponseEntity<?> reissue(@RequestBody TokenReq tokenReq) {
//
//
//        return new ResponseEntity<>(userService.reissue(tokenReq), HttpStatus.OK);
//    }
//
//}
