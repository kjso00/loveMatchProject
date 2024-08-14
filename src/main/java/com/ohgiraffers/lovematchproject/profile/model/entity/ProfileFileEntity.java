package com.ohgiraffers.lovematchproject.profile.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profile_file_table")
public class ProfileFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "profile_no")
    private ProfileEntity profileEntity;

    public ProfileFileEntity() {
    }

    public ProfileFileEntity(Long fileId, String originalFileName, String storedFileName, ProfileEntity profileEntity) {
        this.fileId = fileId;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.profileEntity = profileEntity;
    }

    public Long getId() {
        return fileId;
    }

    public void setId(Long fileId) {
        this.fileId = fileId;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public ProfileEntity getProfileEntity() {
        return profileEntity;
    }

    public void setProfileEntity(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
    }

    @Override
    public String toString() {
        return "ProfileFileEntity{" +
                "fileId=" + fileId +
                ", originalFileName='" + originalFileName + '\'' +
                ", storedFileName='" + storedFileName + '\'' +
                ", profileEntity=" + profileEntity +
                '}';
    }

}
