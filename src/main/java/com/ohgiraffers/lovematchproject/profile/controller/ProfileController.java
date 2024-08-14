package com.ohgiraffers.lovematchproject.profile.controller;

import com.ohgiraffers.lovematchproject.login.model.dto.CustomOAuth2User;
import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/save")
    public String save() {
        return "profile/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ProfileDTO profileDTO, Model model) throws IOException {

        // 로그인 한 유저의 인증정보 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증정보가 없으면 login 페이지로 redirect
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();
        Long number = customUser.getOAuth().getUserNum();

        //프로필 생성 및 저장
        ProfileDTO savedProfile = profileService.save(profileDTO, number);
        model.addAttribute("profile", savedProfile);
        return "profile/saved";
    }


    @GetMapping("/list") //DB 에서 data 가져와야해서 이때는 Model 객체 사용 해야한다.
    public String findAll(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<ProfileDTO> profileDTOPage = profileService.findAll(pageable);
        model.addAttribute("profileDTOList", profileDTOPage);
        return "profile/list";
    }

    @GetMapping("/update/{profileNo}")
    public String update(@PathVariable("profileNo") Long profileNo, Model model) {
        ProfileDTO profileDTO = profileService.findById(profileNo);
        model.addAttribute("profileUpdate", profileDTO);
        return "profile/update";
    }

    @PostMapping("/update/{profileNo}")
    public String update(@PathVariable("profileNo") Long profileNo, @ModelAttribute ProfileDTO profileDTO,Model model) throws IOException {
        ProfileDTO update = profileService.findById(profileNo);
        //▽ DTO가 데이터를 담고있는지 확인. null이 아니면 if함수 실행
        if(update != null){
            //▽ 위에 @GetMapping에서 담아온 DTO가 get한 데이터들을 여기서 선언한DTO에 set해줌
            update.setProfileNo(profileDTO.getProfileNo());
            update.setProfileFile(profileDTO.getProfileFile());
            update.setProfileName(profileDTO.getProfileName());
            update.setProfilePassword(profileDTO.getProfilePassword());
            update.setProfileGender(profileDTO.getProfileGender());
            update.setProfileAge(profileDTO.getProfileAge());
            update.setProfileHeight(profileDTO.getProfileHeight());
            update.setProfileMBTI(profileDTO.getProfileMBTI());
            update.setProfileLocation(profileDTO.getProfileLocation());
        }
        ProfileDTO profile = profileService.update(update, profileNo);
        model.addAttribute("profile", profile);
        return "profile/saved";
    }

    @GetMapping("/{profileNo}")
    public String findById(@PathVariable Long profileNo, Model model) {
        ProfileDTO profileDTO = profileService.findByDetailId(profileNo);
        model.addAttribute("profile", profileDTO);
        return "profile/detail";
    }


}