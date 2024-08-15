package com.ohgiraffers.lovematchproject.date.like.likeservice;


import com.ohgiraffers.lovematchproject.date.like.likemodel.likeentity.LikeEntity;
import com.ohgiraffers.lovematchproject.date.like.likerepository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    // 좋아요 추가
    public LikeEntity addLike(Long userId, String placeName, String placeAddress) {
        // 일일 제한 확인
        if (getDailyLikeCount(userId) >= 10) {
            throw new RuntimeException("일일 좋아요 제한에 도달했습니다.");
        }

        // 전체 제한 확인
        if (getTotalLikeCount(userId) >= 50) {
            throw new RuntimeException("전체 좋아요 제한에 도달했습니다.");
        }

        LikeEntity like = new LikeEntity();
        like.setUserId(userId);
        like.setPlaceName(placeName);
        like.setPlaceAddress(placeAddress);

        return likeRepository.save(like);
    }

    // 일일 좋아요 개수 조회
    private long getDailyLikeCount(Long userId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);
        return likeRepository.findByUserIdAndLikedAtBetween(userId, start, end).size();
    }

    // 전체 좋아요 개수 조회
    private long getTotalLikeCount(Long userId) {
        return likeRepository.countByUserId(userId);
    }

    // 사용자의 모든 좋아요 조회
    public List<LikeEntity> getUserLikes(Long userId) {
        return likeRepository.findByUserId(userId);
    }
}