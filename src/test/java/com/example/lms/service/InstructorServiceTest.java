package com.example.lms.service;

import com.example.lms.dao.InstructorRepository;
import com.example.lms.entities.Instructor;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServiceTest {

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private InstructorService instructorService;

    private Instructor instructor1, instructor2, instructor3;

    @BeforeAll
    static void setupAll() {
        System.out.println("Before all InstructorService tests...");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Before each test...");

        instructor1 = new Instructor();
        instructor1.setId(1L);
        instructor1.setName("Dr. Ramesh Kumar");
        instructor1.setEmail("ramesh.kumar@example.com");
        instructor1.setDepartment("Computer Science");

        instructor2 = new Instructor();
        instructor2.setId(2L);
        instructor2.setName("Prof. Shraddha Patil");
        instructor2.setEmail("shraddha.patil@example.com");
        instructor2.setDepartment("Information Technology");

        instructor3 = new Instructor();
        instructor3.setId(3L);
        instructor3.setName("Dr. Parneet Singh");
        instructor3.setEmail("parneet.singh@example.com");
        instructor3.setDepartment("Electronics");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each test...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("After all InstructorService tests...");
    }

    @Test
    void testAddInstructor() {
        when(instructorRepository.save(instructor1)).thenReturn(instructor1);

        Instructor saved = instructorService.addInstructor(instructor1);

        assertNotNull(saved);
        assertEquals("Dr. Ramesh Kumar", saved.getName());
        assertEquals("ramesh.kumar@example.com", saved.getEmail());
    }

    @Test
    void testGetAllInstructors() {
        when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor1, instructor2, instructor3));

        List<Instructor> instructors = instructorService.getAllInstructors();

        assertNotNull(instructors);
        assertEquals(3, instructors.size());
        assertTrue(instructors.contains(instructor2));
    }

    @Test
    void testGetInstructorById_Found() {
        when(instructorRepository.findById(2L)).thenReturn(Optional.of(instructor2));

        Instructor found = instructorService.getInstructorById(2L);

        assertNotNull(found);
        assertEquals("Prof. Shraddha Patil", found.getName());
        assertEquals("shraddha.patil@example.com", found.getEmail());
    }

    @Test
    void testGetInstructorById_NotFound() {
        when(instructorRepository.findById(99L)).thenReturn(Optional.empty());

        Instructor notFound = instructorService.getInstructorById(99L);

        assertNull(notFound);
    }

    @Test
    void testUpdateInstructor_Found() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor1));
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor2);

        Instructor updated = instructorService.updateInstructor(1L, instructor2);

        assertNotNull(updated);
        assertEquals("Prof. Shraddha Patil", updated.getName());
        assertEquals("shraddha.patil@example.com", updated.getEmail());
    }

    @Test
    void testUpdateInstructor_NotFound() {
        when(instructorRepository.findById(88L)).thenReturn(Optional.empty());

        Instructor result = instructorService.updateInstructor(88L, instructor3);

        assertNull(result);
    }

    @Test
    void testDeleteInstructor() {
        instructorService.deleteInstructor(1L);
        verify(instructorRepository, times(1)).deleteById(1L);
    }
}
