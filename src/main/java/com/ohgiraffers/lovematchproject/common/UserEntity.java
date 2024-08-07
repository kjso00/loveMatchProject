package com.ohgiraffers.lovematchproject.common;

import com.ohgiraffers.lovematchproject.login.entity.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_user")
public class UserEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

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
