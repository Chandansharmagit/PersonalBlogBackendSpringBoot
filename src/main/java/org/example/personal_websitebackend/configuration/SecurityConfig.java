package org.example.personal_websitebackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/reset_password", "/finding", "/saving_email", "/finding_user", "/sending_email", "/finding_user_by_email","/user_finding_email","/reset_password","/register","/http://localhost:3000/new_pass?token").permitAll()
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll()  // Permit all requests without authentication
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // Endpoint for form login
                        .defaultSuccessUrl("/home", true) // Redirect to /home on successful login
                        .permitAll()
                )
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.addAllowedOrigin("*"); // Allow all origins
                            config.addAllowedHeader("*"); // Allow all headers
                            config.addAllowedMethod("*"); // Allow all methods
                            return config;
                        })
                );

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

