package com.a603.tonemate.config;

import com.a603.tonemate.api.util.JwtUtil;
import com.a603.tonemate.security.auth.JwtAuthenticationFilter;
import com.a603.tonemate.security.auth.JwtExceptionFilter;
import com.a603.tonemate.security.auth.JwtTokenProvider;
import com.a603.tonemate.security.handler.AuthenticationFailureHandler;
import com.a603.tonemate.security.handler.AuthenticationSuccessHandler;
import com.a603.tonemate.security.oauth2.CustomOAuth2AuthorizationRequestRepository;
import com.a603.tonemate.security.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;
    private final CorsConfig corsConfig;
    private final CustomOAuth2AuthorizationRequestRepository<OAuth2AuthorizationRequest> customOAuth2AuthorizationRequestRepository;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http
                .httpBasic().disable() // 기본 로그인 화면 비활성화
                .formLogin().disable() // 폼로그인 비활성화
                .csrf().disable()   // csrf 보안 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt사용으로 session 비활성화
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll() // 인가 검증
                .and()
                .oauth2Login()
                .authorizationEndpoint(authorize -> {
                    authorize.authorizationRequestRepository(
                            customOAuth2AuthorizationRequestRepository);
                })
                .userInfoEndpoint(userInfo -> {
                    userInfo.userService(customOAuth2UserService);
                })
                .loginProcessingUrl("/oauth/login/*") //auth/login/google/code=2358072305dfs
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .addFilter(corsConfig.corsFilter()) // cors 설정. 일단 전부 풀어놓음
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }
}
