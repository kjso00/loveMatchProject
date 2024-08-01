package com.ohgiraffers.lovematchproject.login.dto;

public enum Role {
    USER("USER"),
    GUEST("GUEST"),
    ADMIN("ADMIN");
    private final String Role;

    Role(String role) {
        Role = role;
    }

    public String getKey(){
        return Role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "Role='" + Role + '\'' +
                '}';
    }
}
