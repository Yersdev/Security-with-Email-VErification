package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для регистрации нового пользователя.
 */
@Getter
@Setter
public class RegisterUserDto {
    /**
     * Имя пользователя.
     */
    private String username;

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
     * @return строковое представление объекта RegisterUserDto
     */
    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
