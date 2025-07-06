🎬 Movie App Backend

This is the backend service for a mobile-based movie streaming application. Built with Spring Boot, it supports secure content management, JWT-based authentication, and admin-friendly APIs for series and episodes.
🚀 Features
✅ User Authentication

    Sign Up / Sign In with encrypted passwords (BCrypt)

    Stateless session via JWT token

    Role-based access control (ROLE_USER, ROLE_ADMIN)

🎞 Content Management

    Series API: Create, update, list, delete

    Episode API: Add episode by video URL, manage episode list

    Auto-update series' video URL when a new episode is added

    Banner API: Promotional content linked to a series

    Home API that returns:

        User info

        List of series

        List of banners

☁ Bunny CDN Integration

    (Optional) Upload files to BunnyCDN via /admin/bunny/upload endpoint

    Store video and thumbnail URLs in the database

🔐 Security

    Spring Security + JWT custom filter

    Protected routes (/admin/**, /series/**, etc.)

    Global exception handling with proper error messages

🧩 Architecture

    DTO/Entity separation using MapStruct

    Layered structure:
    Controller → Service → Repository → DB

    Clean and scalable project structure

🛠 Tech Stack

    Java 21

    Spring Boot 3

    Spring Security + JWT (jjwt)

    MapStruct

    Hibernate + JPA

    PostgreSQL (optional)

    Gradle

    Docker (optional)

📡 API Endpoints (Sample)
🔐 Auth
Method	Endpoint	Description
POST	/auth/sign-up	Register new user
POST	/auth/sign-in	Login & receive token

🔑 Token is returned as Authorization: Bearer <token>
