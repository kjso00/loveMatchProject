package com.ohgiraffers.lovematchproject.match.matchrepository;

import com.ohgiraffers.lovematchproject.profile.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<ProfileEntity, Long>{
    ProfileEntity findById(long id);
}
