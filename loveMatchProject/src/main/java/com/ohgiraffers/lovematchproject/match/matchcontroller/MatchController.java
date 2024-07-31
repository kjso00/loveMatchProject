package com.ohgiraffers.lovematchproject.match.matchcontroller;

import com.ohgiraffers.lovematchproject.match.matchmodel.dto.MatchDTO;
import com.ohgiraffers.lovematchproject.match.matchservice.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class MatchController {

    @Autowired
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/match/matchProfiles")
    public String getMatches(Model model) {
        long loginUserId = 1; // 현재 사용자의 ID를 하드코딩
        MatchDTO loginUser = matchService.getLoginUser(loginUserId);
        List<MatchDTO> targetGender = matchService.getFilteringGender(loginUserId);
        List<MatchDTO> matchResults = matchService.calculatematchScores(loginUserId);

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("filterGender", targetGender);
        model.addAttribute("matchResults", matchResults);
        return "match/matchProfiles";

//        long loginUser = 1; //현재 사용자의 ID를 하드코딩 -> 로그인사용자로 변경필요
    }

}
