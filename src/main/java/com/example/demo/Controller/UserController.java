package com.example.demo.Controller;

import com.example.demo.Model.Users;
import com.example.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для управления пользователями.
 * Предоставляет эндпоинты для получения текущего аутентифицированного пользователя
 * и списка всех пользователей.
 */
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    /**
     * Конструктор контроллера пользователей.
     *
     * @param userService сервис для работы с пользователями
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получает данные текущего аутентифицированного пользователя.
     *
     * @return объект пользователя, взятый из контекста безопасности
     */
    @GetMapping("/me")
    public ResponseEntity<Users> authenticatedUser() {
        //TODO: Lower logic in controller part
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = (Users) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список всех пользователей системы
     */
    @GetMapping("/")
    public ResponseEntity<List<Users>> allUsers() {
        List<Users> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
