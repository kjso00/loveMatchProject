package com.ohgiraffers.lovematchproject.match.matchmodel.dto;

public class MatchDTO {

    private Long id;
    private String userName;
    private String gender;
    private int userAge;
    private int height;
    private String mbti;
    private String location;

    public MatchDTO() {
    }

    public MatchDTO(Long id, String userName, String gender, int userAge, int height, String mbti, String location) {
        this.id = id;
        this.userName = userName;
        this.gender = gender;
        this.userAge = userAge;
        this.height = height;
        this.mbti = mbti;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", userAge=" + userAge +
                ", height=" + height +
                ", mbti='" + mbti + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
