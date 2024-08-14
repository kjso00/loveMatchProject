package com.ohgiraffers.lovematchproject.profile.service;

import com.ohgiraffers.lovematchproject.profile.model.dto.ProfileDTO;
import com.ohgiraffers.lovematchproject.profile.model.entity.ProfileEntity;
import com.ohgiraffers.lovematchproject.profile.model.entity.ProfileFileEntity;
import com.ohgiraffers.lovematchproject.profile.repository.ProfileFileRepository;
import com.ohgiraffers.lovematchproject.profile.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    public ProfileRepository profileRepository;

    @Autowired
    public ProfileFileRepository profileFileRepository;

    public ProfileDTO save(ProfileDTO profileDTO, Long userId) throws IOException {
        ProfileEntity profileEntity = toSaveFileEntity(profileDTO);

        MultipartFile profileFile = profileDTO.getProfileFile();
        if (profileFile != null && !profileFile.isEmpty()) {
            String originalFilename = profileFile.getOriginalFilename();
            String storedFilename = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/lovematch_img/" + storedFilename;

            // ProfileFileEntity 생성
            ProfileFileEntity profileFileEntity = new ProfileFileEntity();

            // 파일 저장
            profileFile.transferTo(new File(savePath));

            // ProfileEntity에 storedFileName 설정
            profileEntity.setStoredFileName(storedFilename);
            // user_id 설정
            profileEntity.setUserId(userId);

            // ProfileFileEntity 저장
            profileFileEntity.setOriginalFileName(originalFilename);
            profileFileEntity.setStoredFileName(storedFilename);
            profileFileEntity.setProfileEntity(profileEntity);

            profileEntity.getProfileEntities().add(profileFileEntity);
        }

        profileEntity = profileRepository.save(profileEntity);
//        return convertToDTO(savedProfile);
        return findById(profileEntity.getProfileNo());
    }

