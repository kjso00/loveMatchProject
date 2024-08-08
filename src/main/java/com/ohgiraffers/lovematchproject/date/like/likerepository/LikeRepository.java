package com.ohgiraffers.lovematchproject.date.like.likerepository;

import com.ohgiraffers.lovematchproject.date.datemodel.dateentity.DateEntity;
import com.ohgiraffers.lovematchproject.date.like.likemodel.likeentity.LikeEntity;
import com.ohgiraffers.lovematchproject.common.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * LikeRepository 인터페이스
 * 이 인터페이스는 LikeEntity에 대한 데이터베이스 작업을 처리
 */
@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    /**
     * 특정 사용자가 특정 데이트 장소를 찜했는지 확인
     * @param user 사용자
     * @param date 데이트 장소
     * @return 찜한 정보 (있는 경우)
     */
    Optional<LikeEntity> findByUserAndDate(UserEntity user, DateEntity date);

    /**
     * 특정 사용자가 찜한 모든 데이트 장소를 조회
     * @param user 사용자
     * @return 사용자가 찜한 데이트 장소 목록
     */
    List<LikeEntity> findByUser(UserEntity user);

    /**
     * 특정 데이트 장소를 찜한 사용자 수를 조회
     * @param date 데이트 장소
     * @return 찜한 사용자 수
     */
    long countByDate(DateEntity date);

    /*
     * 특정 사용자가 주어진 기간 동안 찜한 데이트 장소 수를 조회
     * @param user 사용자
     * @param startDateTime 시작 일시
     * @param endDateTime 종료 일시
     * @return 찜한 데이트 장소 수
     */

    long countByUser(UserEntity user);

    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.user = :user AND l.likeCreatedAt BETWEEN :startDateTime AND :endDateTime")
    long countLikesByUserAndDateRange(@Param("user") UserEntity user,
                                      @Param("startDateTime") LocalDateTime startDateTime,
                                      @Param("endDateTime") LocalDateTime endDateTime);
}