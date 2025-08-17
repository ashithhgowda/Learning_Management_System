package com.example.lms.service;

import com.example.lms.dao.CourseRepository;
import com.example.lms.entities.Course;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course1, course2, course3;

    @BeforeAll
    static void setupAll() {
        System.out.println("Before all CourseService tests...");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        course1 = new Course(1L, "Java Basics", "Intro to Java", 4,
                LocalDate.of(2025, 8, 20), LocalDate.of(2025, 12, 20));

        course2 = new Course(2L, "Spring Boot", "Spring Boot Advanced", 3,
                LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 15));

        course3 = new Course(3L, "Hibernate", "Hibernate ORM", 2,
                LocalDate.of(2025, 10, 1), LocalDate.of(2025, 12, 30));
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each test...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("After all CourseService tests...");
    }

    @Test
    void testAddCourse() {
        when(courseRepository.save(course1)).thenReturn(course1);

        Course saved = courseService.addCourse(course1);

        assertNotNull(saved);
        assertEquals("Java Basics", saved.getTitle());
    }

    @Test
    void testGetAllCourses() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2, course3));

        List<Course> courses = courseService.getAllCourses();

        assertNotNull(courses);
        assertEquals(3, courses.size());
        assertTrue(courses.contains(course2));
    }

    @Test
    void testGetCourseById_Found() {
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course2));

        Course found = courseService.getCourseById(2L);

        assertNotNull(found);
        assertEquals("Spring Boot", found.getTitle());
    }

    @Test
    void testGetCourseById_NotFound() {
        when(courseRepository.findById(9L)).thenReturn(Optional.empty());

        Course notFound = courseService.getCourseById(9L);

        assertNull(notFound);
    }

    @Test
    void testUpdateCourse_Found() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));
        when(courseRepository.save(any(Course.class))).thenReturn(course2);

        Course updated = courseService.updateCourse(1L, course2);

        assertNotNull(updated);
        assertEquals("Spring Boot", updated.getTitle());
    }

    @Test
    void testUpdateCourse_NotFound() {
        when(courseRepository.findById(9L)).thenReturn(Optional.empty());

        Course result = courseService.updateCourse(9L, course3);

        assertNull(result);
    }

    @Test
    void testDeleteCourse() {
        courseService.deleteCourse(1L);
        verify(courseRepository, times(1)).deleteById(1L);
    }
}
