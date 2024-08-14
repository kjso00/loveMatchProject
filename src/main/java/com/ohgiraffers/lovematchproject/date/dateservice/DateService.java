package com.ohgiraffers.lovematchproject.date.dateservice;


import com.ohgiraffers.lovematchproject.date.datemodel.dateentity.DateEntity;
import com.ohgiraffers.lovematchproject.date.daterepository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DateService {

    @Autowired
    private DateRepository dateRepository;

    // 데이트 추가
    @Transactional
    public DateEntity addDate(Long userId, String placeName, String placeAddress, LocalDateTime dateTime, String notes) {
        DateEntity date = new DateEntity();
        date.setUserId(userId);
        date.setPlaceName(placeName);
        date.setPlaceAddress(placeAddress);
        date.setDateTime(dateTime);
        date.setNotes(notes);

        return dateRepository.save(date);
    }

    // 사용자의 모든 데이트 조회
    @Transactional
    public List<DateEntity> getUserDates(Long userId) {

        return dateRepository.findByUserId(userId);
    }

    // 특정 장소에서의 데이트 조회
    @Transactional
    public List<DateEntity> getDatesByPlace(String placeName) {

        return dateRepository.findByPlaceName(placeName);
    }
}