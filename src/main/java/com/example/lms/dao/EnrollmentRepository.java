package com.example.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lms.entities.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // Add custom query methods if needed, e.g.:
    // List<Enrollment> findByStudentId(Long studentId);
    // List<Enrollment> findByCourseId(Long courseId);
}
