package com.ohgiraffers.lovematchproject.date.dateconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * DateConfig 클래스
 * 이 클래스는 Date 도메인과 관련된 Spring 설정을 담당합니다.
 * 빈(Bean) 설정이나 기타 구성 요소들을 이곳에서 정의합니다.
 */
@Configuration
public class DateConfig {

    /**
     * RestTemplate 빈을 생성합니다.
     * 이 빈은 외부 API와의 통신에 사용될 수 있습니다.
     *
     * @return 구성된 RestTemplate 객체
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 데이트 장소 검색 반경의 기본값을 설정합니다.
     *
     * @return 기본 검색 반경 (km 단위)
     */
    @Bean
    public double defaultSearchRadius() {
        return 5.0; // 기본 검색 반경을 5km로 설정
    }

    /**
     * 데이트 장소 리뷰의 최대 길이를 설정합니다.
     *
     * @return 리뷰의 최대 길이
     */
    @Bean
    public int maxReviewLength() {
        return 500; // 리뷰 최대 길이를 500자로 제한
    }

    // 필요에 따라 추가적인 빈이나 설정을 이곳에 정의할 수 있습니다.
}