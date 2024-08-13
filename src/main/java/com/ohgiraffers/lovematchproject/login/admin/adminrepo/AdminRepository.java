package com.ohgiraffers.lovematchproject.login.admin.adminrepo;

import com.ohgiraffers.lovematchproject.login.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<UserEntity, Integer> {
}
