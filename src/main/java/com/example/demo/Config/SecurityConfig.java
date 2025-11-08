package com.example.demo.Config;

import java.security.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(
                        org.springframework.security.config.annotation.web.builders.HttpSecurity http)
                        throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/events/").hasAnyRole("ADMIN", "USER", "TEACHER","STUDENT")
                                                .requestMatchers("/course/").hasRole("ADMIN")
                                                .requestMatchers("/user/", "/thesis/").hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/teacher/", "/attendance/", "/reports/").hasAnyRole("USER", "ADMIN", "TEACHER","STUDENT")
                                                .requestMatchers("/", "/css/", "/js/", "/images/", "/uploads/",
                                                                "/include/",
                                                                "/fonts/", "/reports/", 
                                                                "/register/","/relationship/","/ui/","/admin/")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .usernameParameter("txtUsername")
                                                .passwordParameter("txtPassword")
                                                .defaultSuccessUrl("/", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .permitAll())
                                .csrf(Customizer.withDefaults());

                return http.build();
        }
}
