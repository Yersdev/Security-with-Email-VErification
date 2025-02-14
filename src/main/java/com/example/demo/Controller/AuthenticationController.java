package com.example.demo.Controller;

import com.example.demo.dto.LoginUserDto;
import com.example.demo.dto.RegisterUserDto;
import com.example.demo.dto.VerifyUserDto;
import com.example.demo.Model.Users;
import com.example.demo.Responses.LoginResponse;
import com.example.demo.Service.AuthenticationService;
import com.example.demo.Service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для аутентификации пользователей.
 * Обрабатывает регистрацию, вход в систему, верификацию и повторную отправку кода подтверждения.
 */
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    /**
     * Конструктор контроллера аутентификации.
     *
     * @param jwtService сервис для работы с JWT-токенами
     * @param authenticationService сервис аутентификации пользователей
     */
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param registerUserDto данные для регистрации пользователя
     * @return зарегистрированный пользователь
     */
    @PostMapping("/signup")
    public ResponseEntity<Users> register(@RequestBody RegisterUserDto registerUserDto) {
        Users registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Аутентификация пользователя.
     *
     * @param loginUserDto данные для входа пользователя
     * @return ответ с JWT-токеном и временем его истечения
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto){
        //TODO: Lower logic in controller part
        Users authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Верификация пользователя с использованием кода подтверждения.
     *
     * @param verifyUserDto данные для верификации пользователя
     * @return успешный ответ или сообщение об ошибке
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {

        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Повторная отправка кода подтверждения на почту пользователя.
     *
     * @param email адрес электронной почты пользователя
     * @return успешный ответ или сообщение об ошибке
     */
    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
