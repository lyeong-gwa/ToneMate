package com.a603.tonemate.security.auth;

import com.a603.tonemate.api.util.JwtUtil;
import com.a603.tonemate.exception.NoTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;

    public JwtExceptionFilter(JwtTokenProvider jwtTokenProvider, JwtUtil jwtUtil) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response); //jwtauthenticaionfilter
        } catch (NoTokenException e) {
            setErrorResponse(response, "토큰이 없습니다.");
        } catch (ExpiredJwtException e) {
            //토큰이 만료되었다는 오류를 받는다면 AT 재발급해서 쿠키 셋팅
            System.out.println("토큰이 만료되었으니까 재발급 하자");
//            TokenReq tokenReq = TokenReq.builder()
//                    .accessToken(request.getHeader(JwtProperties.ACCESS_TOKEN))
//                    .refreshToken(request.getHeader(JwtProperties.REFRESH_TOKEN))
//                    .build();

            TokenInfo tokenInfo = jwtUtil.reissue(request);
            if (tokenInfo != null) {
                response.addHeader("Set-Cookie", tokenInfo.generateRefreshToken().toString());
                response.addHeader("Set-Cookie", tokenInfo.generateAccessToken().toString());
            }

            setErrorResponse(response, "토큰이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            setErrorResponse(response, "손상된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            setErrorResponse(response, "지원하지 않는 토큰입니다.");
        } catch (SignatureException e) {
            setErrorResponse(response, "시그니처 검증에 실패한 토큰입니다.");
        } catch (IllegalArgumentException e) {
            setErrorResponse(response, "토큰에 해당하는 유저가 없습니다.");
        }
    }

    private void setErrorResponse(HttpServletResponse response, String error) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, error);
    }
}