//    private ProfileDTO convertToDTO(ProfileEntity profileEntity) {
//        ProfileDTO profileDTO = new ProfileDTO();
//        profileDTO.setProfileNo(profileEntity.getProfileNo());
//        profileDTO.setProfileName(profileEntity.getProfileName());
//        profileDTO.setProfileAge(profileEntity.getProfileAge());
//        profileDTO.setProfileGender(profileEntity.getProfileGender());
//        profileDTO.setProfileLocation(profileEntity.getProfileLocation());
//        profileDTO.setProfileMBTI(profileEntity.getProfileMBTI());
//        profileDTO.setProfileHeight(profileEntity.getProfileHeight());
//        return profileDTO;
//    }

    public ProfileDTO findById(Long profileNo) {
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findById(profileNo);
        if(profileEntityOptional.isPresent()) {
            ProfileEntity profileEntity = profileEntityOptional.get();
            ProfileDTO profileDTO = toProfileDTO(profileEntity);

            if (!profileEntity.getProfileEntities().isEmpty()) {
                ProfileFileEntity fileEntity = profileEntity.getProfileEntities().get(0);
                profileDTO.setOriginalFileName(fileEntity.getOriginalFileName());
                profileDTO.setStoredFileName(fileEntity.getStoredFileName());
            }

            return profileDTO;
        } else {
            return null;
        }
    }

    public ProfileEntity toSaveFileEntity(ProfileDTO profileDTO){
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setProfileName(profileDTO.getProfileName());
        profileEntity.setProfilePassword(profileDTO.getProfilePassword());
        profileEntity.setProfileGender(profileDTO.getProfileGender());
        profileEntity.setProfileAge(profileDTO.getProfileAge());
        profileEntity.setProfileHeight(profileDTO.getProfileHeight());
        profileEntity.setProfileMBTI(profileDTO.getProfileMBTI());
        profileEntity.setProfileLocation(profileDTO.getProfileLocation());
        return profileEntity;
    }

    public ProfileFileEntity toProfileFileEntity(ProfileEntity profileEntity, String originalFilename, String storedFilename) {
        ProfileFileEntity profileFileEntity = new ProfileFileEntity();
        profileFileEntity.setOriginalFileName(originalFilename);
        profileFileEntity.setStoredFileName(storedFilename);
        profileFileEntity.setProfileEntity(profileEntity);
        return profileFileEntity;
    }

    public ProfileDTO update(ProfileDTO updateDTO, Long userId) throws IOException {
        ProfileEntity updateEntity = toUpdateEntity(updateDTO);

        MultipartFile profileFile = updateDTO.getProfileFile();
        if (profileFile != null && !profileFile.isEmpty()) {
            String originalFilename = profileFile.getOriginalFilename();
            String storedFilename = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/lovematch_img/" + storedFilename;

            // ProfileFileEntity 생성
            ProfileFileEntity profileFileEntity = new ProfileFileEntity();

            // 파일 저장
            profileFile.transferTo(new File(savePath));

            // ProfileEntity에 storedFileName 설정
            updateEntity.setStoredFileName(storedFilename);

            // ProfileFileEntity 저장
            profileFileEntity.setOriginalFileName(originalFilename);
            profileFileEntity.setStoredFileName(storedFilename);
            profileFileEntity.setProfileEntity(updateEntity);

            // user_id 설정
            updateEntity.setUserId(userId);
            updateEntity.getProfileEntities().add(profileFileEntity);
        }

        profileRepository.save(updateEntity);
        return findById(updateEntity.getProfileNo());
    }

    public ProfileFileEntity toUpdateFileEntity(ProfileEntity updateEntity, String originalFilename, String storedFilename) {
        ProfileFileEntity updateFileEntity = new ProfileFileEntity();
        updateFileEntity.setOriginalFileName(originalFilename);
        updateFileEntity.setStoredFileName(storedFilename);
        updateFileEntity.setProfileEntity(updateEntity);
        return updateFileEntity;
    }

    public Page<ProfileDTO> findAll(Pageable pageable) {
        Page<ProfileEntity> profilePage = profileRepository.findAll(pageable);
        return profilePage.map(this::convertToProfileDTO);
    }

    private ProfileDTO convertToProfileDTO(ProfileEntity profile) {
        ProfileDTO dto = new ProfileDTO();

        dto.setProfileNo(profile.getProfileNo());
        dto.setProfileName(profile.getProfileName());
        dto.setProfileLocation(profile.getProfileLocation());
        dto.setProfileMBTI(profile.getProfileMBTI());
        dto.setStoredFileName(profile.getStoredFileName());

        // 프로필 이미지 정보 설정
        if (!profile.getProfileEntities().isEmpty()) {
            ProfileFileEntity fileEntity = profile.getProfileEntities().get(0);
            dto.setStoredFileName(fileEntity.getStoredFileName());
        }

        return dto;
    }

    public ProfileDTO toProfileDTO(ProfileEntity profileEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setProfileNo(profileEntity.getProfileNo());
        profileDTO.setProfileName(profileEntity.getProfileName());
        profileDTO.setProfilePassword(profileEntity.getProfilePassword());
        profileDTO.setProfileGender(profileEntity.getProfileGender());
        profileDTO.setProfileAge(profileEntity.getProfileAge());
        profileDTO.setProfileHeight(profileEntity.getProfileHeight());
        profileDTO.setProfileMBTI(profileEntity.getProfileMBTI());
        profileDTO.setProfileLocation(profileEntity.getProfileLocation());
        return profileDTO;
    }

    public ProfileEntity toUpdateEntity(ProfileDTO updateDTO) {
        ProfileEntity updateEntity = profileRepository.findById(updateDTO.getProfileNo()).get();
        updateEntity.setProfileNo(updateDTO.getProfileNo());
        updateEntity.setProfileName(updateDTO.getProfileName());
        updateEntity.setProfilePassword(updateDTO.getProfilePassword());
        updateEntity.setProfileGender(updateDTO.getProfileGender());
        updateEntity.setProfileAge(updateDTO.getProfileAge());
        updateEntity.setProfileHeight(updateDTO.getProfileHeight());
        updateEntity.setProfileMBTI(updateDTO.getProfileMBTI());
        updateEntity.setProfileLocation(updateDTO.getProfileLocation());
        return updateEntity;
    }

    public ProfileDTO findByDetailId(Long profileNo) {
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findById(profileNo);
        if(profileEntityOptional.isPresent()) {
            ProfileEntity profileEntity = profileEntityOptional.get();
            ProfileDTO profileDTO = toProfileDTO(profileEntity);

            // 프로필 이미지 정보 설정
            if (!profileEntity.getProfileEntities().isEmpty()) {
                ProfileFileEntity fileEntity = profileEntity.getProfileEntities().get(0);
                profileDTO.setOriginalFileName(fileEntity.getOriginalFileName());
                profileDTO.setStoredFileName(fileEntity.getStoredFileName());
            } else {
                // ProfileEntity에 직접 저장된 storedFileName 사용
                profileDTO.setStoredFileName(profileEntity.getStoredFileName());
            }
            return profileDTO;
        } else {
            return null;
        }
    }

}
