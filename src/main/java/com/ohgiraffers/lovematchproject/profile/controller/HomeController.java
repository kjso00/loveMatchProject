package com.ohgiraffers.lovematchproject.profile.controller;

import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {

    private final ProfileService profileService;
    public HomeController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/home")
    public String Home(Model model, @PageableDefault(size = 10) Pageable pageable){
        Page<ProfileDTO> profileDTOPage = profileService.findAll(pageable);
        model.addAttribute("profileDTOList", profileDTOPage);
        return "profile/list";
    }

}
