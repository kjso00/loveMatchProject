package com.ohgiraffers.lovematchproject.match.matchmodel.dto;

public class MatchDTO {

    private Long id;
    private String profiName;
    private String profiGender;
    private int profiAge;
    private int profiHeight;
    private String profiMbti;
    private String profiLocation;
    private int totalScore; // 추가된 속성

    public MatchDTO() {
    }

    public MatchDTO(Long id, String profiName, String profiGender, int profiAge, int profiHeight, String profiMbti, String profiLocation) {
        this.id = id;
        this.profiName = profiName;
        this.profiGender = profiGender;
        this.profiAge = profiAge;
        this.profiHeight = profiHeight;
        this.profiMbti = profiMbti;
        this.profiLocation = profiLocation;
        this.totalScore = totalScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "id=" + id +
                ", profiName='" + profiName + '\'' +
                ", profiGender='" + profiGender + '\'' +
                ", profiAge=" + profiAge +
                ", profiHeight=" + profiHeight +
                ", profiMbti='" + profiMbti + '\'' +
                ", profiLocation='" + profiLocation + '\'' +
                ", totalScore=" + totalScore +
                '}';
    }
}
