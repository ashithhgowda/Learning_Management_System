package com.example.lms.service;

import com.example.lms.dao.StudentRepository;
import com.example.lms.entities.Student;
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

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student1, student2, student3;

    @BeforeAll
    static void setupAll() {
        System.out.println("Before all StudentService tests...");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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

        student3 = new Student();
        student3.setId(3L);
        student3.setName("Rajesh Kumar");
        student3.setEmail("rajesh.kumar@example.com");
        student3.setEnrollmentDate(LocalDate.of(2024, 8, 1));
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each StudentService test...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("After all StudentService tests...");
    }

    @Test
    void testAddStudent() {
        when(studentRepository.save(student1)).thenReturn(student1);

        Student saved = studentService.addStudent(student1);

        assertNotNull(saved);
        assertEquals("Amit Sharma", saved.getName());
        assertEquals("amit.sharma@example.com", saved.getEmail());
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2, student3));

        List<Student> students = studentService.getAllStudents();

        assertNotNull(students);
        assertEquals(3, students.size());
        assertTrue(students.contains(student2));
    }

    @Test
    void testGetStudentById_Found() {
        when(studentRepository.findById(2L)).thenReturn(Optional.of(student2));

        Student found = studentService.getStudentById(2L);

        assertNotNull(found);
        assertEquals("Priya Singh", found.getName());
        assertEquals("priya.singh@example.com", found.getEmail());
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        Student notFound = studentService.getStudentById(99L);

        assertNull(notFound);
    }

    @Test
    void testUpdateStudent_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.save(any(Student.class))).thenReturn(student2);

        Student updated = studentService.updateStudent(1L, student2);

        assertNotNull(updated);
        assertEquals("Priya Singh", updated.getName());
        assertEquals("priya.singh@example.com", updated.getEmail());
    }

    @Test
    void testUpdateStudent_NotFound() {
        when(studentRepository.findById(88L)).thenReturn(Optional.empty());

        Student result = studentService.updateStudent(88L, student3);

        assertNull(result);
    }

    @Test
    void testDeleteStudent() {
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }
}
