package com.example.lms.service;

import com.example.lms.dao.EnrollmentRepository;
import com.example.lms.entities.Enrollment;
import com.example.lms.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setGrade("A");
        enrollment.setProgress("20%");

        when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);

        Enrollment result = enrollmentService.addEnrollment(enrollment);
        assertEquals("20%", result.getProgress());
    }

    @Test
    void testGetAllEnrollments() {
        Enrollment e1 = new Enrollment();
        e1.setId(1L);
        e1.setGrade("B");
        e1.setProgress("50%");

        Enrollment e2 = new Enrollment();
        e2.setId(2L);
        e2.setGrade("C");
        e2.setProgress("80%");

        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        assertEquals(2, enrollmentService.getAllEnrollments().size());
    }

    @Test
    void testGetEnrollmentById() {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setGrade("A");
        enrollment.setProgress("95%");

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        Enrollment result = enrollmentService.getEnrollmentById(1L);
        assertEquals("95%", result.getProgress());
    }

    @Test
    void testUpdateEnrollment() {
        Enrollment existing = new Enrollment();
        existing.setId(1L);
        existing.setGrade("B");
        existing.setProgress("60%");

        Enrollment updated = new Enrollment();
        updated.setId(1L);
        updated.setGrade("A+");
        updated.setProgress("100%");

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(enrollmentRepository.save(existing)).thenReturn(updated);

        Enrollment result = enrollmentService.updateEnrollment(1L, updated);
        assertEquals("100%", result.getProgress());
    }

    @Test
    void testDeleteEnrollment() {
        doNothing().when(enrollmentRepository).deleteById(1L);
        enrollmentService.deleteEnrollment(1L);
        verify(enrollmentRepository, times(1)).deleteById(1L);
    }
}
