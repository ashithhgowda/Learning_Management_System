package com.example.lms;

import com.example.lms.entities.Student;
import com.example.lms.entities.Instructor;
import com.example.lms.dao.StudentRepository;
import com.example.lms.dao.InstructorRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public CustomAuthenticationProvider(StudentRepository studentRepository,
                                      InstructorRepository instructorRepository) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName().trim();
        String email = authentication.getCredentials().toString().trim();

        // Check Student login
        Student student = studentRepository.findByName(name).orElse(null);
        if (student != null && student.getEmail().equals(email)) {
            return new UsernamePasswordAuthenticationToken(
                student.getId().toString(),
                email,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"))
            );
        }

        // Check Instructor login
        Instructor instructor = instructorRepository.findByName(name).orElse(null);
        if (instructor != null && instructor.getEmail().equals(email)) {
            return new UsernamePasswordAuthenticationToken(
                instructor.getId().toString(),
                email,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"))
            );
        }

        throw new BadCredentialsException("Invalid username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}