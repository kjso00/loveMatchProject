package com.ohgiraffers.lovematchproject.date.daterepository;

import com.ohgiraffers.lovematchproject.date.datemodel.dateentity.DateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DateRepository 인터페이스
 * 이 인터페이스는 DateEntity에 대한 데이터베이스 작업을 처리합니다.
 * JpaRepository를 상속받아 기본적인 CRUD 작업과 페이징, 정렬 기능을 제공합니다.
 */
@Repository
public interface DateRepository extends JpaRepository<DateEntity, Long> {

    /**
     * 카테고리별로 데이트 장소를 찾습니다.
     * @param category 검색할 카테고리
     * @return 해당 카테고리의 데이트 장소 목록
     */
    List<DateEntity> findByDateCategory(DateEntity.DateCategory category);

    /**
     * 서브 카테고리별로 데이트 장소를 찾습니다.
     * @param subCategory 검색할 서브 카테고리
     * @return 해당 서브 카테고리의 데이트 장소 목록
     */
    List<DateEntity> findByDateSubCategory(DateEntity.DateSubCategory subCategory);

    /**
     * 평균 평점이 지정된 값 이상인 데이트 장소를 찾습니다.
     * @param rating 최소 평균 평점
     * @return 조건을 만족하는 데이트 장소 목록
     */
    List<DateEntity> findByDateAverageRatingGreaterThanEqual(double rating);

    /**
     * 주어진 위치 근처의 데이트 장소를 찾습니다.
     * @param latitude 위도
     * @param longitude 경도
     * @param distance 검색 반경 (km)
     * @return 주어진 위치 근처의 데이트 장소 목록
     */
    @Query(value = "SELECT * FROM date d WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(d.date_latitude)) * " +
            "cos(radians(d.date_longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(d.date_latitude)))) < :distance",
            nativeQuery = true)
    List<DateEntity> findNearbyDates(@Param("latitude") double latitude,
                                     @Param("longitude") double longitude,
                                     @Param("distance") double distance);
}