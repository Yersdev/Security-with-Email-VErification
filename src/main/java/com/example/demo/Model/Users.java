package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Класс модели пользователя, реализующий интерфейс {@link UserDetails}
 * для интеграции с Spring Security.
 */
@Entity
@Table(name="users")
@Getter
@NoArgsConstructor
@Setter
public class Users implements UserDetails {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя (уникальное, обязательное).
     */
    @Column(unique=true, nullable=false)
    private String username;

    /**
     * Email пользователя (уникальный, обязательный).
     */
    @Column(unique=true, nullable=false)
    private String email;

    /**
     * Пароль пользователя (обязательный).
     */
    @Column(name = "password", nullable=false)
    private String password;

    /**
     * Флаг, указывающий, активирован ли пользователь.
     */
    private boolean enabled;

    /**
     * Код верификации для подтверждения email.
     */
    @Column(name = "verification_code")
    private String verificationCode;

    /**
     * Дата и время истечения срока действия кода верификации.
     */
    @Column(name = "verification_expiration")
    private LocalDateTime verificationExpireAt;

    /**
     * Конструктор с параметрами.
     *
     * @param id                 идентификатор пользователя
     * @param username           имя пользователя
     * @param email              email пользователя
     * @param password           пароль пользователя
     * @param enabled            статус активации
     * @param verificationCode   код верификации
     * @param verificationExpireAt время истечения кода верификации
     */
    public Users(Long id, String username, String email, String password, boolean enabled, String verificationCode, LocalDateTime verificationExpireAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.verificationCode = verificationCode;
        this.verificationExpireAt = verificationExpireAt;
    }

    /**
     * Конструктор для создания пользователя с минимальными параметрами.
     *
     * @param password пароль пользователя
     * @param email    email пользователя
     * @param username имя пользователя
     */
    public Users(String password, String email, String username) {
        this.password = password;
        this.email = email;
        this.username = username;
    }

    /**
     * Возвращает список полномочий пользователя.
     *
     * @return пустой список (пользователь не имеет ролей)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getVerificationExpireAt() {
        return verificationExpireAt;
    }

    public void setVerificationExpireAt(LocalDateTime verificationExpireAt) {
        this.verificationExpireAt = verificationExpireAt;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", verificationCode='" + verificationCode + '\'' +
                ", verificationExpireAt=" + verificationExpireAt +
                '}';
    }
}
