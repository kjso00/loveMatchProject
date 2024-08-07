package com.ohgiraffers.lovematchproject.login.repository;

import com.ohgiraffers.lovematchproject.common.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId);
}
