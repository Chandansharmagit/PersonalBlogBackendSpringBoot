package org.example.personal_websitebackend.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public").permitAll()
                        .anyRequest().authenticated()
                )
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                );

        return http.build();
    }

//    public void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication().withUser("chandan").password("chandan").roles("NORMAL");
//    }
}