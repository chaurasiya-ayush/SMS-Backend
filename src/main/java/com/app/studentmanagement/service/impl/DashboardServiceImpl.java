package com.app.studentmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.studentmanagement.dto.CountResponseDto;
import com.app.studentmanagement.dto.MonthlyTrendDto;
import com.app.studentmanagement.repository.StudentRepository;
import com.app.studentmanagement.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final StudentRepository studentRepository;

    public DashboardServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public long getTotalStudents() {
        return studentRepository.count();
    }

    @Override
    public List<CountResponseDto> getGenderCount() {
        List<Object[]> data = studentRepository.countByGender();
        List<CountResponseDto> result = new ArrayList<>();

        for (Object[] row : data) {
            result.add(new CountResponseDto(
                    row[0].toString(),
                    (Long) row[1]
            ));
        }
        return result;
    }

    @Override
    public List<CountResponseDto> getCategoryCount() {
        List<Object[]> data = studentRepository.countByCategory();
        List<CountResponseDto> result = new ArrayList<>();

        for (Object[] row : data) {
            result.add(new CountResponseDto(
                    row[0].toString(),
                    (Long) row[1]
            ));
        }
        return result;
    }

    @Override
    public List<CountResponseDto> getInstituteCount() {
        List<Object[]> data = studentRepository.countByInstitute();
        List<CountResponseDto> result = new ArrayList<>();

        for (Object[] row : data) {
            result.add(new CountResponseDto(
                    row[0].toString(),
                    (Long) row[1]
            ));
        }
        return result;
    }

    @Override
    public List<MonthlyTrendDto> getRegistrationTrend(int year) {
        List<Object[]> data = studentRepository.registrationTrend(year);
        List<MonthlyTrendDto> result = new ArrayList<>();

        for (Object[] row : data) {
            result.add(new MonthlyTrendDto(
                    (Integer) row[0],
                    (Long) row[1]
            ));
        }
        return result;
    }
}
