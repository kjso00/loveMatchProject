package com.ohgiraffers.lovematchproject.date.like.likemodel.likedto;

import java.time.LocalDateTime;

public class LikeDTO {
    private Long id;
    private Long userId;
    private String placeName;
    private String placeAddress;
    private LocalDateTime likedAt;

    public LikeDTO() {
    }

    public LikeDTO(Long id, Long userId, String placeName, String placeAddress, LocalDateTime likedAt) {
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
        return "LikeDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", placeName='" + placeName + '\'' +
                ", placeAddress='" + placeAddress + '\'' +
                ", likedAt=" + likedAt +
                '}';
    }
}