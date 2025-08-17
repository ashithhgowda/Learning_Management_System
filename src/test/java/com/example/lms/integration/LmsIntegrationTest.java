package com.example.lms.integration;

import com.example.lms.entities.Student;
import com.example.lms.entities.Instructor;
import com.example.lms.entities.Course;
import com.example.lms.entities.Enrollment;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class LmsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Full LMS Workflow Integration Test")
    void fullLmsWorkflowTest() throws Exception {
        // 1️⃣ CREATE STUDENTS
        Long s1Id = createStudentAndReturnId(new Student(null, "Arjun", "arjun@example.com", LocalDate.of(2024, 1, 10)));
        Long s2Id = createStudentAndReturnId(new Student(null, "Meera", "meera@example.com", LocalDate.of(2024, 2, 15)));

        // 2️⃣ CREATE INSTRUCTORS
        Long i1Id = createInstructorAndReturnId(new Instructor(null, "Dr. Rao", "rao@example.com", "Computer Science"));
        Long i2Id = createInstructorAndReturnId(new Instructor(null, "Dr. Anita", "anita@example.com", "Mathematics"));

        // 3️⃣ CREATE COURSES (linked to instructor)
        Long c1Id = createCourseAndReturnId(new Course(null, "Java Basics", "Intro to Java", 4,
                LocalDate.of(2025, 8, 20), LocalDate.of(2025, 12, 20)), i1Id);

        Long c2Id = createCourseAndReturnId(new Course(null, "Calculus I", "Math fundamentals", 3,
                LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 15)), i2Id);

        // 4️⃣ CREATE ENROLLMENTS (link student + course)
        createEnrollment(s1Id, c1Id, new Enrollment(null, "A", "In Progress", null, null));
        createEnrollment(s2Id, c2Id, new Enrollment(null, "B", "Completed", null, null));

        // 5️⃣ VERIFY COUNTS
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(2)));

        mockMvc.perform(get("/instructors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(2)));

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(2)));

        mockMvc.perform(get("/enrollments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(2)));
    }

    // 🔹 Helper Methods

    private Long createStudentAndReturnId(Student student) throws Exception {
        MvcResult result = mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andReturn();

        Student saved = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(saved.getId()).isNotNull();
        return saved.getId();
    }

    private Long createInstructorAndReturnId(Instructor instructor) throws Exception {
        MvcResult result = mockMvc.perform(post("/instructors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isOk())
                .andReturn();

        Instructor saved = objectMapper.readValue(result.getResponse().getContentAsString(), Instructor.class);
        assertThat(saved.getId()).isNotNull();
        return saved.getId();
    }

    private Long createCourseAndReturnId(Course course, Long instructorId) throws Exception {
        Instructor refInstructor = new Instructor();
        refInstructor.setId(instructorId);
        course.setInstructor(refInstructor);

        MvcResult result = mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andReturn();

        Course saved = objectMapper.readValue(result.getResponse().getContentAsString(), Course.class);
        assertThat(saved.getId()).isNotNull();
        return saved.getId();
    }

    private void createEnrollment(Long studentId, Long courseId, Enrollment enrollment) throws Exception {
        Student refStudent = new Student();
        refStudent.setId(studentId);
        Course refCourse = new Course();
        refCourse.setId(courseId);

        enrollment.setStudent(refStudent);
        enrollment.setCourse(refCourse);

        mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollment)))
                .andExpect(status().isOk());
    }
}
