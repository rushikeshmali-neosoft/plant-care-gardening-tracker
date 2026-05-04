# Plant Care & Gardening Tracker - Architecture Design

## 1. Project Overview
**Project Name:** Plant Care & Gardening Tracker  
**Architecture Style:** Modular Monolith  
**Development Approach:** Test Driven Development (TDD) with >85% acceptance rate  
**Tech Stack:** Java 17 + Spring Boot 3.2.x + Angular 18 + PostgreSQL 16

## 2. Architectural Constraints & Boundaries
### ✅ IN SCOPE
- Modular Monolithic Architecture
- REST APIs with JWT Authentication
- Role-Based Access Control (Admin/User)
- PostgreSQL with Hibernate JPA
- WebSocket for real-time updates
- EhCache for L2 caching
- RabbitMQ for async processing
- Docker containerization
- Angular 18 frontend with Material Design

### ❌ OUT OF SCOPE
- Microservices architecture
- Distributed caching (Redis)
- Event streaming (Kafka)
- GraphQL APIs
- IoT sensor integration
- AI/ML disease detection
- Social features
- Native mobile apps

## 3. Module Architecture
### Core Modules (Domain-Driven Design)
```
com.plantcare
├── user-management          # User registration, auth, profiles
├── plant-catalog           # Plant inventory & grouping
├── care-scheduling         # Watering, fertilization, pruning schedules
├── activity-logging        # Care activity tracking
├── growth-tracking         # Photos, measurements, bloom tracking
├── health-monitoring       # Health indicators, symptoms, treatments
├── notification-engine     # Reminders, email, push notifications
├── knowledge-base          # Care guides, plant database, tips
└── shared-infrastructure   # Common utilities, security, config
```

## 4. Database Schema Design
### Core Entities
```sql
-- User Management
users (id, email, password_hash, role, experience_level, preferences, created_at, updated_at)

-- Plant Catalog
plants (id, user_id, scientific_name, common_name, species, variety, 
        purchase_date, source, location_type, room_garden, status, 
        created_at, updated_at)

-- Plant Groups
plant_groups (id, user_id, name, description, created_at)
plant_group_members (plant_id, group_id)

-- Care Schedules
care_schedules (id, plant_id, schedule_type, frequency_days, 
                next_due_date, last_performed_date, seasonal_adjustment, 
                created_at, updated_at)

-- Activity Logs
activity_logs (id, plant_id, activity_type, performed_date, 
               details_json, notes, created_at)

-- Growth Tracking
plant_photos (id, plant_id, photo_url, caption, taken_date, uploaded_at)
plant_measurements (id, plant_id, height_cm, width_cm, 
                    measurement_date, notes)
bloom_records (id, plant_id, start_date, end_date, bloom_count, notes)
harvest_records (id, plant_id, harvest_date, yield_amount, yield_unit, notes)

-- Health Monitoring
health_indicators (id, plant_id, health_status, recorded_date, notes)
symptom_logs (id, plant_id, symptom_type, description, observed_date)
treatment_history (id, plant_id, treatment_type, product_used, 
                   quantity, application_date, effectiveness)
environmental_logs (id, plant_id, temperature_c, humidity_percent, 
                    light_level, recorded_date)

-- Knowledge Base
care_guides (id, plant_type, title, content, created_at)
problem_solutions (id, problem_type, symptoms, causes, solutions)
seasonal_tips (id, season, tip_content, applicable_regions)
plant_database (id, scientific_name, common_name, care_requirements, 
                growth_characteristics)
user_tips (id, user_id, plant_type, tip_content, created_at, likes_count)
```

## 5. API Design Standards
### REST API Conventions
- Base Path: `/api/v1/`
- HTTP Methods: GET, POST, PUT, PATCH, DELETE
- Status Codes: 200, 201, 400, 401, 403, 404, 500
- Response Format: JSON with consistent structure
- Pagination: `?page=1&size=20&sort=createdAt,desc`
- Filtering: `?status=active&location=indoor`

