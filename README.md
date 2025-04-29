
# 📰 News Website Backend (Spring Boot)

This is a RESTful backend for a news website built with **Spring Boot**. It serves breaking news, category-based news, user authentication, and more.

---

## 🚀 Features

- 🔒 JWT-based Authentication
- 🗞️ Breaking News & Full Articles
- 📂 Category-wise News API
- 👥 User Registration, Login, Profile & Password Update
- 📡 RESTful Endpoints with JSON responses
- 📅 Automatic News Refresh every 5 minutes (for breaking news)

---

## ⚙️ Tech Stack

- Java 19+
- Spring Boot
- Spring Security
- JWT Token Authentication
- Maven
- H2 / MySQL (configurable)

---

## 🛠️ Requirements

- **Java Development Kit (JDK)** 19 or higher
- **Maven** (for dependency management)
- **Postman** or any HTTP client (for testing APIs)

---

## 📦 How to Run

1. **Clone the repository**  
   ```bash
   git clone https://github.com/vinayakkushwah01/newsSummariser.git
   cd news-api-backend
   ```

2. **Build the project**  
   ```bash
   mvn clean install
   ```

3. **Run the application**  
   ```bash
   mvn spring-boot:run
   ```

4. Access it at:  
   ```
   http://localhost:8080
   ```

---

## 🔐 Authentication

Use `/login` to obtain a JWT token and include it in the `Authorization` header as:
```
Authorization: Bearer <token>
```

---

## 📄 API Documentation

Check out [News_API_Documentation.md](./News_API_Documentation.md) for complete API details.

---

## 🤝 Contribution

Feel free to fork this repo, open issues, or make pull requests to improve it.

---

## 📃 License

This project is licensed under the MIT License.
