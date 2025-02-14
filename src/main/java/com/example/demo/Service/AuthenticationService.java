package com.example.demo.Service;

import com.example.demo.dto.LoginUserDto;
import com.example.demo.dto.RegisterUserDto;
import com.example.demo.dto.VerifyUserDto;
import com.example.demo.Model.Users;
import com.example.demo.Repository.UserRepository;
import com.example.demo.exception.*;
import com.example.demo.util.HtmlMessage;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/**
 * Сервис для управления аутентификацией и регистрацией пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    /**
     * Регистрация нового пользователя.
     *
     * @param input данные пользователя для регистрации
     * @return сохраненный пользователь
     */
    public Users signup(RegisterUserDto input) {
        Users user = new Users(passwordEncoder.encode(input.getPassword()), input.getEmail(), input.getUsername());
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationExpireAt(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        sendVerificationEmail(user);
        return userRepository.save(user);
    }

    /**
     * Аутентификация пользователя.
     *
     * @param input данные для входа (email и пароль)
     * @return аутентифицированный пользователь
     * @throws UserNotFoundException если пользователь не найден или не верифицирован
     */
    public Users authenticate(LoginUserDto input) {
        Users user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.isEnabled()) {
            throw new AccountNotVerifiedException("Account not verified. Please verify your account.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return user;
    }

    /**
     * Подтверждение учетной записи пользователя с помощью кода верификации.
     *
     * @param input DTO с email и кодом верификации
     * @throws UserNotFoundException если пользователь не найден, код неверный или просрочен
     */
    public void verifyUser(VerifyUserDto input) {
        Optional<Users> optionalUser = userRepository.findByEmail(input.getEmail());
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user.getVerificationExpireAt().isBefore(LocalDateTime.now())) {
                throw new VerificationCodeHasExpiredException("Verification code has expired");
            }
            if (user.getVerificationCode().equals(input.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationExpireAt(null);
                userRepository.save(user);
            } else {
                throw new InvalidVerificationCodeException("Invalid verification code");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Повторная отправка кода верификации на email пользователя.
     *
     * @param email email пользователя
     * @throws AccountHasAlreadyVerifiedException если пользователь уже верифицирован или не найден
     */
    public void resendVerificationCode(String email) {
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user.isEnabled()) {
                throw new AccountHasAlreadyVerifiedException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpireAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Отправка письма с кодом подтверждения пользователю.
     *
     * @param user пользователь, которому отправляется письмо
     */
    private void sendVerificationEmail(Users user) { //TODO: Update with company logo
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = HtmlMessage.htmlMessage(verificationCode);

        try {
            emailService.sendSimpleMail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Генерация случайного шестизначного кода верификации.
     *
     * @return строка с шестизначным кодом
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