### API Endpoints Structure
```
/auth
  POST /register          # User registration
  POST /login             # User login
  POST /refresh           # Refresh token
  POST /logout            # User logout
  POST /password-reset    # Password reset request
  POST /password-reset/confirm  # Password reset confirmation

/users
  GET  /profile           # Get user profile
  PUT  /profile           # Update user profile
  GET  /preferences       # Get user preferences
  PUT  /preferences       # Update preferences

/plants
  GET  /                  # List plants with pagination
  POST /                  # Add new plant
  GET  /{id}              # Get plant details
  PUT  /{id}              # Update plant
  DELETE /{id}            # Delete plant
  GET  /{id}/schedule     # Get plant care schedule
  POST /{id}/schedule     # Create care schedule
  GET  /{id}/activities   # Get plant activity logs
  POST /{id}/activities   # Log activity
  GET  /{id}/growth       # Get growth tracking data
  POST /{id}/growth       # Add growth data
  GET  /{id}/health       # Get health monitoring data
  POST /{id}/health       # Add health data

/groups
  GET  /                  # List plant groups
  POST /                  # Create plant group
  GET  /{id}              # Get group details
  PUT  /{id}              # Update group
  DELETE /{id}            # Delete group
  POST /{id}/plants/{plantId}  # Add plant to group
  DELETE /{id}/plants/{plantId} # Remove plant from group

/tasks
  GET  /upcoming          # Get upcoming care tasks
  GET  /overdue           # Get overdue tasks
  POST /{taskId}/complete # Mark task as complete

/notifications
  GET  /                  # Get user notifications
  POST /preferences       # Set notification preferences
  PUT  /{id}/read         # Mark notification as read

/knowledge
  GET  /guides            # Get care guides
  GET  /guides/{id}       # Get specific guide
  GET  /problems          # Search problem solutions
  GET  /seasonal-tips     # Get seasonal tips
  GET  /plant-database    # Search plant database
  GET  /user-tips         # Get community tips
  POST /user-tips         # Submit user tip
```

## 6. Security Architecture
### Authentication & Authorization
- **JWT-based stateless authentication**
- **Access Token:** Short-lived (15 minutes)
- **Refresh Token:** Long-lived (7 days)
- **Password Hashing:** BCrypt with salt
- **Role-Based Access Control:**
  - `ROLE_USER`: Standard user permissions
  - `ROLE_ADMIN`: Full system access

### Security Measures
- Input validation using Spring Validation
- SQL injection prevention via JPA parameter binding
- XSS protection via content sanitization
- CSRF protection for state-changing operations
- Rate limiting for authentication endpoints
- Secure password reset via email OTP

## 7. Real-Time Communication
### WebSocket Configuration
- **Endpoint:** `/ws/plant-care`
- **Protocol:** STOMP over WebSocket
- **Topics:**
  - `/topic/upcoming-tasks/{userId}`
  - `/topic/plant-updates/{plantId}`
  - `/topic/notifications/{userId}`
- **Use Cases:**
  - Real-time task reminders
  - Live plant status updates
  - Instant notification delivery

## 8. Async Processing & Events
### RabbitMQ Integration
- **Exchange:** `plant-care.exchange`
- **Queues:**
  - `email.notifications` - Email delivery
  - `task.calculation` - Schedule recalculation
  - `health.alerts` - Health monitoring alerts
- **Event Types:**
  - `PlantAddedEvent`
  - `CareActivityLoggedEvent`
  - `TaskDueEvent`
  - `HealthAlertEvent`

## 9. Caching Strategy
### EhCache Configuration
- **L2 Cache:** Hibernate second-level cache
- **Cache Regions:**
  - `plant-catalog` - Plant details (TTL: 1 hour)
  - `care-guides` - Knowledge base (TTL: 24 hours)
  - `user-sessions` - User data (TTL: 30 minutes)
- **Cache Invalidation:**
  - Write-through for critical data
  - Time-based expiration for reference data

## 10. Transaction Management
### Spring @Transactional
- **Propagation:** REQUIRED (default)
- **Isolation:** READ_COMMITTED
- **Timeout:** 30 seconds
- **Read-Only:** For query operations
- **Rollback For:** All RuntimeExceptions

### Critical Transactions
1. User registration (create user + send welcome email)
2. Plant addition (create plant + initial schedule)
3. Activity logging (log + update schedule)
4. Health monitoring (symptom + treatment tracking)

## 11. Error Handling & Logging
### Global Exception Handler
- **Custom Exceptions:**
  - `PlantNotFoundException`
  - `InvalidScheduleException`
  - `UnauthorizedAccessException`
  - `ValidationException`
- **Response Structure:**
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid plant data provided",
  "path": "/api/v1/plants",
  "details": ["Species cannot be empty"]
}
```

### Structured Logging (Logback + SLF4J)
- **Log Levels:** ERROR, WARN, INFO, DEBUG
- **Log Format:** JSON for machine parsing
- **Log Context:** User ID, Request ID, Session ID
- **Log Aggregation:** Centralized log collection

## 12. Performance Optimization
### Database Optimization
- **Indexing Strategy:**
  - Foreign key columns
  - Frequently queried fields (status, user_id)
  - Date-based queries (created_at, next_due_date)
- **Query Optimization:**
  - Fetch joins to avoid N+1 queries
  - Pagination for large result sets
  - Query caching for repetitive queries

### Application Optimization
- **Connection Pooling:** HikariCP
- **Async Processing:** Heavy operations via RabbitMQ
- **Response Compression:** GZIP for large responses
- **Static Content:** CDN-ready asset serving

## 13. Deployment Architecture
### Docker Configuration
```dockerfile
# Backend Dockerfile
FROM openjdk:17-jdk-slim
COPY target/plant-care-tracker.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:16
    environment:
      POSTGRES_DB: plantcare
      POSTGRES_USER: plantcare_user
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"

  rabbitmq:
    image: rabbitmq:3-management
    environment:
      RABBITMQ_DEFAULT_USER: ${RABBITMQ_USER}
      RABBITMQ_DEFAULT_PASS: ${RABBITMQ_PASSWORD}
    ports:
      - "5672:5672"
      - "15672:15672"

  backend:
    build: ./backend
    environment:
      SPRING_PROFILES_ACTIVE: docker
      DB_HOST: postgres
      DB_PORT: 5432
      RABBITMQ_HOST: rabbitmq
    ports:
      - "8080:8080"
    depends_on:
      - postgres
      - rabbitmq

  frontend:
    build: ./frontend
    ports:
      - "4200:80"
    depends_on:
      - backend

