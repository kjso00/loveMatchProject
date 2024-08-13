package com.ohgiraffers.lovematchproject.date.datemodel.dateentity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "dates")
public class DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 데이트 고유 식별자

    @Column(nullable = false)
    private Long userId;  // 사용자 ID

    @Column(nullable = false)
    private String placeName;  // 장소 이름

    @Column(nullable = false)
    private String placeAddress;  // 장소 주소

    @Column(nullable = false)
    private LocalDateTime dateTime;  // 데이트 시간

    @Column(length = 1000)
    private String notes;  // 데이트 관련 메모

    // 생성자, getter, setter 메서드


    public DateEntity(Long id, Long userId, String placeName, String placeAddress, LocalDateTime dateTime, String notes) {
        this.id = id;
        this.userId = userId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.dateTime = dateTime;
        this.notes = notes;
    }

    public DateEntity() {

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "DateEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", placeName='" + placeName + '\'' +
                ", placeAddress='" + placeAddress + '\'' +
                ", dateTime=" + dateTime +
                ", notes='" + notes + '\'' +
                '}';
    }
}