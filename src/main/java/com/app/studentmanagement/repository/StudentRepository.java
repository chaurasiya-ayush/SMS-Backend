package com.app.studentmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.studentmanagement.entity.Student;
import com.app.studentmanagement.enums.Category;

public interface StudentRepository extends JpaRepository<Student, Long> {
	// TOTAL COUNT
	long count();

	// GENDER COUNT
	@Query("SELECT s.gender, COUNT(s) FROM Student s GROUP BY s.gender")
	List<Object[]> countByGender();

	// CATEGORY COUNT
	@Query("SELECT s.category, COUNT(s) FROM Student s GROUP BY s.category")
	List<Object[]> countByCategory();

	// INSTITUTE COUNT
	@Query("SELECT s.institute, COUNT(s) FROM Student s GROUP BY s.institute")
	List<Object[]> countByInstitute();

	// MONTH-WISE REGISTRATION TREND
	@Query("""
			    SELECT MONTH(s.createdAt), COUNT(s)
			    FROM Student s
			    WHERE YEAR(s.createdAt) = :year
			    GROUP BY MONTH(s.createdAt)
			    ORDER BY MONTH(s.createdAt)
			""")
	List<Object[]> registrationTrend(int year);

	Optional<Student> findByEmail(String email);

	Optional<Student> findByPhoneNumber(String phoneNumber);

	Page<Student> findByCategory(Category category, Pageable pageable);

	@Query("""
			   SELECT s FROM Student s
			   WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
			      OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
			      OR LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
			      OR LOWER(s.institute) LIKE LOWER(CONCAT('%', :keyword, '%'))
			""")
	Page<Student> searchStudents(@Param("keyword") String keyword, Pageable pageable);
}
