package com.ohgiraffers.lovematchproject.date.like.likemodel.likeentity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 좋아요 고유 식별자

    @Column(nullable = false)
    private Long userId;  // 사용자 ID

    @Column(nullable = false)
    private String placeName;  // 장소 이름

    @Column(nullable = false)
    private String placeAddress;  // 장소 주소

    @Column(nullable = false)
    private LocalDateTime likedAt;  // 좋아요 누른 시간


    public LikeEntity() {
    }

    public LikeEntity(Long id, Long userId, String placeName, String placeAddress, LocalDateTime likedAt) {
        this.id = id;
        this.userId = userId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.likedAt = likedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }

    @Override
    public String toString() {
        return "LikeEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", placeName='" + placeName + '\'' +
                ", placeAddress='" + placeAddress + '\'' +
                ", likedAt=" + likedAt +
                '}';
    }
}