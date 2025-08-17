package com.example.lms.controller;

import com.example.lms.entities.Student;
import com.example.lms.service.StudentService;
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
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student();
        student1.setId(1L);
        student1.setName("Amit Sharma");
        student1.setEmail("amit.sharma@example.com");
        student1.setEnrollmentDate(LocalDate.of(2024, 6, 10));

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Priya Singh");
        student2.setEmail("priya.singh@example.com");
        student2.setEnrollmentDate(LocalDate.of(2024, 7, 5));
    }

    @Test
    void testAddStudent() {
        when(studentService.addStudent(student1)).thenReturn(student1);

        Student result = studentController.addStudent(student1);

        assertNotNull(result);
        assertEquals("Amit Sharma", result.getName());
        assertEquals("amit.sharma@example.com", result.getEmail());
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(students);

        List<Student> result = studentController.getAllStudents();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(students, result);
        assertEquals("Priya Singh", result.get(1).getName());
    }

    @Test
    void testGetStudentById() {
        when(studentService.getStudentById(1L)).thenReturn(student1);

        Student result = studentController.getStudentById(1L);

        assertNotNull(result);
        assertEquals("Amit Sharma", result.getName());
        assertEquals("amit.sharma@example.com", result.getEmail());
    }

    @Test
    void testUpdateStudent() {
        when(studentService.updateStudent(2L, student2)).thenReturn(student2);

        Student result = studentController.updateStudent(2L, student2);

        assertNotNull(result);
        assertEquals("Priya Singh", result.getName());
        assertEquals("priya.singh@example.com", result.getEmail());
    }

    @Test
    void testDeleteStudent() {
        studentController.deleteStudent(1L);
        verify(studentService, times(1)).deleteStudent(1L);
    }
}
