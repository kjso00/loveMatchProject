package com.ohgiraffers.lovematchproject.login.controller;

import com.ohgiraffers.lovematchproject.login.model.dto.CustomOAuth2User;
import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final ProfileService profileService;
    public MainController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/main")
    public String mainPage(Model model, @PageableDefault(size = 10) Pageable pageable) {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // authentication.getPrincipal() 메서드는 현재 인증된 사용자의 세부 정보를 반환
        CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();
        Long number = customUser.getOAuth().getUserNum();
        System.out.println("인증정보 :" + authentication);
        System.out.println("---------------");
        System.out.println("넘길번호 :" + number);

        // 등록된 회원들의 리스트 불러오기
        Page<ProfileDTO> profileDTOPage = profileService.findAll(pageable);
        model.addAttribute("profileDTOList", profileDTOPage);
        return "profile/list";
    }

}
