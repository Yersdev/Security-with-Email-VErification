package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для верификации пользователя.
 */
@Getter
@Setter
public class VerifyUserDto {
    /**
     * Email пользователя, которого необходимо верифицировать.
     */
    private String email;

    /**
     * Код верификации, отправленный пользователю.
     */
    private String verificationCode;
}
