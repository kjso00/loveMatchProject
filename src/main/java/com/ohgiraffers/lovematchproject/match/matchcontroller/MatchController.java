package com.ohgiraffers.lovematchproject.match.matchcontroller;

import com.ohgiraffers.lovematchproject.login.model.entity.UserEntity;
import com.ohgiraffers.lovematchproject.login.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.match.matchservice.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;



@RestController
public class MatchController {


    private final MatchService matchService;
    private final UserRepository userRepository;

    @Autowired
    public MatchController(MatchService matchService, UserRepository userRepository) {
        this.matchService = matchService;
        this.userRepository = userRepository;


    }

    @GetMapping("/match/matchProfiles")
    public ModelAndView getMatches(ModelAndView mv) {

//        long loginUserId = 7; // 현재 사용자의 ID를 하드코딩
//        long loginUserId =  // 현재 사용자의 ID

        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        String loginUserIdName = principal.getName(); // OAuth2User의 고유 ID를 가져옴

        // UserEntity 를 가져옴
        UserEntity userEntity = userRepository.findByUserId(loginUserIdName);
        if (userEntity == null) {
            throw new IllegalArgumentException("User not found with id " + loginUserIdName);

        }

        long loginUserId = userEntity.getId(); // UserEntity의 실제 DB ID를 사용
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
