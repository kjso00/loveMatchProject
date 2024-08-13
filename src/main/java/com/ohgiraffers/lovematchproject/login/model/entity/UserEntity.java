package com.ohgiraffers.lovematchproject.login.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role") // 사용자 권한
    private Role role;

    public UserEntity() {
    }

    public UserEntity(Long id, String userId, String email, Role role) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
