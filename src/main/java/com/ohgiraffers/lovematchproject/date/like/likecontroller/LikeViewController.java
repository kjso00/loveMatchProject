package com.ohgiraffers.lovematchproject.date.like.likecontroller;


import com.ohgiraffers.lovematchproject.date.like.likemodel.likeentity.LikeEntity;
import com.ohgiraffers.lovematchproject.date.like.likeservice.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/date")
public class LikeViewController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/date/likedPlaces")
    public String likedPlaces(Model model) {
        // 실제로는 로그인한 사용자의 ID를 사용해야 합니다.
        Long userId = 1L;
        List<LikeEntity> likedPlaces = likeService.getUserLikes(userId);
        model.addAttribute("likedPlaces", likedPlaces);
        return "likedPlaces";  // likedPlaces.html을 반환
    }
}
