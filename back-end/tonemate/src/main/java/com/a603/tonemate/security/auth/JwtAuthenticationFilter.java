package com.a603.tonemate.security.auth;

import com.a603.tonemate.exception.NoTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 1. Request Header 에서 JWT Token 추출
        String token = resolveToken((HttpServletRequest) request);
        if (token == null) {
            throw new NoTokenException();
        }
        // 2. validateToken 으로 token 유효성 검사
        if (jwtTokenProvider.validateToken(token)) {
            String isLogin = (String) redisTemplate.opsForValue().get(token);
            // token이 유효할 경우 token에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
            if (ObjectUtils.isEmpty(isLogin)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
