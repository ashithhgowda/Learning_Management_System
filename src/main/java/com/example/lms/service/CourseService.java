package com.example.lms.service;

import com.example.lms.dao.CourseRepository;
import com.example.lms.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.orElse(null);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Optional<Course> existingCourseOptional = courseRepository.findById(id);
        if (existingCourseOptional.isPresent()) {
            Course existingCourse = existingCourseOptional.get();
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setCredits(updatedCourse.getCredits());
            existingCourse.setStartDate(updatedCourse.getStartDate());
            existingCourse.setEndDate(updatedCourse.getEndDate());
            return courseRepository.save(existingCourse);
        } else {
            System.out.println("Course not found with ID: " + id);
            return null;
        }
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
