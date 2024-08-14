package com.ohgiraffers.lovematchproject.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessController {
    @GetMapping("/noaccess")
    public String accessError() {
        return "noaccess";
    }
}
