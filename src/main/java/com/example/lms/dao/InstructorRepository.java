package com.example.lms.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lms.entities.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // Add custom query methods if needed, e.g.:
//     List<Instructor> findByDepartment(String department);
	Optional<Instructor> findByName(String name);
}
