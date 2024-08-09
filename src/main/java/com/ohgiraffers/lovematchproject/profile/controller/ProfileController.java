package com.ohgiraffers.lovematchproject.profile.controller;

import com.ohgiraffers.lovematchproject.login.model.entity.UserEntity;
import com.ohgiraffers.lovematchproject.login.repository.UserRepository;
import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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

        // 현재 로그인한 유저의 ID 가져오기 // <- 이거는 로그인시 제공자(구글,네이버)가 식별하는 id를 찾음. 수정필요.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        String loginUserId = principal.getName();

        // UserEntity를 userId로 조회하여 가져오기
        UserEntity userEntity = userRepository.findByEmail(loginUserId);

        if (userEntity == null) {
            throw new IllegalArgumentException("User not found with userId: " + loginUserId);
        }

        //프로필 생성 및 저장
        ProfileDTO showPersonProfile = profileService.save(profileDTO, userEntity);

        model.addAttribute("profile", showPersonProfile);
        return "profile/saved";
    }


    @GetMapping("/list") //DB 에서 data 가져와야해서 이때는 Model 객체 사용 해야한다.
    public String findAll(Model model){
        List<ProfileDTO> profileDTOList = profileService.findAll();
        model.addAttribute("profileDTOList", profileDTOList);
        return "profile/list";
    }


}
