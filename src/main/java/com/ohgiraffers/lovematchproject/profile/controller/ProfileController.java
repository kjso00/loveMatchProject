package com.ohgiraffers.lovematchproject.profile.controller;

import com.ohgiraffers.lovematchproject.login.model.entity.UserEntity;
import com.ohgiraffers.lovematchproject.login.repository.UserRepository;
import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private ProfileService profileService;
    private UserRepository userRepository;

    @Autowired
    public ProfileController(ProfileService profileService, UserRepository userRepository) {
        this.profileService = profileService;
        this.userRepository = userRepository;
    }

    @GetMapping("/save")
    public String save() {
        return "profile/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ProfileDTO profileDTO, Model model) {

        // 로그인 한 유저의 인증정보 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증정보가 없으면 login 페이지로 redirect
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }


        // loginUserEmail로 UserEntity 조회
        UserEntity userEntity = userRepository.findByEmail(userEmail);
        if (userEntity == null) {
            throw new IllegalArgumentException("User not found with userId: " + userEmail);
        }

        //프로필 생성 및 저장
        ProfileDTO savedProfile = profileService.save(profileDTO, userEntity.getId());
        model.addAttribute("profile", savedProfile);
        return "profile/saved";
    }



    @GetMapping("/list") //DB 에서 data 가져와야해서 이때는 Model 객체 사용 해야한다.
    public String findAll(Model model){
        List<ProfileDTO> profileDTOList = profileService.findAll();
        model.addAttribute("profileDTOList", profileDTOList);
        return "profile/list";
    }


}
