package com.ohgiraffers.lovematchproject.date.datecontroller;


import com.ohgiraffers.lovematchproject.date.datemodel.dateentity.DateEntity;
import com.ohgiraffers.lovematchproject.date.dateservice.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DateController {

    private final DateService dateService;

    @Autowired
    public DateController(DateService dateService) {
        this.dateService = dateService;
    }

    @GetMapping("/date/datesearch")
    public String dateSearch(Model model) {
        model.addAttribute("kakaoAppKey","95e178d0770fb28588aaa64253f3ae29");
        return "date/datesearch";
    }




    @PostMapping
    public ResponseEntity<DateEntity> addDate(@RequestBody DateEntity dateRequest) {
        DateEntity date = dateService.addDate(
                dateRequest.getUserId(),
                dateRequest.getPlaceName(),
                dateRequest.getPlaceAddress(),
                dateRequest.getDateTime(),
                dateRequest.getNotes()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(date);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DateEntity>> getUserDates(@PathVariable Long userId) {
        List<DateEntity> dates = dateService.getUserDates(userId);
        return ResponseEntity.ok(dates);
    }

    @GetMapping("/place/{placeName}")
    public ResponseEntity<List<DateEntity>> getDatesByPlace(@PathVariable String placeName) {
        List<DateEntity> dates = dateService.getDatesByPlace(placeName);
        return ResponseEntity.ok(dates);
    }
}