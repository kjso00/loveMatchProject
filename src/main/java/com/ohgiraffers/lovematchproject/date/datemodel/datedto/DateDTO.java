package com.ohgiraffers.lovematchproject.date.datemodel.datedto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DateDTO 클래스
 * 이 클래스는 데이트 장소 정보를 클라이언트와 서버 간에 전송하기 위한 데이터 전송 객체
 * DateEntity와 유사하지만, 클라이언트에게 필요한 정보만을 포함하고 있음.
 */
public class DateDTO {

    // 고유 식별자
    private Long userId;

    // 장소 이름
    private String placeName;

    // 장소 주소
    private String placeAddress;

    // 시간
    private LocalDateTime dateTime;


// 데이트 관련 메모

    private String notes;

    public DateDTO() {
    }

    public DateDTO(Long userId, String placeName, String placeAddress, LocalDateTime dateTime, String notes) {
        this.userId = userId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.dateTime = dateTime;
        this.notes = notes;
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
        return "DateDTO{" +
                "userId=" + userId +
                ", placeName='" + placeName + '\'' +
                ", placeAddress='" + placeAddress + '\'' +
                ", dateTime=" + dateTime +
                ", notes='" + notes + '\'' +
                '}';
    }
}

