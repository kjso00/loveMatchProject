package com.ohgiraffers.lovematchproject.login.repository;

import com.ohgiraffers.lovematchproject.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
