package com.example.lms.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lms.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Add custom query methods if needed, e.g.:
    // List<Student> findByNameContainingIgnoreCase(String name);
	Optional<Student> findByName(String name);
}
