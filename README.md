# Security with Email Verification 🚀🔒

## Описание 📝

Этот проект реализует систему аутентификации и регистрации пользователей с проверкой электронной почты. 📧
После регистрации пользователю отправляется письмо с подтверждением, которое необходимо для активации учетной записи. ✅

## Стек технологий 🛠️

- **Backend**: Java ☕, Spring Boot 6, Spring Security, JWT, PostgreSQL 🐘
- **Frontend**: (Ожидается разработка) 🎨
- **Контейнеризация**: Docker 🐳, Docker Compose

## Функциональность 🎯

- ✅ Регистрация пользователей с валидацией данных
- 📩 Подтверждение email через ссылку в письме
- 🔐 Аутентификация и авторизация с JWT
- 🔄 Восстановление пароля через email
- 🏷️ Роли пользователей (USER, ADMIN)
- 🔒 Защищенные API-эндпоинты

## Установка и запуск 🚀

### 1. Клонирование репозитория 💾

```sh
git clone https://github.com/Yersdev/Security-with-Email-VErification.git
cd Security-with-Email-VErification
```

### 2. Настройка переменных окружения ⚙️

Создайте файл `.env` в корне проекта и укажите необходимые переменные:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/security_db
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
JWT_SECRET=your_jwt_secret
EMAIL_HOST=smtp.example.com
EMAIL_PORT=587
EMAIL_USERNAME=your_email@example.com
EMAIL_PASSWORD=your_email_password
```

### 3. Запуск проекта в Docker 🐳

#### Запуск backend 🎯

```sh
docker-compose up -d --build
```

#### Запуск frontend (после его разработки) 🎨

```sh
cd frontend
npm install
npm run dev
```

## API 📌

Документация API доступна в Swagger по адресу:

```
http://localhost:8080/swagger-ui.html
```

## Контакты 📧

Автор: [Yersdev](https://github.com/Yersdev)
