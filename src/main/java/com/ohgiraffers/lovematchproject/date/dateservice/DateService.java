package com.ohgiraffers.lovematchproject.date.dateservice;

import com.ohgiraffers.lovematchproject.date.datemodel.datedto.DateDTO;
import com.ohgiraffers.lovematchproject.date.datemodel.dateentity.DateEntity;
import com.ohgiraffers.lovematchproject.date.daterepository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DateService 클래스
 * 이 클래스는 데이트 관련 비즈니스 로직을 처리합니다.
 * DateRepository를 사용하여 데이터베이스 작업을 수행하고,
 * 필요한 경우 DateEntity와 DateDTO 간의 변환을 처리함.
 */
@Service
public class DateService {

    private final DateRepository dateRepository;
    private final RestTemplate restTemplate;

    @Value("${api.culture.key}")
    private String cultureApiKey;

    @Value("${api.culture2.key}")
    private String culture2ApiKey;

    @Value("${api.kakao.key}")
    private String kakaoApiKey;

    @Autowired
    public DateService(DateRepository dateRepository, RestTemplate restTemplate) {
        this.dateRepository = dateRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * 모든 데이트 장소를 조회합니다.
     * @return 모든 데이트 장소의 DTO 리스트
     */
    public List<DateDTO> getAllDates() {
        return dateRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * ID로 특정 데이트 장소를 조회합니다.
     * @param id 조회할 데이트 장소의 ID
     * @return 조회된 데이트 장소의 DTO, 없으면 null
     */
    public DateDTO getDateById(Long id) {
        return dateRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    /**
     * 새로운 데이트 장소를 저장합니다.
     * @param dateDTO 저장할 데이트 장소 정보
     * @return 저장된 데이트 장소의 DTO
     */
    @Transactional
    public DateDTO saveDate(DateDTO dateDTO) {
        DateEntity dateEntity = convertToEntity(dateDTO);
        DateEntity savedEntity = dateRepository.save(dateEntity);
        return convertToDTO(savedEntity);
    }

    /**
     * 기존 데이트 장소 정보를 업데이트합니다.
     * @param id 업데이트할 데이트 장소의 ID
     * @param dateDTO 업데이트할 정보
     * @return 업데이트된 데이트 장소의 DTO, 해당 ID의 장소가 없으면 null
     */
    @Transactional
    public DateDTO updateDate(Long id, DateDTO dateDTO) {
        return dateRepository.findById(id)
                .map(existingDate -> {
                    // 업데이트 로직
                    existingDate.setDateName(dateDTO.getDateName());
                    // ... 다른 필드들도 업데이트
                    return convertToDTO(dateRepository.save(existingDate));
                })
                .orElse(null);
    }

    /**
     * 데이트 장소를 삭제합니다.
     * @param id 삭제할 데이트 장소의 ID
     */
    @Transactional
    public void deleteDate(Long id) {
        dateRepository.deleteById(id);
    }

    /**
     * 주어진 위치 근처의 데이트 장소를 찾습니다.
     * @param latitude 위도
     * @param longitude 경도
     * @param distance 검색 반경 (km)
     * @return 근처의 데이트 장소 DTO 리스트
     */
    public List<DateDTO> getNearbyDates(double latitude, double longitude, double distance) {
        return dateRepository.findNearbyDates(latitude, longitude, distance).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //API

    /**
     * 문화 활동 정보를 가져옵니다.
     * @return 문화 활동 정보 목록
     */
    public List<Map<String, Object>> getCultureActivities() {
        String url = "https://www.culture.go.kr/openapi/rest/publicperformancedisplays/area" +
                "?serviceKey=" + cultureApiKey +
                "&sido=서울&gugun=종로구&from=20230101&to=20231231&cPage=1&rows=10&sortStdr=1";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        // API 응답 구조에 따라 적절히 파싱
        return (List<Map<String, Object>>) response.getBody().get("items");
    }

    /**
     * 공연 정보를 가져옵니다.
     * @return 공연 정보 목록
     */
    public List<Map<String, Object>> getPerformances() {
        String url = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period" +
                "?serviceKey=" + culture2ApiKey +
                "&from=20230101&to=20231231&cPage=1&rows=10&sortStdr=1";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        // API 응답 구조에 따라 적절히 파싱
        return (List<Map<String, Object>>) response.getBody().get("items");
    }

    /**
     * 주변 식당 정보를 가져옵니다.
     * @param latitude 위도
     * @param longitude 경도
     * @return 식당 정보 목록
     */
    public List<Map<String, Object>> getRestaurants(double latitude, double longitude) {
        String url = "https://dapi.kakao.com/v2/local/search/category.json" +
                "?category_group_code=FD6&x=" + longitude + "&y=" + latitude + "&radius=5000";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        // API 응답 구조에 따라 적절히 파싱
        return (List<Map<String, Object>>) response.getBody().get("documents");
    }

    /**
     * 카테고리별 데이트 장소 정보를 가져옵니다.
     * @param category 카테고리 (enjoy, view, eat)
     * @param latitude 위도 (선택적)
     * @param longitude 경도 (선택적)
     * @return 카테고리별 데이트 장소 정보 목록
     */
    public List<Map<String, Object>> getDatesByCategory(String category, Double latitude, Double longitude) {
        switch (category) {
            case "enjoy":
                return getCultureActivities();
            case "view":
                return getPerformances();
            case "eat":
                if (latitude != null && longitude != null) {
                    return getRestaurants(latitude, longitude);
                } else {
                    throw new IllegalArgumentException("Latitude and longitude are required for restaurants");
                }
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    //API


    /**
     * DateEntity를 DateDTO로 변환합니다.
     */
    private DateDTO convertToDTO(DateEntity dateEntity) {
        // 변환 로직
        return new DateDTO(/* 필드 매핑 */);
    }

    /**
     * DateDTO를 DateEntity로 변환합니다.
     */
    private DateEntity convertToEntity(DateDTO dateDTO) {
        // 변환 로직
        return new DateEntity(/* 필드 매핑 */);
    }


}