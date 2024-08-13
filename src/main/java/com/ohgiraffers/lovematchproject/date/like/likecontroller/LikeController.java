package com.ohgiraffers.lovematchproject.date.like.likecontroller;


import com.ohgiraffers.lovematchproject.date.like.likemodel.likeentity.LikeEntity;
import com.ohgiraffers.lovematchproject.date.like.likeservice.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // 좋아요 추가
    @PostMapping
    public ResponseEntity<LikeEntity> addLike(@RequestParam Long userId,
                                              @RequestParam String placeName,
                                              @RequestParam String placeAddress) {
        LikeEntity like = likeService.addLike(userId, placeName, placeAddress);
        return ResponseEntity.ok(like);
    }

    // 사용자의 모든 좋아요 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeEntity>> getUserLikes(@PathVariable Long userId) {
        List<LikeEntity> likes = likeService.getUserLikes(userId);
        return ResponseEntity.ok(likes);
    }
}