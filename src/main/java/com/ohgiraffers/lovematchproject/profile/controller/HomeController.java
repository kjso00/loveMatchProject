package com.ohgiraffers.lovematchproject.profile.controller;

import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.service.ProfileService;
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
    public String Home(Model model){
        List<ProfileDTO> profileDTOList = profileService.findAll();
        model.addAttribute("profileDTOList", profileDTOList);
        return "profile/list";
    }

}
