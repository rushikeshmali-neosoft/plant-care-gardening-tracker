

#  Project Scope & Boundaries :
==============================


🌿 Project: Plant Care & Gardening Tracker



1️⃣  Project In-Scope :
========================

# User & Account Management
----------------------------

* User Registration (name + email + password)
* User Login (secure authentication)
* Password Reset via email (send otp on email)
* Profile Management (preferences, experience level)
* Session Management (JWT-based, session time-out )



# Plant Catalog & Inventory
----------------------------

* Add / Update / Delete plant records
* Store:

  * Scientific name
  * Common name
  * Care requirements
* Location tracking (indoor/outdoor/room)
* Plant grouping (collections)
* Plant lifecycle status (active, dormant, gifted, deceased)

---

# Care Schedule Management
----------------------------

* Watering schedule (frequency-based)
* Fertilization schedule
* Pruning/trimming schedule
* Repotting reminders
* Seasonal schedule adjustments

---

# Care Activity Logging
----------------------------

* Log watering (date, amount, method)
* Log fertilization (type, quantity)
* Log pruning activities
* Pest/Disease tracking
* General notes per plant


# Growth Tracking
-------------------

* Photo timeline (upload + organize)
* Measurement logging (height, width)
* Bloom tracking
* Harvest tracking (for edible plants)
* Growth charts (visualization)


# Reminders & Notifications
----------------------------

* Upcoming tasks dashboard (today, tomorrow, week)
* Custom reminders per plant/task
* Email notifications for overdue tasks
* Mobile push notifications (optional)
* Weather-based care suggestions (season-driven)


# Plant Health Monitoring
--------------------------

* Health indicators (Excellent → Critical)
* Symptom logging
* Treatment history tracking
* Environmental factor logging (temperature, humidity, light)
* Recovery tracking


# Knowledge Base
-----------------

* Plant care guides
* Problem-solution repository
* Seasonal gardening tips
* Searchable plant database
* User-contributed tips


# Responsive UI
----------------

* Mobile (≥320px)
* Tablet (≥768px)
* Desktop (≥1024px)
* Touch-friendly interactions
* Mobile photo capture support

---

## 🐳 Deployment & Operations

* Docker containerized application
* Docker Compose setup (app + DB)
* Environment-based configuration
* Persistent database storage
* Production-ready configuration

---

## 🧪 Testing & Documentation

* Unit testing
* Integration testing
* UI testing (critical flows)
* API documentation (Swagger/OpenAPI)
* User guide documentation
* Code-level documentation/comments

---

# 2️⃣  Project Out-Of-Scope

These are **explicitly excluded to avoid scope creep**.

## 🚫 Advanced/External Systems

* IoT sensor integration (soil moisture, humidity devices)
* AI/ML plant disease detection
* Computer vision for plant recognition

---

## 🚫 Social / Marketplace Features

* Plant buying/selling marketplace
* Social networking (followers, likes, feeds)
* Chat/messaging between users

---

## 🚫 Enterprise-Level Extensions

* Multi-tenant SaaS architecture
* Organization/team-based accounts


---

## 🚫 Offline & Native Apps

* Fully offline mode
* Native Android/iOS apps (Web-first only)

---

## 🚫 Advanced Analytics

* Predictive analytics
* AI-driven recommendations
* Big data pipelines

---

## 🚫 External System Integrations

* Payment gateways
* ERP/CRM integrations
* Smart home integrations

---

# 3️⃣ 🏗️ Architectural Scope

Defines **HOW the system will be built (technical capabilities included)**.

---

## 🧩 Architecture Style

* Modular Monolithic Architecture
* Layered design:

  * Controller
  * Service
  * Repository
  * Domain

---

## ⚙️ Core Technologies

* Backend: Java 17 + Spring Boot
* Frontend: Angular 18
* Database: PostgreSQL
* ORM: Hibernate (JPA)

---

## 🔄 Communication

* REST APIs (primary communication)
* WebSocket (real-time updates)

---

## ⚡ Asynchronous Processing

* RabbitMQ for:

  * Email notifications
  * Background jobs
  * Event-driven processing

---

## 🧠 Caching Strategy

* EhCache (L2 cache via Hibernate)

---

## 📁 Storage

* Local file storage (initial)
* Extendable to S3/MinIO

---

## 🔐 Security Architecture

* Spring Security
* JWT authentication
* RBAC authorization

---

## 📊 Observability

* Logging (Logback)
* Metrics (Micrometer)
* Monitoring (Prometheus + Grafana)

---

## 🐳 Deployment Architecture

* Dockerized services
* Docker Compose orchestration
* Environment-based configs

---

# 4️⃣ 🚧 Architectural / Project Boundaries

These define **limits that MUST NOT be crossed during development**.

---

## 🔒 Architecture Constraints

* ❌ No Microservices (strict monolith)
* ❌ No distributed caching (initial phase)
* ❌ No event streaming systems (Kafka excluded)
* ❌ No GraphQL (REST only)

---

## 🔌 Integration Boundaries

* Only:

  * Email (SMTP)
  * Optional push notifications
* ❌ No third-party heavy integrations

---

## 🧱 Codebase Boundaries

* Strict modular separation:

  * No cross-module direct DB access
  * Communication via services/events only
* No shared mutable state across modules

---

## ⚡ Performance Boundaries

* Must support:

  * Moderate concurrency (10k users)
* Not designed for:

  * Massive real-time streaming systems

---

## 🧠 Complexity Boundaries

* Avoid over-engineering:

  * ❌ No CQRS
  * ❌ No Event Sourcing
  * ❌ No reactive stack (WebFlux)

---

## 📦 Deployment Boundaries

* Single deployable unit (monolith)
* Horizontal scaling via container replication
* No service mesh / distributed infra

---

## 🔐 Security Boundaries

* JWT-based stateless auth only
* No OAuth providers (Google, Facebook) in initial phase

---

# ✅ Final Architectural Insight

This scope ensures:

✔ Clear delivery boundaries
✔ No feature creep
✔ Maintainable monolith
✔ Scalable but not over-engineered
✔ Fully aligned with KPI-driven PRD

