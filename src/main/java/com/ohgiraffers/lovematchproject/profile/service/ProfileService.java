package com.ohgiraffers.lovematchproject.profile.service;

import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.model.entity.ProfileEntity;
import com.ohgiraffers.lovematchproject.profile.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO save(ProfileDTO profileDTO) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setProfileName(profileDTO.getProfileName());
        profileEntity.setProfileGender(profileDTO.getProfileGender());
        profileEntity.setProfileAge(profileDTO.getProfileAge());
        profileEntity.setProfileHeight(profileDTO.getProfileHeight());
        profileEntity.setProfileMBTI(profileDTO.getProfileMBTI());
        profileEntity.setProfileLocation(profileDTO.getProfileLocation());
        profileRepository.save(profileEntity);
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
