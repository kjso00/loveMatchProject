package com.ohgiraffers.lovematchproject.profile.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProfileDTO {

    private Long userId;
    private Long profileNo; // 폼에서 입력x

    private String profileName; // 이름
    private String profilePassword; //비밀번호

    private String profileGender; // 성별
    private int profileAge; // 나이
    private int profileHeight; // 키
    private String profileLocation; // 위치

    private String profileMBTI; // MBTI

    private int totalScore; // 총 점수 폼에서 입력x

    private MultipartFile profileFile;
    private String originalFileName;
    private String storedFileName;

    public ProfileDTO() {
    }

    public ProfileDTO(Long userId, Long profileNo, String profileName, String profilePassword, String profileGender, int profileAge, int profileHeight, String profileLocation, String profileMBTI, int totalScore, MultipartFile profileFile, String originalFileName, String storedFileName) {
        this.userId = userId;
        this.profileNo = profileNo;
        this.profileName = profileName;
        this.profilePassword = profilePassword;
        this.profileGender = profileGender;
        this.profileAge = profileAge;
        this.profileHeight = profileHeight;
        this.profileLocation = profileLocation;
        this.profileMBTI = profileMBTI;
        this.totalScore = totalScore;
        this.profileFile = profileFile;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProfileNo() {
        return profileNo;
    }

    public void setProfileNo(Long profileNo) {
        this.profileNo = profileNo;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfilePassword() {
        return profilePassword;
    }

    public void setProfilePassword(String profilePassword) {
        this.profilePassword = profilePassword;
    }

    public String getProfileGender() {
        return profileGender;
    }

    public void setProfileGender(String profileGender) {
        this.profileGender = profileGender;
    }

    public int getProfileAge() {
        return profileAge;
    }

    public void setProfileAge(int profileAge) {
        this.profileAge = profileAge;
    }

    public int getProfileHeight() {
        return profileHeight;
    }

    public void setProfileHeight(int profileHeight) {
        this.profileHeight = profileHeight;
    }

    public String getProfileLocation() {
        return profileLocation;
    }

    public void setProfileLocation(String profileLocation) {
        this.profileLocation = profileLocation;
    }

    public String getProfileMBTI() {
        return profileMBTI;
    }

    public void setProfileMBTI(String profileMBTI) {
        this.profileMBTI = profileMBTI;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public MultipartFile getProfileFile() {
        return profileFile;
    }

    public void setProfileFile(MultipartFile profileFile) {
        this.profileFile = profileFile;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "userId=" + userId +
                ", profileNo=" + profileNo +
                ", profileName='" + profileName + '\'' +
                ", profilePassword='" + profilePassword + '\'' +
                ", profileGender='" + profileGender + '\'' +
                ", profileAge=" + profileAge +
                ", profileHeight=" + profileHeight +
                ", profileLocation='" + profileLocation + '\'' +
                ", profileMBTI='" + profileMBTI + '\'' +
                ", totalScore=" + totalScore +
                ", profileFile=" + profileFile +
                ", originalFileName='" + originalFileName + '\'' +
                ", storedFileName='" + storedFileName + '\'' +
                '}';
    }
}
