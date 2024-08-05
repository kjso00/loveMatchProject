package com.ohgiraffers.lovematchproject.date.datemodel.datedto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DateDTO 클래스
 * 이 클래스는 데이트 장소 정보를 클라이언트와 서버 간에 전송하기 위한 데이터 전송 객체입니다.
 * DateEntity와 유사하지만, 클라이언트에게 필요한 정보만을 포함하고 있습니다.
 */
public class DateDTO {

    /**
     * 데이트 장소의 고유 식별자
     */
    private Long dateId;

    /**
     * 데이트 장소의 이름
     */
    private String dateName;

    /**
     * 데이트 장소에 대한 상세 설명
     */
    private String dateDescription;

    /**
     * 데이트 장소의 주소
     */
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
     */
    private BigDecimal datePrice;

    /**
     * 데이트 장소의 대분류 카테고리
     */
    private String dateCategory;

    /**
     * 데이트 장소의 소분류 카테고리
     */
    private String dateSubCategory;

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
     */
    private double dateAverageRating;

    /**
     * 데이트 장소에 대한 리뷰 목록
     */
    private List<String> dateReviews;

    // 기본 생성자
    public DateDTO() {}

    // 모든 필드를 포함하는 생성자
    public DateDTO(Long dateId, String dateName, String dateDescription, String dateAddress,
                   double dateLatitude, double dateLongitude, BigDecimal datePrice,
                   String dateCategory, String dateSubCategory, String dateImageUrl,
                   String dateOperatingHours, double dateAverageRating, List<String> dateReviews) {
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

    // Getter


    public Long getDateId() {
        return dateId;
    }

    public String getDateName() {
        return dateName;
    }

    public String getDateDescription() {
        return dateDescription;
    }

    public String getDateAddress() {
        return dateAddress;
    }

    public double getDateLatitude() {
        return dateLatitude;
    }

    public double getDateLongitude() {
        return dateLongitude;
    }

    public BigDecimal getDatePrice() {
        return datePrice;
    }

    public String getDateCategory() {
        return dateCategory;
    }

    public String getDateSubCategory() {
        return dateSubCategory;
    }

    public String getDateImageUrl() {
        return dateImageUrl;
    }

    public String getDateOperatingHours() {
        return dateOperatingHours;
    }

    public double getDateAverageRating() {
        return dateAverageRating;
    }

    public List<String> getDateReviews() {
        return dateReviews;
    }

    //setter


    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    public void setDateDescription(String dateDescription) {
        this.dateDescription = dateDescription;
    }

    public void setDateAddress(String dateAddress) {
        this.dateAddress = dateAddress;
    }

    public void setDateLatitude(double dateLatitude) {
        this.dateLatitude = dateLatitude;
    }

    public void setDateLongitude(double dateLongitude) {
        this.dateLongitude = dateLongitude;
    }

    public void setDatePrice(BigDecimal datePrice) {
        this.datePrice = datePrice;
    }

    public void setDateCategory(String dateCategory) {
        this.dateCategory = dateCategory;
    }

    public void setDateSubCategory(String dateSubCategory) {
        this.dateSubCategory = dateSubCategory;
    }

    public void setDateImageUrl(String dateImageUrl) {
        this.dateImageUrl = dateImageUrl;
    }

    public void setDateOperatingHours(String dateOperatingHours) {
        this.dateOperatingHours = dateOperatingHours;
    }

    public void setDateAverageRating(double dateAverageRating) {
        this.dateAverageRating = dateAverageRating;
    }

    public void setDateReviews(List<String> dateReviews) {
        this.dateReviews = dateReviews;
    }

    /**
     * 객체의 문자열 표현을 반환합니다.
     * @return 데이트 장소의 주요 정보를 포함한 문자열
     */
    @Override
    public String toString() {
        return "DateDTO{" +
                "dateId=" + dateId +
                ", dateName='" + dateName + '\'' +
                ", dateCategory='" + dateCategory + '\'' +
                ", dateSubCategory='" + dateSubCategory + '\'' +
                ", dateAverageRating=" + dateAverageRating +
                '}';
    }
}