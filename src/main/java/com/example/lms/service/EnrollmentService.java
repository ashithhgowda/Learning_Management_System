package com.example.lms.service;

import com.example.lms.dao.EnrollmentRepository;
import com.example.lms.entities.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment addEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(Long id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        return enrollment.orElse(null);
    }

    public Enrollment updateEnrollment(Long id, Enrollment updatedEnrollment) {
        Optional<Enrollment> existingEnrollmentOptional = enrollmentRepository.findById(id);
        if (existingEnrollmentOptional.isPresent()) {
            Enrollment existingEnrollment = existingEnrollmentOptional.get();
            existingEnrollment.setGrade(updatedEnrollment.getGrade());
            existingEnrollment.setProgress(updatedEnrollment.getProgress());
            existingEnrollment.setStudent(updatedEnrollment.getStudent());
            existingEnrollment.setCourse(updatedEnrollment.getCourse());
            return enrollmentRepository.save(existingEnrollment);
        } else {
            System.out.println("Enrollment not found with ID: " + id);
            return null;
        }
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
}
