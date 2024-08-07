package com.ohgiraffers.lovematchproject.date.datecontroller;

import com.ohgiraffers.lovematchproject.date.datemodel.datedto.DateDTO;
import com.ohgiraffers.lovematchproject.date.dateservice.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dates")
public class DateApiController {

    private final DateService dateService;

    @Autowired
    public DateApiController(DateService dateService) {
        this.dateService = dateService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DateDTO>> getAllDates() {
        List<DateDTO> dates = dateService.getAllDates();
        return ResponseEntity.ok(dates);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DateDTO> getDateById(@PathVariable Long id) {
        DateDTO date = dateService.getDateById(id);
        return date != null ? ResponseEntity.ok(date) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DateDTO> createDate(@RequestBody DateDTO dateDTO) {
        DateDTO createdDate = dateService.saveDate(dateDTO);
        return new ResponseEntity<>(createdDate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DateDTO> updateDate(@PathVariable Long id, @RequestBody DateDTO dateDTO) {
        DateDTO updatedDate = dateService.updateDate(id, dateDTO);
        return updatedDate != null ? ResponseEntity.ok(updatedDate) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteDate(@PathVariable Long id) {
        dateService.deleteDate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nearby")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DateDTO>> getNearbyDates(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") double distance) {
        List<DateDTO> nearbyDates = dateService.getNearbyDates(latitude, longitude, distance);
        return ResponseEntity.ok(nearbyDates);
    }

    @GetMapping("/culture-activities")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Map<String, Object>>> getCultureActivities() {
        List<Map<String, Object>> activities = dateService.getCultureActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/performances")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Map<String, Object>>> getPerformances() {
        List<Map<String, Object>> performances = dateService.getPerformances();
        return ResponseEntity.ok(performances);
    }

    @GetMapping("/restaurants")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Map<String, Object>>> getRestaurants(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        List<Map<String, Object>> restaurants = dateService.getRestaurants(latitude, longitude);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Map<String, Object>>> getDatesByCategory(
            @PathVariable String category,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude) {
        List<Map<String, Object>> dates = dateService.getDatesByCategory(category, latitude, longitude);
        return ResponseEntity.ok(dates);
    }
}