package com.example.lms.service;

import com.example.lms.dao.InstructorRepository;
import com.example.lms.entities.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    public Instructor addInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor getInstructorById(Long id) {
        Optional<Instructor> instructor = instructorRepository.findById(id);
        return instructor.orElse(null);
    }

    public Instructor updateInstructor(Long id, Instructor updatedInstructor) {
        Optional<Instructor> existingInstructorOptional = instructorRepository.findById(id);
        if (existingInstructorOptional.isPresent()) {
            Instructor existingInstructor = existingInstructorOptional.get();
            existingInstructor.setName(updatedInstructor.getName());
            existingInstructor.setEmail(updatedInstructor.getEmail());
            existingInstructor.setDepartment(updatedInstructor.getDepartment());
            return instructorRepository.save(existingInstructor);
        } else {
            System.out.println("Instructor not found with ID: " + id);
            return null;
        }
    }

    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }
}
