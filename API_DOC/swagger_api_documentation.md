# Plant Care Backend API Documentation

This document was generated automatically by testing the Swagger UI interface locally. It demonstrates the expected inputs and responses for key API endpoints.

## 1. Get All Care Schedules
**Description:** Retrieves all care schedules for the authenticated user.
- **Path**: `/api/v1/schedules`
- **Method**: `GET`
- **Authentication Required**: Yes (Bearer Token)
- **Input Data**: None (No parameters required)
- **Response Status Code**: `401 Unauthorized`
- **Response Body**:
  ```json
  (Empty)
  ```
  *Note: The 401 status indicates that this endpoint successfully enforced its security configuration, as no JWT token was provided in the headers during this test.*

---

## 2. Register a New User
**Description:** Registers a new user account on the platform and returns authentication tokens.
- **Path**: `/api/v1/auth/register`
- **Method**: `POST`
- **Authentication Required**: No
- **Input Data**:
  ```json
  {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "password": "Password123!",
    "experienceLevel": "BEGINNER"
  }
  ```
- **Response Status Code**: `201 Created`
- **Response Body**:
  ```json
  {
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoi...",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlblR...",
    "tokenType": "Bearer",
    "expiresIn": 3600,
    "user": {
      "id": 8,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "role": "ROLE_USER",
      "experienceLevel": "BEGINNER"
    }
  }
  ```
  *Note: The registration successfully processed the request body and returned an active JWT token and the created user's profile information.*
