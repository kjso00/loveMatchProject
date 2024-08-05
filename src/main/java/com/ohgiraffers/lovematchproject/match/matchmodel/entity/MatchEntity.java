package com.ohgiraffers.lovematchproject.match.matchmodel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profile")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profiId")
    private Long profiId;

    @Column(name = "profiName")
    private String profiName;
    @Column(name = "profiGender")
    private String profiGender;
    @Column(name = "profiAge")
    private int profiAge;
    @Column(name = "profiHeight")
    private int profiHeight;
    @Column(name = "profiMBTI")
    private String profiMbti;
    @Column(name = "profiLocation")
    private String profiLocation;

    public MatchEntity() {
    }

    public MatchEntity(Long profiId, String profiName, String profiGender, int profiAge, int profiHeight, String profiMbti, String profiLocation) {
        this.profiId = profiId;
        this.profiName = profiName;
        this.profiGender = profiGender;
        this.profiAge = profiAge;
        this.profiHeight = profiHeight;
        this.profiMbti = profiMbti;
        this.profiLocation = profiLocation;
    }

    public Long getProfiId() {
        return profiId;
    }

    public void setProfiId(Long profiId) {
        this.profiId = profiId;
    }

    public String getProfiName() {
        return profiName;
    }

    public void setProfiName(String profiName) {
        this.profiName = profiName;
    }

    public String getProfiGender() {
        return profiGender;
    }

    public void setProfiGender(String profiGender) {
        this.profiGender = profiGender;
    }

    public int getProfiAge() {
        return profiAge;
    }

    public void setProfiAge(int profiAge) {
        this.profiAge = profiAge;
    }

    public int getProfiHeight() {
        return profiHeight;
    }

    public void setProfiHeight(int profiHeight) {
        this.profiHeight = profiHeight;
    }

    public String getProfiMbti() {
        return profiMbti;
    }

    public void setProfiMbti(String profiMbti) {
        this.profiMbti = profiMbti;
    }

    public String getProfiLocation() {
        return profiLocation;
    }

    public void setProfiLocation(String profiLocation) {
        this.profiLocation = profiLocation;
    }

    @Override
    public String toString() {
        return "MatchEntity{" +
                "profiId=" + profiId +
                ", profiName='" + profiName + '\'' +
                ", profiGender='" + profiGender + '\'' +
                ", profiAge=" + profiAge +
                ", profiHeight=" + profiHeight +
                ", profiMbti='" + profiMbti + '\'' +
                ", profiLocation='" + profiLocation + '\'' +
                '}';
    }
}
