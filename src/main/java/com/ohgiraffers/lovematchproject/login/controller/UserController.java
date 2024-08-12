package com.ohgiraffers.lovematchproject.login.controller;


import com.ohgiraffers.lovematchproject.login.model.dto.CustomOAuth2User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/logininfo")
public class UserController {
    @GetMapping("/userinfo")
    public ResponseEntity<Map<String, Object>> getUserInfo(){ // HTTP 응답의 상태 코드와 함께 반환할 데이터를 포함

        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // authentication.getPrincipal() 메서드는 현재 인증된 사용자의 세부 정보를 반환
        CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();

        Map<String, Object> userInfo = new HashMap<>();

        // 인증된 사용자의 넘어온 값(id, 이름, 권한을 저장)
        userInfo.put("userid", customUser.getUserId());
        userInfo.put("authority", customUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER"));
        userInfo.put("name", customUser.getName());

        return ResponseEntity.ok(userInfo);
    }
}