volumes:
  postgres_data:
```

## 14. Monitoring & Observability
### Health Checks
- **Spring Boot Actuator Endpoints:**
  - `/actuator/health` - Application health
  - `/actuator/metrics` - Performance metrics
  - `/actuator/info` - Application info
  - `/actuator/prometheus` - Prometheus metrics

### Key Metrics
- API response times (p95, p99)
- Database query performance
- Cache hit/miss ratios
- Active user sessions
- Task completion rates

## 15. TDD Implementation Strategy
### Test Structure
```
src/test/java/com/plantcare/
├── unit/
│   ├── service/          # Service layer tests
│   ├── controller/       # Controller tests
│   └── util/            # Utility tests
├── integration/
│   ├── repository/       # Repository tests with Testcontainers
│   ├── api/             # API integration tests
│   └── security/        # Security integration tests
└── e2e/
    └── features/        # End-to-end feature tests
```

### Test Coverage Requirements
- **Unit Tests:** >90% line coverage
- **Integration Tests:** All critical flows
- **API Tests:** All endpoints with positive/negative cases
- **Acceptance Rate:** >85% as per requirements

### TDD Workflow
1. Write failing test for new feature
2. Implement minimal code to pass test
3. Refactor while keeping tests green
4. Repeat for each requirement
5. Generate `TDD_REVIEW.md` with pass/fail logs

## 16. Frontend Architecture (Angular 18)
### Component Structure
```
src/app/
├── core/                 # Singleton services, interceptors
├── shared/              # Shared components, pipes, directives
├── features/            # Feature modules
│   ├── auth/            # Authentication
│   ├── dashboard/       # Main dashboard
│   ├── plants/          # Plant management
│   ├── schedules/       # Care scheduling
│   ├── growth/          # Growth tracking
│   ├── health/          # Health monitoring
│   ├── knowledge/       # Knowledge base
│   └── notifications/   # Notifications
└── layouts/             # Layout components
```

### State Management
- **Angular Signals** for reactive state
- **Services with BehaviorSubject** for shared state
- **NgRx Store** for complex state management (optional)
- **Local Storage** for user preferences

## 17. Implementation Roadmap
### Phase 1: Foundation (Week 1-2)
1. Project setup with Spring Boot + Angular
2. User management module (auth, profiles)
3. Basic plant catalog CRUD
4. Database schema implementation

### Phase 2: Core Features (Week 3-4)
1. Care scheduling system
2. Activity logging
3. Growth tracking (photos, measurements)
4. Health monitoring basics

### Phase 3: Advanced Features (Week 5-6)
1. Notification engine
2. Knowledge base
3. Real-time updates (WebSocket)
4. Async processing (RabbitMQ)

### Phase 4: Polish & Deployment (Week 7-8)
1. Responsive UI refinement
2. Performance optimization
3. Security hardening
4. Docker deployment
5. Testing & documentation

## 18. Risk Mitigation
### Technical Risks
1. **Database Performance:** Implement indexing strategy early
2. **Real-time Scaling:** Use WebSocket with connection pooling
3. **Cache Consistency:** Implement cache invalidation patterns
4. **Security Vulnerabilities:** Regular security scanning

### Project Risks
1. **Scope Creep:** Strict adherence to documented requirements
2. **Testing Coverage:** Continuous TDD enforcement
3. **Integration Complexity:** Incremental feature rollout
4. **Deployment Issues:** Comprehensive Docker testing

## 19. Success Criteria
### Technical Success
- All 56 KPIs from KPI_STATEMENT.md implemented
- >85% test acceptance rate
- API response times <300ms
- Zero critical security vulnerabilities
- Successful Docker deployment

### Business Success
- User registration and login working
- Complete plant lifecycle management
- Effective reminder system
- Useful knowledge base access
- Multi-device responsive experience

---

*This architecture design follows all constraints from the provided documentation files and serves as the blueprint for implementation.*