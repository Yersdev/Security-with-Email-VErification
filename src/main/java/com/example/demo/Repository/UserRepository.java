package com.example.demo.Repository;

import com.example.demo.Model.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByVerificationCode(String verificationCode);
    Users findUsersByEmail(String email);
}

