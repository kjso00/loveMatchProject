package com.ohgiraffers.lovematchproject.match.matchservice;

import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.model.entity.ProfileEntity;
import com.ohgiraffers.lovematchproject.match.matchrepository.MatchRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final KakaoMapService kakaoMapService;

    @Autowired
    public MatchService(MatchRepository matchRepository, KakaoMapService kakaoMapService) {
        this.matchRepository = matchRepository;
        this.kakaoMapService = kakaoMapService;
    }

    public ProfileDTO getLoginUser(Long ProfileNo) {
        ProfileEntity entity = matchRepository.findById(ProfileNo).orElse(null);
        if (entity == null) {
            throw new IllegalArgumentException("User not found with ID: " + ProfileNo);
        }
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setProfileNo(entity.getProfileNo());
        profileDTO.setProfileGender(entity.getProfileGender());
        profileDTO.setProfileName(entity.getProfileName());
        profileDTO.setProfileAge(entity.getProfileAge());
        profileDTO.setProfileHeight(entity.getProfileHeight());
        profileDTO.setProfileMBTI(entity.getProfileMBTI());
        profileDTO.setProfileLocation(entity.getProfileLocation());
        return profileDTO;
    }


    public List<ProfileDTO> getFilteringGender(long loginUserId) {
        ProfileDTO loginUser = getLoginUser(loginUserId);
        String targetGender = loginUser.getProfileGender().equalsIgnoreCase("여") ? "남" : "여";
        // 여성이면 남성을, 남성이면 여성을 타겟으로 설정

        List<ProfileEntity> entities = matchRepository.findAll();
        List<ProfileDTO> profileDTOList = new ArrayList<>();
        for (ProfileEntity entity : entities) {
            if (!entity.getProfileNo().equals(loginUserId) && entity.getProfileGender().equalsIgnoreCase(targetGender)) {
                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setProfileNo(entity.getProfileNo());
                profileDTO.setProfileGender(entity.getProfileGender());
                profileDTO.setProfileName(entity.getProfileName());
                profileDTO.setProfileAge(entity.getProfileAge());
                profileDTO.setProfileHeight(entity.getProfileHeight());
                profileDTO.setProfileMBTI(entity.getProfileMBTI());
                profileDTO.setProfileLocation(entity.getProfileLocation());
                profileDTOList.add(profileDTO);
            }
        }
        return profileDTOList;
    }


    //키 점수계산 함수
    private int calculateHeightScore(String gender, int height){
        if (gender.equalsIgnoreCase("여")) {
            if (height >= 180) return 10;
            if (height >= 178) return 8;
            if (height >= 176) return 6;
            if (height >= 173) return 4;
            if (height >= 170) return 2;
            return 0;
        } else if (gender.equalsIgnoreCase("남")) {
            if (height <= 152) return 0;
            if (height <= 154) return 2;
            if (height <= 157) return 4;
            if (height <= 160) return 6;
            if (height <= 163) return 8;
            if (height <= 167) return 10;
            if (height <= 170) return 8;
            if (height <= 172) return 6;
            if (height <= 174) return 4;
            if (height <= 177) return 2;
            return 0;
        }
        return 0;
    }

    // 나이 점수 계산 함수
    private int calculateAgeScore(int age1, int age2) {
        int ageDifference = Math.abs(age1 - age2);
        if (ageDifference <= 2) return 10;
        if (ageDifference <= 4) return 8;
        if (ageDifference <= 6) return 6;
        if (ageDifference <= 8) return 4;
        if (ageDifference <= 10) return 2;
        return 0;
    }


    // 거리 점수 계산 함수
    public int calculateDistanceScore(String location1, String location2) {
        if (location1 == null || location2 == null || location1.isEmpty() || location2.isEmpty()) {
            System.out.println("Invalid location data: " + location1 + ", " + location2);
        }

        // 좌표 데이터를 저장하기 위한 JSON 객체 생성
        JSONObject coord1 = kakaoMapService.getCoordinates(location1);
        JSONObject coord2 = kakaoMapService.getCoordinates(location2);

        if (coord1 == null || coord2 == null) {
            return 0; // 좌표를 찾을 수 없는 경우 0점 처리
        }

        // 좌표로부터 거리를 계산
        double distance = kakaoMapService.calculateDistance(coord1, coord2) / 1000.0; // m를 km로 변환

        // 거리 점수 계산
        if (distance < 10) return 10;
        else if (distance < 20) return 8;
        else if (distance < 30) return 6;
        else if (distance < 40) return 4;
        else if (distance < 50) return 2;
        else return 0;
    }


    // 토탈스코어 계산 함수
    public List<ProfileDTO> calculatematchScores(Long ProfileNo){
        List<ProfileEntity> allProfiles = matchRepository.findAll();
        ProfileEntity userProfile = matchRepository.findById(ProfileNo).orElse(null);

        if(userProfile == null){
            throw new IllegalArgumentException("User profile not found");
        }

        String targetGender = userProfile.getProfileGender().equalsIgnoreCase("여") ? "남" : "여";

        List<ProfileDTO> matchScores = new ArrayList<>();
        for (ProfileEntity profile : allProfiles){
            if (!profile.getProfileNo().equals(userProfile.getProfileNo()) && profile.getProfileGender().equalsIgnoreCase(targetGender)) {

                int heightScore = calculateHeightScore(userProfile.getProfileGender(), profile.getProfileHeight());
                int ageScore = calculateAgeScore(userProfile.getProfileAge(), profile.getProfileAge());
                int locationScore = calculateDistanceScore(userProfile.getProfileLocation(), profile.getProfileLocation());

                int totalScore = heightScore + ageScore + locationScore;


                ProfileDTO profileDTO = new ProfileDTO();
                profileDTO.setProfileNo(profile.getProfileNo());
                profileDTO.setProfileGender(profile.getProfileGender());
                profileDTO.setProfileName(profile.getProfileName());
                profileDTO.setProfileAge(profile.getProfileAge());
                profileDTO.setProfileHeight(profile.getProfileHeight());
                profileDTO.setProfileMBTI(profile.getProfileMBTI());
                profileDTO.setProfileLocation(profile.getProfileLocation());
                profileDTO.setTotalScore(totalScore);
                matchScores.add(profileDTO);
            }
        }
        matchScores.sort(Comparator.comparingInt(ProfileDTO::getTotalScore).reversed());
        return matchScores;
    }
}
