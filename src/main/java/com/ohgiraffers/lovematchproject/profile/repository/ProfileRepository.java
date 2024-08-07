package com.ohgiraffers.lovematchproject.profile.repository;

import com.ohgiraffers.lovematchproject.profile.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
}
