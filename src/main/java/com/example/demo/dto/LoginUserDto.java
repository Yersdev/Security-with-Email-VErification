package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для передачи данных при входе пользователя в систему.
 */
@Getter
@Setter
public class LoginUserDto {
    /**
     * Email пользователя.
     */
    private String email;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Переопределенный метод toString для удобного отображения информации о пользователе.
     *
     * @return строковое представление объекта LoginUserDto
     */
    @Override
    public String toString() {
        return "LoginUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
