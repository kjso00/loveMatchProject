package com.ohgiraffers.lovematchproject.profile.service;

import com.ohgiraffers.lovematchproject.login.model.entity.UserEntity;
import com.ohgiraffers.lovematchproject.login.repository.UserRepository;
import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.model.entity.ProfileEntity;
import com.ohgiraffers.lovematchproject.profile.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    public ProfileDTO save(ProfileDTO profileDTO, Long userId) {
        ProfileEntity profileEntity = new ProfileEntity();

        profileEntity.setUserId(userId); // user_id 설정
        profileEntity.setProfileName(profileDTO.getProfileName());
        profileEntity.setProfileGender(profileDTO.getProfileGender());
        profileEntity.setProfileAge(profileDTO.getProfileAge());
        profileEntity.setProfileHeight(profileDTO.getProfileHeight());
        profileEntity.setProfileMBTI(profileDTO.getProfileMBTI());
        profileEntity.setProfileLocation(profileDTO.getProfileLocation());

        // 프로필 저장
        ProfileEntity savedProfile = profileRepository.save(profileEntity);

        return convertToDTO(savedProfile);
        // 저장된 프로필을 DTO로 변환하여 반환
    }

    private ProfileDTO convertToDTO(ProfileEntity profileEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setProfileNo(profileEntity.getProfileNo());
        profileDTO.setProfileName(profileEntity.getProfileName());
        profileDTO.setProfileAge(profileEntity.getProfileAge());
        profileDTO.setProfileGender(profileEntity.getProfileGender());
        profileDTO.setProfileLocation(profileEntity.getProfileLocation());
        profileDTO.setProfileMBTI(profileEntity.getProfileMBTI());
        profileDTO.setProfileHeight(profileEntity.getProfileHeight());
        return profileDTO;
    }

    public List<ProfileDTO> findAll() {
        List<ProfileEntity> profileEntityList = profileRepository.findAll();
        List<ProfileDTO> profileDTOList = new ArrayList<>();
        for (ProfileEntity profileEntity : profileEntityList) {
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setProfileNo(profileEntity.getProfileNo());
            profileDTO.setProfileName(profileEntity.getProfileName());
            profileDTO.setProfileGender(profileEntity.getProfileGender());
            profileDTO.setProfileAge(profileEntity.getProfileAge());
            profileDTO.setProfileHeight(profileEntity.getProfileHeight());
            profileDTO.setProfileMBTI(profileEntity.getProfileMBTI());
            profileDTO.setProfileLocation(profileEntity.getProfileLocation());
            profileDTOList.add(profileDTO);
        }
        return profileDTOList;
    }

}
