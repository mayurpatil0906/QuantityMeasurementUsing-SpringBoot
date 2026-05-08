# 📏 Quantity Measurement App — Spring Boot REST API with JPA

> A Spring Boot-based Quantity Measurement REST service that modernizes the standalone Quantity Measurement Application using **REST Controllers**, **Spring Data JPA**, **DTOs**, **Service Layer**, **H2 Database**, **Swagger/OpenAPI**, **Exception Handling**, and **Spring Boot Testing**.

---

# 📋 Table of Contents

- [Project Overview](#project-overview)
- [Problem Statement](#problem-statement)
- [Why UC17 Was Needed](#why-uc17-was-needed)
- [Core Features](#core-features)
- [Application Architecture](#application-architecture)
- [Use Case Flow](#use-case-flow)
- [Spring Boot Migration Steps](#spring-boot-migration-steps)
- [REST API Endpoints](#rest-api-endpoints)
- [DTO Layer](#dto-layer)
- [JPA Entity Layer](#jpa-entity-layer)
- [Repository Layer](#repository-layer)
- [Service Layer](#service-layer)
- [Controller Layer](#controller-layer)
- [Exception Handling](#exception-handling)
- [Testing Strategy](#testing-strategy)
- [Swagger API Documentation](#swagger-api-documentation)
- [H2 Console](#h2-console)
- [Project Structure](#project-structure)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Sample CURL Commands](#sample-curl-commands)
- [Educational Goals](#educational-goals)
- [Future Enhancements](#future-enhancements)
- [License](#license)

---

# 📌 Project Overview

The **Quantity Measurement App — Spring Boot REST API** transforms the earlier standalone Quantity Measurement Application into an enterprise-style backend application.

UC17 focuses on integrating the existing business logic with the Spring Framework ecosystem while preserving all previously implemented functionality from UC1 to UC16.

The application provides REST APIs for:

- Quantity comparison
- Unit conversion
- Addition
- Subtraction
- Multiplication
- Division
- Operation history
- Measurement type history
- Error history
- Operation count

This use case introduces real-world backend development using Spring Boot, Spring MVC, Spring Data JPA, DTOs, repositories, services, testing, and API documentation.

---

# 🌍 Problem Statement

The previous JDBC-based implementation worked correctly but had several limitations:

- Too much boilerplate code
- Manual database connection handling
- Repetitive SQL queries
- Manual ResultSet mapping
- Complex transaction management
- No built-in REST API support
- No automatic JSON conversion
- No centralized exception handling
- Difficult testing setup
- No API documentation
- Limited monitoring support

UC17 solves these problems by migrating the application to a Spring Boot-based REST architecture.

---

# ❌ Why UC17 Was Needed

| UC16 Limitation | UC17 Spring Boot Solution |
|---|---|
| Manual JDBC code | Spring Data JPA |
| Manual connection handling | Auto-configured datasource |
| Manual SQL queries | JpaRepository query methods |
| Manual JSON handling | Spring MVC + Jackson |
| Manual object creation | Dependency Injection |
| No REST framework | REST Controllers |
| No global error handling | @ControllerAdvice |
| Difficult testing | MockMvc + Spring Boot Test |
| No API documentation | Swagger/OpenAPI |
| No monitoring | Spring Boot Actuator |
| External server needed | Embedded Tomcat |

---

# ✨ Core Features

| Module | Features |
|---|---|
| 🚀 Spring Boot App | Auto-configuration and embedded Tomcat |
| 🌐 REST API | JSON-based HTTP endpoints |
| 🧮 Quantity Operations | Compare, convert, add, subtract, multiply, divide |
| 🗃️ JPA Persistence | Store operation history in database |
| 📦 DTO Layer | Clean request and response objects |
| 🧠 Service Layer | Business logic separation |
| 🗂️ Repository Layer | Spring Data JPA repositories |
| ⚠️ Exception Handling | Centralized REST error responses |
| 📘 Swagger UI | Interactive API documentation |
| 🧪 Testing | MockMvc and integration tests |
| 🩺 Actuator | Health and monitoring endpoints |
| 🧾 H2 Console | In-memory database inspection |

---

# 🏛️ Application Architecture

```text
Client / Postman / CURL / Swagger UI
              ↓
REST Controller Layer
              ↓
DTO Request / Response Layer
              ↓
Service Layer
              ↓
Spring Data JPA Repository
              ↓
JPA Entity
              ↓
H2 / MySQL Database
```

---

# 🔄 Use Case Flow

```text
User Sends HTTP Request
          ↓
Controller Receives JSON Body
          ↓
DTO Validation Happens
          ↓
Service Executes Business Logic
          ↓
Repository Saves Result / Error
          ↓
DTO Response Returned
          ↓
Client Receives JSON Response
```

---

# 🧩 Spring Boot Migration Steps

---

## Step 1 — Generate Spring Boot Project

Use Spring Initializr to generate a Spring Boot project.

```text
https://start.spring.io/
```

Recommended dependencies:

- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Spring Boot Starter Test
- Spring Security
- Spring Boot Actuator
- Swagger/OpenAPI

---

## Step 2 — Update pom.xml

The `pom.xml` is updated to include Spring Boot parent and required dependencies.

Main dependencies include:

```xml
spring-boot-starter-web
spring-boot-starter-data-jpa
spring-boot-starter-validation
spring-boot-starter-test
spring-boot-starter-actuator
h2
lombok
springdoc-openapi
```

---

## Step 3 — Add Spring Boot Application Class

```java
@SpringBootApplication
public class QuantityMeasurementApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurementApplication.class, args);
    }
}
```

This class becomes the main entry point of the application.

---

## Step 4 — Convert Entity to JPA Entity

The old model/entity class is converted into a JPA entity.

Important annotations:

| Annotation | Purpose |
|---|---|
| @Entity | Marks class as database entity |
| @Table | Maps class to table |
| @Id | Defines primary key |
| @GeneratedValue | Auto-generates ID |
| @Column | Maps fields to columns |
| @PrePersist | Runs before insert |
| @PreUpdate | Runs before update |
| @Data | Lombok-generated getters/setters |

---

## Step 5 — Replace JDBC Repository with Spring Data JPA

Old JDBC repository classes are replaced with:

```java
public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {
}
```

This removes the need for manual SQL CRUD code.

---

## Step 6 — Create DTO Classes

DTOs are used to separate API communication from database persistence.

Main DTOs:

```text
QuantityDTO
QuantityInputDTO
QuantityMeasurementDTO
```

DTOs help keep controller input/output clean and separate from JPA entities.

---

## Step 7 — Refactor Service Layer

The service layer is marked with:

```java
@Service
```

The repository is injected into the service using Spring Dependency Injection.

The service layer handles:

- Business logic
- DTO to model conversion
- Model to DTO conversion
- Result persistence
- Error persistence
- History retrieval

---

## Step 8 — Create REST Controller

The controller exposes REST endpoints using:

```java
@RestController
@RequestMapping("/api/v1/quantities")
```

It handles HTTP requests and delegates business logic to the service layer.

---

## Step 9 — Configure application.properties

Development uses H2 database.

```properties
spring.application.name=quantity-measurement-app

spring.datasource.url=jdbc:h2:mem:quantitymeasurementdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## Step 10 — Add Global Exception Handling

Global exception handling is implemented using:

```java
@ControllerAdvice
@ExceptionHandler
ResponseEntity
```

This ensures consistent error responses across the whole REST API.

---

# 🌐 REST API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/v1/quantities/compare` | Compare two quantities |
| POST | `/api/v1/quantities/convert` | Convert one unit to another |
| POST | `/api/v1/quantities/add` | Add two quantities |
| POST | `/api/v1/quantities/subtract` | Subtract two quantities |
| POST | `/api/v1/quantities/multiply` | Multiply two quantities |
| POST | `/api/v1/quantities/divide` | Divide two quantities |
| GET | `/api/v1/quantities/history/operation/{operation}` | Get history by operation |
| GET | `/api/v1/quantities/history/type/{measurementType}` | Get history by measurement type |
| GET | `/api/v1/quantities/history/errored` | Get errored records |
| GET | `/api/v1/quantities/count/{operation}` | Count operation records |

---

# 📦 DTO Layer

## QuantityDTO

Used to represent individual quantity input.

```json
{
  "value": 1.0,
  "unit": "FEET",
  "measurementType": "LengthUnit"
}
```

Important validations:

| Annotation | Purpose |
|---|---|
| @NotNull | Value must not be null |
| @NotEmpty | String must not be empty |
| @Pattern | Must match allowed values |
| @AssertTrue | Custom validation logic |

---

## QuantityInputDTO

Used as request body for operations.

```json
{
  "thisQuantityDTO": {
    "value": 1.0,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  },
  "thatQuantityDTO": {
    "value": 12.0,
    "unit": "INCHES",
    "measurementType": "LengthUnit"
  }
}
```

---

## QuantityMeasurementDTO

Used as API response DTO.

```json
{
  "thisValue": 1.0,
  "thisUnit": "FEET",
  "thisMeasurementType": "LengthUnit",
  "thatValue": 12.0,
  "thatUnit": "INCHES",
  "thatMeasurementType": "LengthUnit",
  "operation": "compare",
  "resultString": "true",
  "resultValue": 0.0,
  "resultUnit": null,
  "resultMeasurementType": null,
  "errorMessage": null,
  "error": false
}
```

---

# 🗃️ JPA Entity Layer

The entity represents a database table storing quantity measurement operation records.

Example fields:

```text
id
thisValue
thisUnit
thisMeasurementType
thatValue
thatUnit
thatMeasurementType
operation
resultString
resultValue
resultUnit
resultMeasurementType
errorMessage
isError
createdAt
updatedAt
```

Entity lifecycle callbacks:

```java
@PrePersist
@PreUpdate
```

These automatically maintain timestamps.

---

# 🗂️ Repository Layer

The repository extends JpaRepository.

```java
@Repository
public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(String operation);

    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);

    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);

    List<QuantityMeasurementEntity> findByIsErrorTrue();

    long countByOperationAndIsErrorFalse(String operation);
}
```

Benefits:

- No manual SQL
- No ResultSet mapping
- Built-in CRUD methods
- Custom query methods
- Cleaner persistence logic

---

# 🧠 Service Layer

The service layer contains the main business logic.

Responsibilities:

- Compare quantities
- Convert units
- Add quantities
- Subtract quantities
- Multiply quantities
- Divide quantities
- Save success result
- Save error result
- Retrieve operation history
- Retrieve measurement history
- Retrieve error history
- Count operations

Service annotation:

```java
@Service
```

Repository injection:

```java
@Autowired
private QuantityMeasurementRepository repository;
```

---

# 🎮 Controller Layer

The controller exposes REST APIs.

Important annotations:

| Annotation | Purpose |
|---|---|
| @RestController | Creates REST controller |
| @RequestMapping | Defines base API path |
| @PostMapping | Handles POST requests |
| @GetMapping | Handles GET requests |
| @RequestBody | Reads JSON body |
| @PathVariable | Reads URL path variable |
| @Valid | Triggers DTO validation |
| @Tag | Swagger group annotation |
| @Operation | Swagger endpoint description |

---

# ⚠️ Exception Handling

Global exception handler provides structured error responses.

```java
@ControllerAdvice
public class GlobalExceptionHandler {
}
```

Error response format:

```json
{
  "timestamp": "2026-03-09T06:38:49.953048",
  "status": 400,
  "error": "Quantity Measurement Error",
  "message": "Cannot perform arithmetic between different measurement categories",
  "path": "/api/v1/quantities/add"
}
```

Handled errors:

| Exception Type | HTTP Status |
|---|---|
| Validation error | 400 Bad Request |
| QuantityMeasurementException | 400 Bad Request |
| Generic Exception | 500 Internal Server Error |

---

# 🧪 Testing Strategy

---

## Controller Testing with MockMvc

Used for testing only the REST controller layer.

Important annotations:

```java
@WebMvcTest(QuantityMeasurementController.class)
@MockBean
@Autowired
```

MockMvc is used to perform fake HTTP requests without starting the full server.

---

## Integration Testing with SpringBootTest

Used for testing the full application stack.

```java
@SpringBootTest
```

Integration tests verify:

- Controller
- Service
- Repository
- JPA
- Database
- Exception handling
- JSON serialization

---

## Test Commands

```bash
mvn test
```

Run specific controller test:

```bash
mvn test -Dtest=QuantityMeasurementControllerTest
```

Run integration test:

```bash
mvn test -Dtest=QuantityMeasurementApplicationTests
```

---

# 📘 Swagger API Documentation

Swagger UI provides interactive API documentation.

Access Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

Swagger annotations used:

| Annotation | Purpose |
|---|---|
| @OpenAPIDefinition | API metadata |
| @Tag | Groups endpoints |
| @Operation | Describes endpoint |
| @ApiResponse | Documents responses |
| @Schema | Describes DTO structure |
| @ExampleObject | Shows sample request/response |

---

# 🧾 H2 Console

H2 console is available for development database inspection.

```text
http://localhost:8080/h2-console
```

Login details:

```text
JDBC URL : jdbc:h2:mem:quantitymeasurementdb
Username : sa
Password : leave blank
```

---

# 📁 Project Structure

```text
quantity-measurement-app/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/app/quantitymeasurement/
│   │   │       ├── QuantityMeasurementApplication.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   └── QuantityMeasurementController.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── IQuantityMeasurementService.java
│   │   │       │   └── QuantityMeasurementServiceImpl.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   └── QuantityMeasurementRepository.java
│   │   │       │
│   │   │       ├── model/
│   │   │       │   ├── QuantityMeasurementEntity.java
│   │   │       │   ├── QuantityDTO.java
│   │   │       │   ├── QuantityInputDTO.java
│   │   │       │   ├── QuantityMeasurementDTO.java
│   │   │       │   └── OperationType.java
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   ├── QuantityMeasurementException.java
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       │
│   │   │       └── config/
│   │   │           ├── SecurityConfig.java
│   │   │           └── SwaggerConfig.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-prod.properties
│   │
│   └── test/
│       └── java/
│           └── com/app/quantitymeasurement/
│               ├── QuantityMeasurementControllerTest.java
│               └── QuantityMeasurementApplicationTests.java
│
├── dump/
│   └── old-jdbc-files/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

# 💻 Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Persistence layer |
| Hibernate | ORM implementation |
| H2 Database | Development database |
| MySQL | Production database option |
| Maven | Build and dependency management |
| Lombok | Reduces boilerplate code |
| Swagger/OpenAPI | API documentation |
| Spring Boot Test | Testing framework |
| MockMvc | REST controller testing |
| Actuator | Health and monitoring |
| Embedded Tomcat | Runs app without external server |

---

# 🚀 Getting Started

---

## Prerequisites

```bash
Java 17+
Maven
IDE with Spring Boot support
Postman / curl / Swagger UI
```

---

## Build Project

```bash
mvn clean compile
```

---

## Run Tests

```bash
mvn test
```

---

## Run Application

```bash
mvn spring-boot:run
```

---

## Package as JAR

```bash
mvn clean package
```

---

## Run JAR

```bash
java -jar target/quantity-measurement-app-1.0.0.jar
```

---

# 🧪 Sample CURL Commands

---

## Compare Quantities

```bash
curl -X POST http://localhost:8080/api/v1/quantities/compare \
 -H "Content-Type: application/json" \
 -d '{
   "thisQuantityDTO": {
     "value": 1.0,
     "unit": "FEET",
     "measurementType": "LengthUnit"
   },
   "thatQuantityDTO": {
     "value": 12.0,
     "unit": "INCHES",
     "measurementType": "LengthUnit"
   }
 }'
```

---

## Convert Quantities

```bash
curl -X POST http://localhost:8080/api/v1/quantities/convert \
 -H "Content-Type: application/json" \
 -d '{
   "thisQuantityDTO": {
     "value": 1.0,
     "unit": "FEET",
     "measurementType": "LengthUnit"
   },
   "thatQuantityDTO": {
     "value": 0.0,
     "unit": "INCHES",
     "measurementType": "LengthUnit"
   }
 }'
```

---

## Add Quantities

```bash
curl -X POST http://localhost:8080/api/v1/quantities/add \
 -H "Content-Type: application/json" \
 -d '{
   "thisQuantityDTO": {
     "value": 1.0,
     "unit": "FEET",
     "measurementType": "LengthUnit"
   },
   "thatQuantityDTO": {
     "value": 12.0,
     "unit": "INCHES",
     "measurementType": "LengthUnit"
   }
 }'
```

---

## Get History by Operation

```bash
curl http://localhost:8080/api/v1/quantities/history/operation/COMPARE
```

---

## Get History by Measurement Type

```bash
curl http://localhost:8080/api/v1/quantities/history/type/LengthUnit
```

---

## Get Operation Count

```bash
curl http://localhost:8080/api/v1/quantities/count/COMPARE
```

---

## Get Error History

```bash
curl http://localhost:8080/api/v1/quantities/history/errored
```

---

# ✅ Postconditions

After UC17 implementation:

- Spring Boot application runs successfully
- Embedded Tomcat starts on port 8080
- REST endpoints are available
- H2 database stores operation records
- Swagger UI documents APIs
- Global exception handling returns structured errors
- JPA replaces JDBC persistence
- All UC1–UC16 business logic is preserved
- Tests run using Spring Boot Test and MockMvc
- Application is ready for future security and production database integration

---

# 🎯 Educational Goals

| Module | Learning Outcome |
|---|---|
| Spring Boot | Auto-configuration and embedded server |
| REST API | Controller-based API development |
| JPA | ORM and repository pattern |
| DTO | Clean API request and response design |
| Service Layer | Business logic separation |
| Dependency Injection | Spring-managed beans |
| Exception Handling | @ControllerAdvice and @ExceptionHandler |
| Testing | MockMvc and integration testing |
| Swagger | API documentation |
| H2 | Development database testing |
| Actuator | Health checks and monitoring |

---

# 🔮 Future Enhancements

- JWT authentication
- Role-based authorization
- MySQL production deployment
- Docker support
- React frontend integration
- API versioning
- Redis caching
- CI/CD pipeline
- Cloud deployment
- Advanced analytics dashboard
- Export operation history as CSV/PDF

---

# 📜 License

This project is created for educational and learning purposes.

---

> 📏 *"UC17 is not just a migration from JDBC to Spring Boot — it is a transition from standalone Java logic to enterprise-grade REST API architecture."*
