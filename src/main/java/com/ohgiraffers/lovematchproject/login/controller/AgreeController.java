package com.ohgiraffers.lovematchproject.login.controller;

import ch.qos.logback.core.model.Model;
import com.ohgiraffers.lovematchproject.login.model.dto.CustomOAuth2User;
import com.ohgiraffers.lovematchproject.login.model.entity.UserEntity;
import com.ohgiraffers.lovematchproject.login.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AgreeController {

    private final UserRepository userRepository;

    public AgreeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/terms")
    public String terms() {
        System.out.println("getmapping 출력확인");
        return "login/terms";
    }

    @PostMapping("/terms")
    public String acceptTerms(Authentication authentication) {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        UserEntity user = userRepository.findByUserId(oAuth2User.getUserId());
        System.out.println("유저엔터티 동의화면 출력확인");
        System.out.println(user);

        user.setAgree("Y");
        userRepository.save(user);
        return "redirect:/main"; // 동의 후 메인 페이지로 이동
    }
}
