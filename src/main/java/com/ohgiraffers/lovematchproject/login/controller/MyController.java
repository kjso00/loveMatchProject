package com.ohgiraffers.lovematchproject.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/my")
public class MyController {

    @GetMapping
    public String myPage() {
        return "login/my";
    }
}
