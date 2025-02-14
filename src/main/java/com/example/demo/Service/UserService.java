package com.example.demo.Service;

import com.example.demo.Model.Users;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для управления пользователями.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Конструктор сервиса пользователей.
     *
     * @param userRepository Репозиторий пользователей
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получает список всех пользователей.
     *
     * @return Список пользователей
     */
    public List<Users> getAllUsers() {
        List<Users> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
