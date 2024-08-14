package com.ohgiraffers.lovematchproject.date.daterepository;

import com.ohgiraffers.lovematchproject.date.datemodel.dateentity.DateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<DateEntity, Long> {

    // 특정 사용자의 모든 데이트 조회
    List<DateEntity> findByUserId(Long userId);

    // 특정 장소에서의 데이트 조회
    List<DateEntity> findByPlaceName(String placeName);

}