package com.ohgiraffers.lovematchproject.login.admin.admincon;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login/admin") // 이 경로로 들어오면
@PreAuthorize("hasAuthority('ADMIN')") // ADMIN권한만 접근가능
public class AdminController {

    @GetMapping // 관리자 메인페이지
    public String adminPage(){
        return "/login/admin/admin";
    }
}
