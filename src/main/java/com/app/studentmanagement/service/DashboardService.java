package com.app.studentmanagement.service;

import java.util.List;

import com.app.studentmanagement.dto.CountResponseDto;
import com.app.studentmanagement.dto.MonthlyTrendDto;

public interface DashboardService {

    long getTotalStudents();

    List<CountResponseDto> getGenderCount();

    List<CountResponseDto> getCategoryCount();

    List<CountResponseDto> getInstituteCount();

    List<MonthlyTrendDto> getRegistrationTrend(int year);
}
