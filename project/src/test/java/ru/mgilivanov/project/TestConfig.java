package ru.mgilivanov.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.mgilivanov.project.config.SecurityConfig;
import ru.mgilivanov.project.controller.ExceptionController;

@Configuration
public class TestConfig {

    @Autowired
    private final UserDetailsService userDetailsService;

    public TestConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public ExceptionController exceptionController() {
        return new ExceptionController();
    }

    @Bean
    public SecurityConfig securityConfig() {
        return new SecurityConfig(userDetailsService);
    }
}
