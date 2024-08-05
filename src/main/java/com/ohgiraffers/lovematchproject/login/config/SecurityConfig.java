package com.ohgiraffers.lovematchproject.login.config;

import com.ohgiraffers.lovematchproject.login.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .formLogin((login) -> login.disable())
                .httpBasic((basic) -> basic.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**","/static/**", "/image/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
//                        .loginPage("/my")
                        .defaultSuccessUrl("/",true)// 로그인 성공하면 여기로 리다이렉트
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)))
                .logout(logout -> logout
//                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler())
                        .permitAll());
        return http.build();
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        logoutSuccessHandler.setDefaultTargetUrl("/"); // 로그아웃후에 매핑할 주소
        return logoutSuccessHandler;
    }

}
