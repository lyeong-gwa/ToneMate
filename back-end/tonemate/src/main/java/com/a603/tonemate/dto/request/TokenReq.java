package com.a603.tonemate.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ApiModel(value = "토큰 재발행, 로그아웃 시 사용할 정보", description = "토큰의 정보가 담김")
public class TokenReq {
    private String accessToken;
    private String refreshToken;
}
