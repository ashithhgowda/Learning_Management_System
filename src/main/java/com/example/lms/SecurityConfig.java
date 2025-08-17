package com.example.lms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationProvider authProvider;

    public SecurityConfig(CustomAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/students/**").hasAuthority("ROLE_STUDENT")
                .requestMatchers("/instructors/**").hasAuthority("ROLE_INSTRUCTOR")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, 
                                                      HttpServletResponse response,
                                                      org.springframework.security.core.Authentication authentication) 
                                                      throws IOException {
                        String role = authentication.getAuthorities().iterator().next().getAuthority();
                        String userId = authentication.getName();
                        if (role.equals("ROLE_STUDENT")) {
                            response.sendRedirect("/students/" + userId);
                        } else if (role.equals("ROLE_INSTRUCTOR")) {
                            response.sendRedirect("/instructors/" + userId);
                        }
                    }
                })
                .permitAll()
            )
            .httpBasic(withDefaults())
            .authenticationProvider(authProvider);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}