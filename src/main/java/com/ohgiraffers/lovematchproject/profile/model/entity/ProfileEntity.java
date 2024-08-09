package com.ohgiraffers.lovematchproject.profile.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_no")
    public Long profileNo;

    @Column(name = "profileName")
    public String profileName;

    @Column(name = "profileGender")
    public String profileGender;

    @Column(name = "profileAge")
    public int profileAge;

    @Column(name = "profileHeight")
    public int profileHeight;

    @Column(name = "profileMBTI")
    public String profileMBTI;

    @Column(name = "profileLocation")
    public String profileLocation;

    @Column(name = "user_id")
    public Long userId;

    public ProfileEntity() {
    }

    public ProfileEntity(Long profileNo, String profileName, String profileGender, int profileAge, int profileHeight, String profileMBTI, String profileLocation, Long userId) {
        this.profileNo = profileNo;
        this.profileName = profileName;
        this.profileGender = profileGender;
        this.profileAge = profileAge;
        this.profileHeight = profileHeight;
        this.profileMBTI = profileMBTI;
        this.profileLocation = profileLocation;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProfileEntity{" +
                "profileNo=" + profileNo +
                ", profileName='" + profileName + '\'' +
                ", profileGender='" + profileGender + '\'' +
                ", profileAge=" + profileAge +
                ", profileHeight=" + profileHeight +
                ", profileMBTI='" + profileMBTI + '\'' +
                ", profileLocation='" + profileLocation + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

}
