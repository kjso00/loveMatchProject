package com.ohgiraffers.lovematchproject.match.matchcontroller;

import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.match.matchservice.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



import java.util.List;



@RestController
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;

    }
//    private final MatchService matchService;
//    private final CustomOAuth2UserService customOAuth2UserService;
//    private final OAuth2Response oAuth2Response;
//
//    @Autowired
//    public MatchController(MatchService matchService, CustomOAuth2UserService customOAuth2UserService, OAuth2Response oAuth2Response) {
//        this.matchService = matchService;
//        this.customOAuth2UserService = customOAuth2UserService;
//        this.oAuth2Response = oAuth2Response;
//
//    }

    @GetMapping("/match/matchProfiles")
    public ModelAndView getMatches(ModelAndView mv) {

        long loginUserId = 7; // 현재 사용자의 ID를 하드코딩
//        long loginUserId = Long.parseLong(OAuth2Response.getProviderId()); // 현재 사용자의 ID
        ProfileDTO loginUser = matchService.getLoginUser(loginUserId);
        List<ProfileDTO> targetGender = matchService.getFilteringGender(loginUserId);
        List<ProfileDTO> matchResults = matchService.calculatematchScores(loginUserId);

        mv.addObject("loginUser", loginUser);
        mv.addObject("filterGender", targetGender);
        mv.addObject("matchResults", matchResults);

        mv.setViewName("match/matchProfiles");

        return mv;

//        long loginUser = 1; //현재 사용자의 ID를 하드코딩 -> 로그인사용자로 변경필요
    }




}
