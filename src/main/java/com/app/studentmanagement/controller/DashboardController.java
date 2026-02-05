package com.app.studentmanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.studentmanagement.dto.CountResponseDto;
import com.app.studentmanagement.dto.MonthlyTrendDto;
import com.app.studentmanagement.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    //  TOTAL STUDENTS
    @GetMapping("/total-students")
    public ResponseEntity<Long> totalStudents() {
        return ResponseEntity.ok(dashboardService.getTotalStudents());
    }

    //  GENDER GRAPH
    @GetMapping("/gender-count")
    public ResponseEntity<List<CountResponseDto>> genderCount() {
        return ResponseEntity.ok(dashboardService.getGenderCount());
    }

    //  CATEGORY GRAPH
    @GetMapping("/category-count")
    public ResponseEntity<List<CountResponseDto>> categoryCount() {
        return ResponseEntity.ok(dashboardService.getCategoryCount());
    }

    //  INSTITUTE GRAPH
    @GetMapping("/institute-count")
    public ResponseEntity<List<CountResponseDto>> instituteCount() {
        return ResponseEntity.ok(dashboardService.getInstituteCount());
    }

    //  REGISTRATION TREND
    @GetMapping("/registration-trend")
    public ResponseEntity<List<MonthlyTrendDto>> registrationTrend(
            @RequestParam int year) {
        return ResponseEntity.ok(
                dashboardService.getRegistrationTrend(year)
        );
    }
}
