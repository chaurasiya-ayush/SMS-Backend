package com.app.studentmanagement.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.studentmanagement.dto.StudentPatchRequestDto;
import com.app.studentmanagement.dto.StudentRequestDto;
import com.app.studentmanagement.dto.StudentResponseDto;
import com.app.studentmanagement.entity.Student;
import com.app.studentmanagement.enums.Category;
import com.app.studentmanagement.exception.DuplicateResourceException;
import com.app.studentmanagement.exception.ResourceNotFoundException;
import com.app.studentmanagement.repository.StudentRepository;
import com.app.studentmanagement.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	// ---------- CREATE ----------
	@Override
	public StudentResponseDto createStudent(StudentRequestDto request) {

		studentRepository.findByEmail(request.getEmail()).ifPresent(s -> {
			throw new DuplicateResourceException("Email already exists");
		});

		studentRepository.findByPhoneNumber(request.getPhoneNumber()).ifPresent(s -> {
			throw new DuplicateResourceException("Phone number already exists");
		});

		Student student = new Student();
		student.setFirstName(request.getFirstName());
		student.setLastName(request.getLastName());
		student.setEmail(request.getEmail());
		student.setPhoneNumber(request.getPhoneNumber());
		student.setGender(request.getGender());
		student.setAddress(request.getAddress());
		student.setInstitute(request.getInstitute());
		student.setProfileImageUrl(request.getProfileImageUrl());
		student.setCategory(request.getCategory());
		student.setCreatedAt(LocalDateTime.now());
		student.setUpdatedAt(LocalDateTime.now());

		Student saved = studentRepository.save(student);

		return mapToResponse(saved);
	}

	// ---------- GET BY ID ----------
	@Override
	public StudentResponseDto getStudentById(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

		return mapToResponse(student);
	}

	// ---------- GET ALL (PAGINATION) ----------
	@Override
	public Page<StudentResponseDto> getAllStudents(int page, int size) {
		return studentRepository.findAll(PageRequest.of(page, size)).map(this::mapToResponse);
	}

	// ---------- CATEGORY FILTER ----------
	@Override
	public Page<StudentResponseDto> getStudentsByCategory(Category category, int page, int size) {
		return studentRepository.findByCategory(category, PageRequest.of(page, size)).map(this::mapToResponse);
	}

	// ---------- UPDATE ----------
	@Override
	public StudentResponseDto updateStudent(Long id, StudentRequestDto request) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		student.setFirstName(request.getFirstName());
		student.setLastName(request.getLastName());
		student.setGender(request.getGender());
		student.setAddress(request.getAddress());
		student.setInstitute(request.getInstitute());
		student.setCategory(request.getCategory());
		student.setUpdatedAt(LocalDateTime.now());
		student.setProfileImageUrl(request.getProfileImageUrl());
		Student updated = studentRepository.save(student);

		return mapToResponse(updated);
	}

	// ---------- DELETE ----------
	@Override
	public void deleteStudent(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		studentRepository.delete(student);
	}

	@Override
	public StudentResponseDto patchStudent(Long id, StudentPatchRequestDto request) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		if (request.getFirstName() != null)
			student.setFirstName(request.getFirstName());

		if (request.getLastName() != null)
			student.setLastName(request.getLastName());

		if (request.getGender() != null)
			student.setGender(request.getGender());

		if (request.getAddress() != null)
			student.setAddress(request.getAddress());

		if (request.getInstitute() != null)
			student.setInstitute(request.getInstitute());

		if (request.getCategory() != null)
			student.setCategory(request.getCategory());

		if (request.getProfileImageUrl() != null)
			student.setProfileImageUrl(request.getProfileImageUrl());

		student.setUpdatedAt(LocalDateTime.now());

		Student updated = studentRepository.save(student);
		return mapToResponse(updated);
	}

	@Override
	public Page<StudentResponseDto> searchStudents(String keyword, int page, int size, String sortBy,
			String direction) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Student> students = studentRepository.searchStudents(keyword, pageable);

		return students.map(this::mapToResponse);
	}

	@Override
	public Page<StudentResponseDto> sortStudents(String sortBy, String direction, int page, int size) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return studentRepository.findAll(pageable).map(this::mapToResponse);
	}

	// ---------- MAPPER ----------
	private StudentResponseDto mapToResponse(Student student) {
		StudentResponseDto dto = new StudentResponseDto();
		dto.setId(student.getId());
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setEmail(student.getEmail());
		dto.setPhoneNumber(student.getPhoneNumber());
		dto.setGender(student.getGender());
		dto.setAddress(student.getAddress());
		dto.setInstitute(student.getInstitute());
		dto.setCategory(student.getCategory());
		dto.setProfileImageUrl(student.getProfileImageUrl());
		dto.setCreatedAt(student.getCreatedAt());
		dto.setUpdatedAt(student.getUpdatedAt());
		return dto;
	}
}
