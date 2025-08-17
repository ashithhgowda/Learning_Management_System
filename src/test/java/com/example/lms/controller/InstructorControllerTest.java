package com.example.lms.controller;

import com.example.lms.entities.Instructor;
import com.example.lms.service.InstructorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstructorControllerTest {

    @Mock
    private InstructorService instructorService;

    @InjectMocks
    private InstructorController instructorController;

    private Instructor instructor1;
    private Instructor instructor2;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void testAddInstructor() {
        when(instructorService.addInstructor(instructor1)).thenReturn(instructor1);

        Instructor result = instructorController.addInstructor(instructor1);

        assertNotNull(result);
        assertEquals("Dr. Ramesh Kumar", result.getName());
        assertEquals("ramesh.kumar@example.com", result.getEmail());
    }

    @Test
    void testGetAllInstructors() {
        List<Instructor> instructors = Arrays.asList(instructor1, instructor2);
        when(instructorService.getAllInstructors()).thenReturn(instructors);

        List<Instructor> result = instructorController.getAllInstructors();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Prof. Shraddha Patil", result.get(1).getName());
    }

    @Test
    void testGetInstructorById() {
        when(instructorService.getInstructorById(1L)).thenReturn(instructor1);

        Instructor result = instructorController.getInstructorById(1L);

        assertNotNull(result);
        assertEquals("Dr. Ramesh Kumar", result.getName());
        assertEquals("Computer Science", result.getDepartment());
    }

    @Test
    void testUpdateInstructor() {
        when(instructorService.updateInstructor(2L, instructor2)).thenReturn(instructor2);

        Instructor result = instructorController.updateInstructor(2L, instructor2);

        assertNotNull(result);
        assertEquals("Prof. Shraddha Patil", result.getName());
        assertEquals("Information Technology", result.getDepartment());
    }

    @Test
    void testDeleteInstructor() {
        instructorController.deleteInstructor(2L);
        verify(instructorService, times(1)).deleteInstructor(2L);
    }
}
