package com.ohgiraffers.lovematchproject.date.datecontroller;

import com.ohgiraffers.lovematchproject.date.dateservice.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dates")
public class DateViewController {

    private final DateService dateService;

    @Autowired
    public DateViewController(DateService dateService) {
        this.dateService = dateService;
    }

    /**
     * 데이트 메인 페이지를 표시합니다.
     * @param model Thymeleaf 모델
     * @return 데이트 메인 페이지 뷰 이름
     */
    @GetMapping("/main")
    public String dateMain(Model model) {
        model.addAttribute("categories", dateService.getAllCategories());
        return "date/datemain";
    }


}