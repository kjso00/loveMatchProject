package com.ohgiraffers.lovematchproject.date.datemodel.dateentity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * DateEntity 클래스
 * 이 클래스는 데이트 장소에 대한 정보를 나타내는 엔티티입니다.
 * JPA를 사용하여 데이터베이스의 'date' 테이블과 매핑됩니다.
 * 각 필드는 데이트 장소의 특정 속성을 나타냅니다.
 */
@Entity
@Table(name = "date")
public class DateEntity {

    /**
     * 데이트 장소의 고유 식별자
     * 자동 생성되는 기본 키 입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateId;

    /**
     * 데이트 장소의 이름
     * null이 될 수 업슴
     */
    @Column(nullable = false)
    private String dateName;

    /**
     * 데이트 장소에 대한 상세 설명
     * 최대 1000자까지 저장할 수 있습니다.
     */
    @Column(length = 1000)
    private String dateDescription;

    /**
     * 데이트 장소의 주소
     * null이 될 수 없습니다.
     */
    @Column(nullable = false)
    private String dateAddress;

    /**
     * 데이트 장소의 위도
     */
    private double dateLatitude;

    /**
     * 데이트 장소의 경도
     */
    private double dateLongitude;

    /**
     * 데이트 장소의 이용 가격
     * 최대 10자리 숫자와 소수점 이하 2자리까지 저장할수있음
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal datePrice;

    /**
     * 데이트 장소의 대분류 카테고리
     * ENJOY(즐길거리), EAT(먹을거리), VIEW(볼거리) 중 하나의 값을 가진다
     */
    @Enumerated(EnumType.STRING)
    private DateCategory dateCategory;

    /**
     * 데이트 장소의 소분류 카테고리
     * 각 대분류에 따른 세부 카테고리 값을 가진다.
     */
    @Enumerated(EnumType.STRING)
    private DateSubCategory dateSubCategory;

    /**
     * 데이트 장소의 대표 이미지 URL
     */
    private String dateImageUrl;

    /**
     * 데이트 장소의 운영 시간
     */
    private String dateOperatingHours;

    /**
     * 데이트 장소의 평균 평점
     * 0.00에서 5.00 사이의 값을 가집니다.
     */
    @Column(precision = 3, scale = 2)
    private BigDecimal dateAverageRating;

    /**
     * 데이트 장소에 대한 리뷰 목록
     * 별도의 테이블에 저장되며, dateId와 조인됩니다.
     */
    @ElementCollection
    @CollectionTable(name = "date_reviews", joinColumns = @JoinColumn(name = "date_id"))
    @Column(name = "date_review")
    private List<String> dateReviews;

    public DateEntity() {}

    public DateEntity(Long dateId, String dateName, String dateDescription, String dateAddress,
                      double dateLatitude, double dateLongitude, BigDecimal datePrice, DateCategory dateCategory,
                      DateSubCategory dateSubCategory, String dateImageUrl, String dateOperatingHours,
                      BigDecimal dateAverageRating, List<String> dateReviews) {
        this.dateId = dateId;
        this.dateName = dateName;
        this.dateDescription = dateDescription;
        this.dateAddress = dateAddress;
        this.dateLatitude = dateLatitude;
        this.dateLongitude = dateLongitude;
        this.datePrice = datePrice;
        this.dateCategory = dateCategory;
        this.dateSubCategory = dateSubCategory;
        this.dateImageUrl = dateImageUrl;
        this.dateOperatingHours = dateOperatingHours;
        this.dateAverageRating = dateAverageRating;
        this.dateReviews = dateReviews;
    }


    // getter
    public Long getDateId() {return dateId;}

    public String getDateName() {return dateName;}

    public String getDateDescription() {return dateDescription;}

    public String getDateAddress() {return dateAddress;}

    public double getDateLatitude() {return dateLatitude;}

    public double getDateLongitude() {return dateLongitude;}

    public BigDecimal getDatePrice() {return datePrice;}

    public DateCategory getDateCategory() {return dateCategory;}

    public DateSubCategory getDateSubCategory() {return dateSubCategory;}

    public String getDateImageUrl() {return dateImageUrl;}

    public String getDateOperatingHours() {return dateOperatingHours;}

    public BigDecimal getDateAverageRating() {return dateAverageRating;}

    public List<String> getDateReviews() {return dateReviews;}


    //setter

    public void setDateId(Long dateId) {this.dateId = dateId;}

    public void setDateName(String dateName) {this.dateName = dateName;}

    public void setDateDescription(String dateDescription) {this.dateDescription = dateDescription;}

    public void setDateAddress(String dateAddress) {this.dateAddress = dateAddress;}

    public void setDateLatitude(double dateLatitude) {this.dateLatitude = dateLatitude;}

    public void setDateLongitude(double dateLongitude) {this.dateLongitude = dateLongitude;}

    public void setDatePrice(BigDecimal datePrice) {this.datePrice = datePrice;}

    public void setDateCategory(DateCategory dateCategory) {this.dateCategory = dateCategory;}

    public void setDateSubCategory(DateSubCategory dateSubCategory) {this.dateSubCategory = dateSubCategory;}

    public void setDateImageUrl(String dateImageUrl) {this.dateImageUrl = dateImageUrl;}

    public void setDateOperatingHours(String dateOperatingHours) {this.dateOperatingHours = dateOperatingHours;}

    public void setDateAverageRating(BigDecimal dateAverageRating) {this.dateAverageRating = dateAverageRating;}

    public void setDateReviews(List<String> dateReviews) {this.dateReviews = dateReviews;}


    /**
     * 데이트 장소의 대분류 카테고리를 나타내는 열거형
     */
    public enum DateCategory {
        ENJOY,  // 즐길거리
        EAT,    // 먹을거리
        VIEW    // 볼거리
    }

    /**
     * 데이트 장소의 소분류 카테고리를 나타내는 열거형
     */
    public enum DateSubCategory {
        THEME_PARK,    // 테마파크 (ENJOY 카테고리)
        ONE_DAY_CLASS, // 원데이클래스 (ENJOY 카테고리)
        FORTUNE,       // 사주/타로 (ENJOY 카테고리)
        KOREAN,        // 한식 (EAT 카테고리)
        FOREIGN,       // 일식&양식 (EAT 카테고리)
        CAFE,          // 카페 (EAT 카테고리)
        PLAY,          // 연극 (VIEW 카테고리)
        PERFORMANCE,   // 공연 (VIEW 카테고리)
        EXHIBITION     // 전시회 (VIEW 카테고리)
    }
}