package com.ohgiraffers.lovematchproject.match.matchservice;

import com.ohgiraffers.lovematchproject.match.matchmodel.dto.MatchDTO;
import com.ohgiraffers.lovematchproject.match.matchmodel.entity.MatchEntity;
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

    public MatchDTO getLoginUser(Long profiId) {
        MatchEntity entity = matchRepository.findById(profiId).orElse(null);
        if (entity == null) {
            throw new IllegalArgumentException("User not found with ID: " + profiId);
        }
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setProfiId(entity.getProfiId());
        matchDTO.setProfiGender(entity.getProfiGender());
        matchDTO.setProfiName(entity.getProfiName());
        matchDTO.setProfiAge(entity.getProfiAge());
        matchDTO.setProfiHeight(entity.getProfiHeight());
        matchDTO.setProfiMbti(entity.getProfiMbti());
        matchDTO.setProfiLocation(entity.getProfiLocation());
        return matchDTO;
    }


    public List<MatchDTO> getFilteringGender(long loginUserId) {
        MatchDTO loginUser = getLoginUser(loginUserId);
        String targetGender = loginUser.getProfiGender().equalsIgnoreCase("여") ? "남" : "여";
        // 여성이면 남성을, 남성이면 여성을 타겟으로 설정

        List<MatchEntity> entities = matchRepository.findAll();
        List<MatchDTO> matchDTOList = new ArrayList<>();
        for (MatchEntity entity : entities) {
            if (!entity.getProfiId().equals(loginUserId) && entity.getProfiGender().equalsIgnoreCase(targetGender)) {
                MatchDTO matchDTO = new MatchDTO();
                matchDTO.setProfiId(entity.getProfiId());
                matchDTO.setProfiGender(entity.getProfiGender());
                matchDTO.setProfiName(entity.getProfiName());
                matchDTO.setProfiAge(entity.getProfiAge());
                matchDTO.setProfiHeight(entity.getProfiHeight());
                matchDTO.setProfiMbti(entity.getProfiMbti());
                matchDTO.setProfiLocation(entity.getProfiLocation());
                matchDTOList.add(matchDTO);
            }
        }
        return matchDTOList;
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
    public List<MatchDTO> calculatematchScores(Long profiId){
        List<MatchEntity> allProfiles = matchRepository.findAll();
        MatchEntity userProfile = matchRepository.findById(profiId).orElse(null);

        if(userProfile == null){
            throw new IllegalArgumentException("User profile not found");
        }

        String targetGender = userProfile.getProfiGender().equalsIgnoreCase("여") ? "남" : "여";

        List<MatchDTO> matchScores = new ArrayList<>();
        for (MatchEntity profile : allProfiles){
            if (!profile.getProfiId().equals(userProfile.getProfiId()) && profile.getProfiGender().equalsIgnoreCase(targetGender)) {

                int heightScore = calculateHeightScore(userProfile.getProfiGender(), profile.getProfiHeight());
                int ageScore = calculateAgeScore(userProfile.getProfiAge(), profile.getProfiAge());
                int locationScore = calculateDistanceScore(userProfile.getProfiLocation(), profile.getProfiLocation());

                int totalScore = heightScore + ageScore + locationScore;


                MatchDTO matchDTO = new MatchDTO();
                matchDTO.setProfiId(profile.getProfiId());
                matchDTO.setProfiGender(profile.getProfiGender());
                matchDTO.setProfiName(profile.getProfiName());
                matchDTO.setProfiAge(profile.getProfiAge());
                matchDTO.setProfiHeight(profile.getProfiHeight());
                matchDTO.setProfiMbti(profile.getProfiMbti());
                matchDTO.setProfiLocation(profile.getProfiLocation());
                matchDTO.setTotalScore(totalScore);
                matchScores.add(matchDTO);
            }
        }
        matchScores.sort(Comparator.comparingInt(MatchDTO::getTotalScore).reversed());
        return matchScores;
    }
}
