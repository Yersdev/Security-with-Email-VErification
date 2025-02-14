package com.example.demo.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с JWT-токенами: генерация, проверка и извлечение данных.
 */
@Service
@Slf4j
public class JwtService {

    /**
     * Секретный ключ для подписи JWT.
     * Значение должно быть передано через application.properties или application.yml.
     */
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    /**
     * Время жизни токена в миллисекундах.
     */
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Извлекает имя пользователя (subject) из JWT-токена.
     *
     * @param token JWT-токен
     * @return Имя пользователя (subject)
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлекает указанный клейм из JWT-токена.
     *
     * @param token JWT-токен
     * @param claimsResolver Функция для извлечения клейма
     * @param <T> Тип возвращаемого значения
     * @return Значение клейма
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Генерирует JWT-токен для пользователя.
     *
     * @param userDetails Данные пользователя
     * @return Сгенерированный JWT-токен
     */
    public String generateToken(UserDetails userDetails) {
        log.info("Генерация токена для пользователя: {}", userDetails.getUsername());
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Генерирует JWT-токен с дополнительными клеймами.
     *
     * @param extraClaims Дополнительные клеймы
     * @param userDetails Данные пользователя
     * @return Сгенерированный JWT-токен
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Возвращает время жизни токена.
     *
     * @return Время жизни токена в миллисекундах
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Создает JWT-токен с заданными параметрами.
     *
     * @param extraClaims Дополнительные клеймы
     * @param userDetails Данные пользователя
     * @param expiration Время жизни токена
     * @return Сгенерированный JWT-токен
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Проверяет, является ли токен действительным для заданного пользователя.
     *
     * @param token JWT-токен
     * @param userDetails Данные пользователя
     * @return true, если токен действителен, иначе false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Проверяет, истек ли срок действия токена.
     *
     * @param token JWT-токен
     * @return true, если токен истек, иначе false
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлекает дату истечения токена.
     *
     * @param token JWT-токен
     * @return Дата истечения токена
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлекает все клеймы из JWT-токена.
     *
     * @param token JWT-токен
     * @return Объект Claims с данными токена
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получает ключ для подписи JWT, декодируя его из Base64.
     *
     * @return Ключ для подписи
     */
    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
