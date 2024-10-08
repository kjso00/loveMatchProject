package com.ohgiraffers.lovematchproject.chat.repository;

import com.ohgiraffers.lovematchproject.chat.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
