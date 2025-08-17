package com.example.lms.controller;

import com.example.lms.controller.EnrollmentController;
import com.example.lms.entities.Enrollment;
import com.example.lms.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setGrade("A");
        enrollment.setProgress("50%");

        when(enrollmentService.addEnrollment(enrollment)).thenReturn(enrollment);

        Enrollment result = enrollmentController.addEnrollment(enrollment);
        assertEquals("50%", result.getProgress());
    }

    @Test
    void testGetAllEnrollments() {
        Enrollment e1 = new Enrollment();
        e1.setId(1L);
        e1.setGrade("A");
        e1.setProgress("40%");

        Enrollment e2 = new Enrollment();
        e2.setId(2L);
        e2.setGrade("B");
        e2.setProgress("70%");

        when(enrollmentService.getAllEnrollments()).thenReturn(Arrays.asList(e1, e2));

        List<Enrollment> result = enrollmentController.getAllEnrollments();
        assertEquals(2, result.size());
    }

    @Test
    void testGetEnrollmentById() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setGrade("A");
        enrollment.setProgress("90%");

        when(enrollmentService.getEnrollmentById(1L)).thenReturn(enrollment);

        Enrollment result = enrollmentController.getEnrollmentById(1L);
        assertEquals("90%", result.getProgress());
    }

    @Test
    void testUpdateEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setGrade("A+");
        enrollment.setProgress("100%");

        when(enrollmentService.updateEnrollment(1L, enrollment)).thenReturn(enrollment);

        Enrollment result = enrollmentController.updateEnrollment(1L, enrollment);
        assertEquals("100%", result.getProgress());
    }

    @Test
    void testDeleteEnrollment() {
        doNothing().when(enrollmentService).deleteEnrollment(1L);
        enrollmentController.deleteEnrollment(1L);
        verify(enrollmentService, times(1)).deleteEnrollment(1L);
    }
}
