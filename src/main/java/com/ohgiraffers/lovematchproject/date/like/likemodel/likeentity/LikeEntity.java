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
    private Long id;

    private Long userId;

    private String placeName;

    private String placeAddress;

    private LocalDateTime likedAt;

    public LikeEntity() {
        this.likedAt = LocalDateTime.now();  // 기본값으로 현재 시간을 설정
    }

    public LikeEntity(Long userId, String placeName, String placeAddress) {
        this.userId = userId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.likedAt = LocalDateTime.now();  // 기본값으로 현재 시간을 설정
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

