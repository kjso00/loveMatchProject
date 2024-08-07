package com.ohgiraffers.lovematchproject.profile.controller;

import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

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
    public String save(@ModelAttribute ProfileDTO profileDTO, Model model) {
        ProfileDTO showPersonProfile = profileService.save(profileDTO);
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
