package com.app.studentmanagement.service;

import org.springframework.data.domain.Page;

import com.app.studentmanagement.dto.StudentPatchRequestDto;
import com.app.studentmanagement.dto.StudentRequestDto;
import com.app.studentmanagement.dto.StudentResponseDto;
import com.app.studentmanagement.enums.Category;

public interface StudentService {

	StudentResponseDto createStudent(StudentRequestDto request);

	StudentResponseDto getStudentById(Long id);

	Page<StudentResponseDto> getAllStudents(int page, int size);

	Page<StudentResponseDto> getStudentsByCategory(Category category, int page, int size);

	StudentResponseDto updateStudent(Long id, StudentRequestDto request);

	void deleteStudent(Long id);

	StudentResponseDto patchStudent(Long id, StudentPatchRequestDto request);

	Page<StudentResponseDto> searchStudents(String keyword, int page, int size, String sortBy, String direction);

	Page<StudentResponseDto> sortStudents(String sortBy, String direction, int page, int size);
}
