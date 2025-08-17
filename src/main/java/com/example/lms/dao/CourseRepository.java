package com.example.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lms.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Add custom query methods if needed, e.g.:
    // List<Course> findByTitleContainingIgnoreCase(String title);
}
