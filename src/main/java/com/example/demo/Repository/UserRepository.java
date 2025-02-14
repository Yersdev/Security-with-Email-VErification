package com.example.demo.Repository;

import com.example.demo.Model.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями {@link Users}.
 * Предоставляет методы для поиска пользователей по различным критериям.
 */
public interface UserRepository extends CrudRepository<Users, Long> {

    /**
     * Ищет пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return {@link Optional}, содержащий пользователя, если найден
     */
    Optional<Users> findByUsername(String username);

    /**
     * Ищет пользователя по email.
     *
     * @param email email пользователя
     * @return {@link Optional}, содержащий пользователя, если найден
     */
    Optional<Users> findByEmail(String email);

    /**
     * Ищет пользователя по коду верификации.
     *
     * @param verificationCode код верификации
     * @return {@link Optional}, содержащий пользователя, если найден
     */
    Optional<Users> findByVerificationCode(String verificationCode);

    /**
     * Возвращает пользователя по email.
     *
     * @param email email пользователя
     * @return объект {@link Users}, если найден, иначе {@code null}
     */
    Users findUsersByEmail(String email);
}
