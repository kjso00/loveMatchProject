package com.ohgiraffers.lovematchproject.date.like.likemodel.likeentity;

import com.ohgiraffers.lovematchproject.date.datemodel.dateentity.DateEntity;
import com.ohgiraffers.lovematchproject.common.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * LikeEntity 클래스
 * 이 클래스는 사용자가 데이트 장소에 대해 '찜'한 정보를 나타냄
 */
@Entity
@Table(name = "likes")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // user_id에서 id로 변경
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "date_id", nullable = false)
    private DateEntity date;

    @Column(nullable = false)
    private LocalDateTime likeCreatedAt;

    // 기본 생성자
    public LikeEntity() {
    }

    // 모든 필드를 포함하는 생성자
    public LikeEntity(UserEntity user, DateEntity date) {
        this.user = user;
        this.date = date;
        this.likeCreatedAt = LocalDateTime.now();
    }

    // Getter와 Setter 메서드
    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public DateEntity getDate() {
        return date;
    }

    public void setDate(DateEntity date) {
        this.date = date;
    }

    public LocalDateTime getLikeCreatedAt() {
        return likeCreatedAt;
    }

    public void setLikeCreatedAt(LocalDateTime likeCreatedAt) {
        this.likeCreatedAt = likeCreatedAt;
    }

    @Override
    public String toString() {
        return "LikeEntity{" +
                "likeId=" + likeId +
                ", userId=" + (user != null ? user.getUserId() : null) +
                ", dateId=" + (date != null ? date.getDateId() : null) +
                ", likeCreatedAt=" + likeCreatedAt +
                '}';
    }
}