package com.example.demo.Config;

import com.example.demo.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

/**
 * Конфигурационный класс приложения для настройки аутентификации и управления пользователями.
 */
@Configuration
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    /**
     * Конструктор для инициализации репозитория пользователей.
     *
     * @param userRepository репозиторий пользователей
     */
    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Создает бин для кодирования паролей с использованием BCrypt.
     *
     * @return экземпляр {@link BCryptPasswordEncoder}
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Определяет сервис загрузки информации о пользователе по имени пользователя (email).
     *
     * @return {@link UserDetailsService}, реализованный с использованием репозитория пользователей
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        new ArrayList<>())) // Подключите роли или права доступа, если необходимо
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Создает менеджер аутентификации на основе конфигурации Spring Security.
     *
     * @param config конфигурация аутентификации
     * @return {@link AuthenticationManager}
     * @throws Exception в случае ошибки конфигурации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Определяет провайдер аутентификации, использующий DAO-методы для проверки учетных данных.
     *
     * @return {@link AuthenticationProvider}
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}