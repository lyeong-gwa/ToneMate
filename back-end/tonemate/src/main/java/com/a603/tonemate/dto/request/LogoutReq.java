package com.a603.tonemate.dto.request;

import lombok.Data;

@Data
public class LogoutReq {
    private String accessToken;
    private String refreshToken;
}
