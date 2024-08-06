package com.ohgiraffers.lovematchproject.profile.model.dto;

public class ProfileDTO {

    public int profileNo; // 폼에서 입력x

    public String profileName; // 실명 인증 받아야 하는데
    // private String profileEmail; 이메일 인증 받아야 하는데
    // SNS 로그인 받으면 인증정보가 어디까지 연결되는거지?
    // 이름, 비밀번호, 이메일.. 은 자동으로 실명 인증된거 아닌가? 흐음..

    public String profileGender;
    public int profileAge;
    public int profileHeight;

    public String profileMBTI;
    public String profileLocation;

    public int totalScore; // 폼에서 입력x

    public ProfileDTO() {
    }

    public ProfileDTO(int profileNo, String profileName, String profileGender, int profileAge, int profileHeight, String profileMBTI, String profileLocation, int totalScore) {
        this.profileNo = profileNo;
        this.profileName = profileName;
        this.profileGender = profileGender;
        this.profileAge = profileAge;
        this.profileHeight = profileHeight;
        this.profileMBTI = profileMBTI;
        this.profileLocation = profileLocation;
        this.totalScore = totalScore;
    }

    public int getProfileNo() {
        return profileNo;
    }

    public void setProfileNo(int profileNo) {
        this.profileNo = profileNo;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
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

    public String getProfileMBTI() {
        return profileMBTI;
    }

    public void setProfileMBTI(String profileMBTI) {
        this.profileMBTI = profileMBTI;
    }

    public String getProfileLocation() {
        return profileLocation;
    }

    public void setProfileLocation(String profileLocation) {
        this.profileLocation = profileLocation;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "profileNo=" + profileNo +
                ", profileName='" + profileName + '\'' +
                ", profileGender='" + profileGender + '\'' +
                ", profileAge=" + profileAge +
                ", profileHeight=" + profileHeight +
                ", profileMBTI='" + profileMBTI + '\'' +
                ", profileLocation='" + profileLocation + '\'' +
                ", totalScore=" + totalScore +
                '}';
    }
}
