# 🎬 Movie App Backend

This is the backend service for a mobile-based movie streaming application. It is built with **Spring Boot**, using **JWT authentication**, and provides secure endpoints for user login, signup, series listing, and promotional banners.

---

## 🚀 Features

- ✅ **User Authentication**
  - Sign up / Sign in with secure password encryption (BCrypt)
  - JWT token generation and validation
  - Stateless authentication via `Authorization: Bearer <token>`

- 🎞 **Content Management**
  - Series listing (title, status, image)
  - Banner display with linked series (movie inside banner)
  - Home API returns user + series + banners as one response

- 🔐 **Security**
  - Spring Security with custom JWT filter
  - Restricted access to protected endpoints
  - Global exception handling

- 🧩 **Architecture**
  - DTO/Entity separation using MapStruct
  - Layered structure: Controller → Service → Repository → DB
  - Clean, maintainable, scalable design

---

## 🛠 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT (io.jsonwebtoken)
- MapStruct
- Hibernate + JPA
- Maven or Gradle

---

## 📡 API Endpoints

| Method | Endpoint           | Description                |
|--------|--------------------|----------------------------|
| POST   | `/auth/sign-up`    | Register a new user        |
| POST   | `/auth/sign-in`    | Log in and get JWT token   |
| GET    | `/home?userId=1`   | Get homepage content       |
| GET    | `/series`          | Get all series (auth req.) |

> ✅ Token is required for `/home` and `/series` via `Authorization` header.

---

## 🧪 Testing

You can use **Postman** or any HTTP client to test the API.

1. Register or login using `/auth/sign-up` or `/auth/sign-in`
2. Copy the returned token
3. Send it with protected endpoints:

