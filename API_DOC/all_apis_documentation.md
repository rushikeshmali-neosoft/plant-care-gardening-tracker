# Full API Documentation (Using Token: 77755)

This document contains automated tests for all APIs found in the backend OpenAPI specification.

## ❌ APIs Responding with Errors
These APIs returned a 4xx or 5xx status code (or failed to connect).

### GET /api/v1/users/profile
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/users/profile`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### PUT /api/v1/users/profile
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/users/profile`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
""
```

---

### GET /api/v1/users/preferences
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/users/preferences`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### PUT /api/v1/users/preferences
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/users/preferences`
- **Request Body**: `{}`
- **Response**:
```json
""
```

---

### GET /api/v1/plants/{id}
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### PUT /api/v1/plants/{id}
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
""
```

---

### DELETE /api/v1/plants/{id}
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### PUT /api/v1/notifications/{id}/read
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/notifications/1/read`
- **Request Body**: `{}`
- **Response**:
```json
""
```

---

### GET /api/v1/plants
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### POST /api/v1/plants
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
""
```

---

### GET /api/v1/plants/{plantId}/schedules
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/schedules`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### POST /api/v1/plants/{plantId}/schedules
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/schedules`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
""
```

---

### POST /api/v1/plants/{plantId}/schedules/{scheduleId}/complete
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/schedules/1/complete`
- **Request Body**: `{}`
- **Response**:
```json
""
```

---

### GET /api/v1/plants/{plantId}/logs
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/logs`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### POST /api/v1/plants/{plantId}/logs
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/logs`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
""
```

---

### GET /api/v1/plants/{plantId}/health
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/health`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### POST /api/v1/plants/{plantId}/health
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/health`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
""
```

---

### GET /api/v1/plants/{plantId}/growth
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/growth`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### POST /api/v1/plants/{plantId}/growth
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/growth`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
""
```

---

### GET /api/v1/groups
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/groups`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### POST /api/v1/groups
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/groups`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
""
```

---

### POST /api/v1/groups/{groupId}/plants/{plantId}
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/groups/1/plants/1`
- **Request Body**: `{}`
- **Response**:
```json
""
```

---

### DELETE /api/v1/groups/{groupId}/plants/{plantId}
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/groups/1/plants/1`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### POST /api/v1/auth/register
- **Status Code**: `400 `
- **Request URL**: `http://localhost:8080/api/v1/auth/register`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
{
  "password": "Password is required",
  "email": "Email is required"
}
```

---

### POST /api/v1/auth/refresh
- **Status Code**: `500 `
- **Request URL**: `http://localhost:8080/api/v1/auth/refresh`
- **Request Body**: `{}`
- **Response**:
```json
{
  "status": 500,
  "message": "An internal error occurred. Reference ID: e9e7bdf0-a04a-45c2-8fee-51590d453ffa",
  "timestamp": "2026-05-13T22:08:52.9504082"
}
```

---

### POST /api/v1/auth/login
- **Status Code**: `400 `
- **Request URL**: `http://localhost:8080/api/v1/auth/login`
- **Request Body**: `{"name":"Dummy","title":"Dummy","description":"Dummy","status":"ACTIVE"}`
- **Response**:
```json
{
  "password": "Password is required",
  "email": "Email is required"
}
```

---

### GET /api/v1/tasks/upcoming
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/tasks/upcoming`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### GET /api/v1/tasks/overdue
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/tasks/overdue`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### GET /api/v1/schedules
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/schedules`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### GET /api/v1/plants/{plantId}/health/analysis
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/health/analysis`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### GET /api/v1/notifications
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/notifications`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### GET /api/v1/knowledge/guides
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/knowledge/guides`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### GET /api/v1/knowledge/guides/{id}
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/knowledge/guides/1`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### DELETE /api/v1/plants/{plantId}/schedules/{scheduleId}
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/plants/1/schedules/1`
- **Request Body**: `N/A`
- **Response**:
```json
""
```

---

### DELETE /api/v1/groups/{groupId}
- **Status Code**: `401 `
- **Request URL**: `http://localhost:8080/api/v1/groups/1`
- **Request Body**: `N/A`
- **Response**:
```json
""
```


## ✅ Successful APIs
These APIs returned a 2xx or 3xx status code.

### POST /api/v1/auth/logout
- **Status Code**: `200 `
- **Request URL**: `http://localhost:8080/api/v1/auth/logout`
- **Request Body**: `{}`
- **Response**:
```json
""
```

