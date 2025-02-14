package com.example.demo.Responses;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс, представляющий ответ при успешной аутентификации пользователя.
 * Содержит JWT-токен и срок его действия.
 */
@Getter
@Setter
public class LoginResponse {
    private String token;
    private long expiresIn;

    /**
     * Конструктор для создания объекта ответа при входе в систему.
     *
     * @param token     JWT-токен аутентификации
     * @param expiresIn время истечения токена в секундах
     */
    public LoginResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    /**
     * Получает JWT-токен.
     *
     * @return токен
     */
    public String getToken() {
        return token;
    }

    /**
     * Устанавливает JWT-токен.
     *
     * @param token токен
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Получает время истечения токена.
     *
     * @return время истечения токена в секундах
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Устанавливает время истечения токена.
     *
     * @param expiresIn время истечения токена в секундах
     */
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
