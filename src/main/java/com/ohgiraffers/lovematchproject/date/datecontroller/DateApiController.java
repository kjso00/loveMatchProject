package com.ohgiraffers.lovematchproject.date.datecontroller;

import com.ohgiraffers.lovematchproject.date.datemodel.datedto.DateDTO;
import com.ohgiraffers.lovematchproject.date.dateservice.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dates")
public class DateApiController {

    private final DateService dateService;

    @Autowired
    public DateApiController(DateService dateService) {
        this.dateService = dateService;
    }

    /**
     * 모든 데이트 장소를 조회
     * @return 모든 데이트 장소의 DTO 리스트
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DateDTO>> getAllDates() {
        List<DateDTO> dates = dateService.getAllDates();
        return ResponseEntity.ok(dates);
    }

    /**
     * ID로 특정 데이트 장소를 조회
     * @param id 조회할 데이트 장소의 ID
     * @return 조회된 데이트 장소의 DTO
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DateDTO> getDateById(@PathVariable Long id) {
        DateDTO date = dateService.getDateById(id);
        return date != null ? ResponseEntity.ok(date) : ResponseEntity.notFound().build();
    }

    /**
     * 새로운 데이트 장소를 생성합니다.
     * @param dateDTO 생성할 데이트 장소 정보
     * @return 생성된 데이트 장소의 DTO
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DateDTO> createDate(@RequestBody DateDTO dateDTO) {
        DateDTO createdDate = dateService.saveDate(dateDTO);
        return new ResponseEntity<>(createdDate, HttpStatus.CREATED);
    }

    /**
     * 기존 데이트 장소 정보를 업데이트
     * @param id 업데이트할 데이트 장소의 ID
     * @param dateDTO 업데이트할 정보
     * @return 업데이트된 데이트 장소의 DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DateDTO> updateDate(@PathVariable Long id, @RequestBody DateDTO dateDTO) {
        DateDTO updatedDate = dateService.updateDate(id, dateDTO);
        return updatedDate != null ? ResponseEntity.ok(updatedDate) : ResponseEntity.notFound().build();
    }

    /**
     * 데이트 장소를 삭제
     * @param id 삭제할 데이트 장소의 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteDate(@PathVariable Long id) {
        dateService.deleteDate(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 주변 데이트 장소를 조회
     * @param latitude 위도
     * @param longitude 경도
     * @param distance 검색 반경 (km)
     * @return 주변 데이트 장소 목록
     */
    @GetMapping("/nearby")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DateDTO>> getNearbyDates(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") double distance) {
        List<DateDTO> nearbyDates = dateService.getNearbyDates(latitude, longitude, distance);
        return ResponseEntity.ok(nearbyDates);
    }

    /**
     * 문화 활동 정보를 조회
     * @return 문화 활동 정보 목록
     */
    @GetMapping("/culture-activities")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Map<String, Object>>> getCultureActivities() {
        List<Map<String, Object>> activities = dateService.getCultureActivities();
        return ResponseEntity.ok(activities);
    }

    /**
     * 공연 정보를 조회
     * @return 공연 정보 목록
     */
    @GetMapping("/performances")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Map<String, Object>>> getPerformances() {
        List<Map<String, Object>> performances = dateService.getPerformances();
        return ResponseEntity.ok(performances);
    }

    /**
     * 주변 식당 정보를 조회
     * @param latitude 위도
     * @param longitude 경도
     * @return 식당 정보 목록
     */
    @GetMapping("/restaurants")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Map<String, Object>>> getRestaurants(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        List<Map<String, Object>> restaurants = dateService.getRestaurants(latitude, longitude);
        return ResponseEntity.ok(restaurants);
    }

    /**
     * 카테고리별 데이트 장소를 조회
     * @param category 카테고리
     * @param latitude 위도 (선택적)
     * @param longitude 경도 (선택적)
     * @return 카테고리별 데이트 장소 목록
     */
    @GetMapping("/category/{category}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Map<String, Object>>> getDatesByCategory(
            @PathVariable String category,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude) {
        List<Map<String, Object>> dates = dateService.getDatesByCategory(category, latitude, longitude);
        return ResponseEntity.ok(dates);
    }
}