# Security


```markdown
# Security with Email Verification

This project demonstrates the implementation of email verification and security mechanisms in a Spring Boot application. It includes user registration, authentication with JWT, email verification, and secure password storage.

## Features
- User registration with email verification
- Secure password storage using bcrypt
- JWT-based authentication
- Password reset functionality
- Email service integration

## Technologies
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Mail
- MySQL/PostgreSQL (or any preferred database)

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Yersdev/Security-with-Email-VErification.git
   ```

2. Configure the `.env` file with your environment variables:
    - `SPRING_DATASOURCE_URL`
    - `SPRING_DATASOURCE_USERNAME`
    - `SPRING_DATASOURCE_PASSWORD`
    - `MAIL_HOST`
    - `MAIL_PORT`
    - `MAIL_USERNAME`
    - `MAIL_PASSWORD`
    - `JWT_SECRET_KEY`

3. Install dependencies:
   ```bash
   mvn install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### Register User
- **POST** `/api/auth/register`
- Request Body: `{ "email": "user@example.com", "password": "yourpassword", "name": "User" }`

### Login User
- **POST** `/api/auth/login`
- Request Body: `{ "email": "user@example.com", "password": "yourpassword" }`

### Verify Email
- **GET** `/api/auth/verify-email/{token}`

### Password Reset
- **POST** `/api/auth/reset-password`

## Contributing
Feel free to fork and submit pull requests.

## License
MIT License
```

You can copy and paste this code into your project’s `README.md` file.