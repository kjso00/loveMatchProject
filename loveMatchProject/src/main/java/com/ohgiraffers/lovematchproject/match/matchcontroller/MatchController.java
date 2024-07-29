package com.ohgiraffers.lovematchproject.match.matchcontroller;

import com.ohgiraffers.loveMatchProject.match.matchrepository.MatchRepository;
import com.ohgiraffers.loveMatchProject.match.matchservice.MatchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/matchProfiles")
public class MatchController {
    private final MatchService matchService;
    private final MatchRepository matchRepository;


    public MatchController(MatchService matchService, MatchRepository matchRepository) {
        this.matchService = matchService;
        this.matchRepository = matchRepository;
    }

    


}
