package com.app.studentmanagement.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.app.studentmanagement.dto.StudentPatchRequestDto;
import com.app.studentmanagement.dto.StudentRequestDto;
import com.app.studentmanagement.dto.StudentResponseDto;
import com.app.studentmanagement.enums.Category;
import com.app.studentmanagement.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    // ---------- CREATE ----------
    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(
            @Valid @RequestBody StudentRequestDto request) {

        StudentResponseDto response = studentService.createStudent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ---------- GET BY ID ----------
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {

        StudentResponseDto response = studentService.getStudentById(id);
        return ResponseEntity.ok(response);
    }

    // ---------- GET ALL (PAGINATION) ----------
    @GetMapping
    public ResponseEntity<Page<StudentResponseDto>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<StudentResponseDto> response = studentService.getAllStudents(page, size);
        return ResponseEntity.ok(response);
    }

    // ---------- CATEGORY FILTER ----------
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<StudentResponseDto>> getByCategory(
            @PathVariable Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<StudentResponseDto> response =
                studentService.getStudentsByCategory(category, page, size);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/sort")
    public ResponseEntity<Page<StudentResponseDto>> sortStudents(
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<StudentResponseDto> response =
                studentService.sortStudents(sortBy, direction, page, size);

        return ResponseEntity.ok(response);
    }

    // ---------- UPDATE ----------
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto request) {

        StudentResponseDto response = studentService.updateStudent(id, request);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDto> patchStudent(
            @PathVariable Long id,
            @RequestBody StudentPatchRequestDto request) {

        StudentResponseDto response = studentService.patchStudent(id, request);
        return ResponseEntity.ok(response);
    }
    

    // ---------- DELETE ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponseDto>> searchStudents(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<StudentResponseDto> response =
                studentService.searchStudents(keyword, page, size, sortBy, direction);

        return ResponseEntity.ok(response);
    }
    
    

}
