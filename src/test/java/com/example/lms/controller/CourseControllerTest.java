package com.example.lms.controller;

import com.example.lms.entities.Course;
import com.example.lms.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        course1 = new Course(1L, "Java Basics", "Intro to Java", 4,
                LocalDate.of(2025, 8, 20), LocalDate.of(2025, 12, 20));

        course2 = new Course(2L, "Spring Boot", "Spring Boot Advanced", 3,
                LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 15));
    }

    @Test
    void testAddCourse() {
        when(courseService.addCourse(course1)).thenReturn(course1);

        Course result = courseController.addCourse(course1);

        assertNotNull(result);
        assertEquals("Java Basics", result.getTitle());
        assertEquals(4, result.getCredits());
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = Arrays.asList(course1, course2);
        when(courseService.getAllCourses()).thenReturn(courses);

        List<Course> result = courseController.getAllCourses();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Spring Boot", result.get(1).getTitle());
    }

    @Test
    void testGetCourseById() {
        when(courseService.getCourseById(1L)).thenReturn(course1);

        Course result = courseController.getCourseById(1L);

        assertNotNull(result);
        assertEquals("Java Basics", result.getTitle());
        assertEquals(4, result.getCredits());
    }

    @Test
    void testUpdateCourse() {
        when(courseService.updateCourse(2L, course2)).thenReturn(course2);

        Course result = courseController.updateCourse(2L, course2);

        assertNotNull(result);
        assertEquals("Spring Boot", result.getTitle());
    }

    @Test
    void testDeleteCourse() {
        courseController.deleteCourse(2L);
        verify(courseService, times(1)).deleteCourse(2L);
    }
}
