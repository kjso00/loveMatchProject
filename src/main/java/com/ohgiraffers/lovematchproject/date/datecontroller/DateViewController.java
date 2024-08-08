package com.ohgiraffers.lovematchproject.date.datecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dates")
public class DateViewController {

    @GetMapping("/main")
    public String dateMain() {
        return "date/datemain";
    }


}