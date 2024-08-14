package com.ohgiraffers.lovematchproject.profile.repository;


import com.ohgiraffers.lovematchproject.profile.model.entity.ProfileFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileFileRepository extends JpaRepository<ProfileFileEntity, Long> {
}
