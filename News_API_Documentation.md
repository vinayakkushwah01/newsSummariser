
# 📰 News Website REST API Documentation

## 🔐 Authentication
All authenticated endpoints require a **Bearer Token** passed in the `Authorization` header:
```
Authorization: Bearer <token>
```

---

## 📚 Endpoints Overview

### 1. Get Article by ID
- **URL**: `/api/article/{id}`
- **Method**: `GET`
- **Auth Required**: ✅ Yes
- **Description**: Retrieves a full news article by its ID.

**Path Parameters**:
- `id` (integer) — Unique article ID.

**Response**:
```json
{
  "id": 101,
  "headline": "Example Headline",
  "content": "Full detailed news content here...",
  "imageUrl": "https://example.com/image.jpg",
  "category": "Politics",
  "publishedAt": "2025-04-29T10:15:30"
}
```

**Errors**:
- 401 Unauthorized
- 404 Not Found

---

### 2. Get Breaking News Headlines
- **URL**: `/api/breakingnews`
- **Method**: `GET`
- **Auth Required**: ❌ No
- **Description**: Returns the 6 most recent breaking news headlines. Updated every 5 minutes.

**Response**:
```json
[
  {
    "id": 201,
    "headline": "Breaking News 1",
    "timestamp": "2025-04-29T10:30:00"
  }
]
```

---

### 3. Get Detailed Breaking News by ID
- **URL**: `/api/breakingnews/{id}`
- **Method**: `GET`
- **Auth Required**: ✅ Yes
- **Description**: Returns full content of a breaking news article by ID.

**Path Parameter**:
- `id` (integer)

**Response**:
```json
{
  "id": 201,
  "headline": "Breaking News Title",
  "content": "Full content...",
  "imageUrl": "https://example.com/image.jpg",
  "category": "National",
  "publishedAt": "2025-04-29T10:30:00"
}
```

---

### 4. Get Homepage News (Uncategorized)
- **URL**: `/api/category`
- **Method**: `GET`
- **Auth Required**: ❌ No
- **Description**: Returns a general list of homepage news, not tied to a specific category.

**Response**:
```json
[
  {
    "id": 301,
    "headline": "General News Headline",
    "summary": "Brief summary...",
    "imageUrl": "https://example.com/image.jpg",
    "publishedAt": "2025-04-29T10:00:00"
  }
]
```

---

### 5. Get News by Category
- **URL**: `/api/category/{category}`
- **Method**: `GET`
- **Auth Required**: ❌ No
- **Description**: Returns news based on a specific category.

**Path Parameter**:
- `category` (string) — Example: `sports`, `politics`, `technology`

**Response**:
```json
[
  {
    "id": 401,
    "headline": "Category News Headline",
    "summary": "Brief summary...",
    "imageUrl": "https://example.com/image.jpg",
    "publishedAt": "2025-04-29T09:45:00"
  }
]
```

---

## 👤 Authentication & User Endpoints

### 6. Login
- **URL**: `/login`
- **Method**: `POST`
- **Auth Required**: ❌ No
- **Description**: Logs in a user and returns a JWT token.

**Request Body**:
```json
{
  "username": "user@example.com",
  "password": "yourpassword"
}
```

**Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

---

### 7. Register
- **URL**: `/register`
- **Method**: `POST`
- **Auth Required**: ❌ No
- **Description**: Registers a new user.

**Request Body**:
```json
{
  "username": "user@example.com",
  "password": "yourpassword",
  "name": "Full Name"
}
```

**Response**:
```json
{
  "message": "User registered successfully"
}
```

---

### 8. Get User Profile
- **URL**: `/user/profile`
- **Method**: `GET`
- **Auth Required**: ✅ Yes
- **Description**: Fetches the current logged-in user's profile.

**Response**:
```json
{
  "username": "user@example.com",
  "name": "Full Name",
  "joinedAt": "2025-01-15"
}
```

---

### 9. Update Password
- **URL**: `/user/update-password`
- **Method**: `PUT`
- **Auth Required**: ✅ Yes
- **Description**: Allows user to change their password.

**Request Body**:
```json
{
  "oldPassword": "oldpassword",
  "newPassword": "newsecurepassword"
}
```

**Response**:
```json
{
  "message": "Password updated successfully"
}
```
