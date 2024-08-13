package com.ohgiraffers.lovematchproject.date.like.likerepository;


import com.ohgiraffers.lovematchproject.date.like.likemodel.likeentity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    // 특정 사용자의 모든 좋아요 조회
    List<LikeEntity> findByUserId(Long userId);

    // 특정 사용자의 특정 기간 동안의 좋아요 조회
    List<LikeEntity> findByUserIdAndLikedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    // 특정 사용자의 좋아요 개수 조회
    long countByUserId(Long userId);
}