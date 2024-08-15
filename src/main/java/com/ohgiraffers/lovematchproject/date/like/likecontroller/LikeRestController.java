package com.ohgiraffers.lovematchproject.date.like.likecontroller;


import com.ohgiraffers.lovematchproject.date.like.likemodel.likeentity.LikeEntity;
import com.ohgiraffers.lovematchproject.date.like.likeservice.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeRestController {

    @Autowired
    private LikeService likeService;

    // 사용자의 모든 좋아요 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeEntity>> getUserLikes(@PathVariable Long userId) {
        List<LikeEntity> likes = likeService.getUserLikes(userId);
        return ResponseEntity.ok(likes);
    }

    @PostMapping
    public ResponseEntity<LikeEntity> addLike(@RequestBody Map<String, Object> request) {
        Long userId = Long.parseLong(request.get("userId").toString());
        String placeName = (String) request.get("placeName");
        String placeAddress = (String) request.get("placeAddress");

        LikeEntity like = likeService.addLike(userId, placeName, placeAddress);
        return ResponseEntity.ok(like);
    }

    @GetMapping("/date/likedPlaces")
    public String likedPlaces(Model model) {
        // 여기서는 임시로 userId를 1로 설정했습니다. 실제로는 로그인한 사용자의 ID를 사용해야 합니다.
        Long userId = 1L;
        List<LikeEntity> likedPlaces = likeService.getUserLikes(userId);
        model.addAttribute("likedPlaces", likedPlaces);
        return "/date/likedPlaces";
    }
}