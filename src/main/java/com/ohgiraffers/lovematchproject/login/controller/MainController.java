package com.ohgiraffers.lovematchproject.login.controller;

import com.ohgiraffers.lovematchproject.login.model.dto.CustomOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage() {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // authentication.getPrincipal() 메서드는 현재 인증된 사용자의 세부 정보를 반환
        CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();

        Long number = customUser.getOAuth().getUserNum();
        return "profile/home";
    }

}
