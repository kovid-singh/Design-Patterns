# 👤 UserService Learning Guide

---

## 🔒 Constructor Injection with `final` Keyword

> **Why use `final` with constructor injection fields?**
>
> Using `final` on the field means the reference can be assigned only once (during constructor initialization) and cannot be reassigned later. This ensures immutability and thread safety.

```java
private final UserService userService; // Can only be set once in constructor
```

---

## ✅ @Valid Annotation - Bean Validation

> The `@Valid` annotation triggers **Jakarta Validation** for incoming request bodies.

### What @Valid Does:
- ✓ Tells Spring to validate the incoming request body against constraints defined in the **UserDto** class
- ✓ Returns `400 Bad Request` with validation error details if validation fails
- ✓ Proceeds with the request if validation passes

---

## 📨 Deserialization using Jackson

> **Converting JSON to Java Objects**

### Process Flow:
1. `@RequestBody` annotation tells Spring to use a converter
2. JSON field names are matched to **UserDto** class fields (via getter/setter or direct field mapping)
3. `@Valid` annotation validates the mapped object against its constraints
4. This automatic behavior requires **Jackson** library (included in Spring Boot by default)

### Key Components:

| Term | Conversion | Example |
|------|-----------|---------|
| **Serialization** | Java Object → JSON | `LoginResponse` → `{"token":"abc"}` |
| **Deserialization** | JSON → Java Object | `{"email":"a@gmail.com"}` → `LoginRequest` |
| **`@RequestBody`** | Triggers deserialization | Request JSON becomes a Java object |
| **Return object / `ResponseEntity`** | Triggers serialization | Java object becomes response JSON |
| **`ResponseEntity`** | Wraps body, status, and headers | `200 OK` + JSON body + optional headers |

> **Note:** `MappingJackson2HttpMessageConverter` is the default converter used by Spring Boot for JSON conversion.

---

## 📦 ResponseEntity

> **What is ResponseEntity?**
>
> `ResponseEntity` is a Spring class that represents the entire HTTP response with three main components:

- **Response Body** - The data being sent back
- **HTTP Status Code** - Success (200), errors (400, 404, 500), etc.
- **HTTP Headers** - Metadata about the response

```java
ResponseEntity.ok(user)                    // 200 OK
ResponseEntity.status(HttpStatus.CREATED)  // Custom status
ResponseEntity.notFound().build()          // 404 Not Found
```

---

## 🔧 How User Service Reads Configuration
│
├─ Reads application.properties
│  ├─ spring.application.name = users-service
│  └─ spring.profiles.active = dev
│
├─ Calls Config Server (localhost:8888)
│  └─ GET /users-service/dev
│
├─ Config Server checks Git repository for:
│  ├─ application.yml (global defaults)
│  ├─ users-service.properties (service-specific defaults)
│  └─ users-service-dev.properties (profile-specific overrides)
│
├─ Merge properties (higher specificity overrides lower)
│
└─ Users Service loads into Environment
   └─ @Value, Environment, @ConfigurationProperties now work
```

### Configuration Hierarchy (Priority Order):
1. 🔵 `users-service-dev.properties` (profile-specific) - **Highest**
2. 🟢 `users-service.properties` (service-specific)
3. 🟡 `application.yml` (global defaults) - **Lowest**

---

## 🔐 @JsonProperty with Access Control

### What is `@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)`?

> A **Jackson annotation** that controls field behavior during JSON serialization and deserialization.

#### Behavior:
| Direction | Status | Description |
|-----------|--------|-------------|
| **JSON → Java Object** (Deserialization) | ✅ Allowed | Field can be read from incoming JSON |
| **Java Object → JSON** (Serialization) | ❌ Not Allowed | Field will NOT appear in outgoing JSON response |

**Use Case:** Store sensitive fields like passwords that should be accepted from clients but never returned in responses.

```java
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private String password;  // Accepted from client, hidden in responses
```

---

## ❓ Entity vs DTO

### What's the Difference?

| Aspect | Entity | DTO |
|--------|--------|-----|
| **Purpose** | Database table representation | Data transfer between application layers |
| **Annotations** | JPA annotations (`@Entity`, `@Column`) | Simple POJO |
| **Database Aware** | Yes - manages ORM | No - simple data container |
| **Usage Location** | Service ↔ Repository ↔ Database | Controller ↔ Service |
| **Security** | May expose sensitive fields | Can hide sensitive data |

### Key Insight:
> **Why can't we use DTO instead of Entity in JPA Repository?**
>
> Entities use JPA annotations and ORM management, while DTOs are simple objects containing only necessary data. **JPA repositories work only with Entities** because they need to track changes and manage the database mapping.

### Flow:
```
Controller (Request) → DTO → Service → Entity → Repository → Database
Database → Entity → Repository → Service → DTO → Controller (Response)
```

---

## 📊 @Enumerated Annotation

> **Why use `@Enumerated(EnumType.STRING)`?**

### Two Storage Options:

| Option | Storage Format | Pros | Cons |
|--------|---|---|---|
| **EnumType.STRING** | Text (e.g., "ADMIN", "USER") | ✅ Readable, Safe if enum order changes | More storage space |
| **EnumType.ORDINAL** (default) | Index (0, 1, 2...) | ✅ Compact storage | ❌ Breaks if enum order changes |

**Best Practice:** Use `EnumType.STRING` for better maintainability and readability.

```java
@Enumerated(EnumType.STRING)
private UserRole role;  // Stored as "ADMIN" not "0"
```

---

## 🔄 Converting Entity to DTO After Saving

### Why Convert?

> Converting to a DTO after saving ensures:
> - 🔒 Sensitive fields (like passwords) are not exposed in API responses
> - 📦 Decoupling of internal database representation from external API contract
> - 🎯 Fine-grained control over what data clients receive

### How to Convert?

Using **ModelMapper** or **MapStruct**:

```java
@Service
public class UserService {
    private final ModelMapper modelMapper;
    
    public UserDto createUser(UserDto dto) {
        User entity = new User(dto);
        User saved = userRepository.save(entity);
        
        // Convert entity back to DTO (password field not included)
        return modelMapper.map(saved, UserDto.class);
    }
}
```

### ModelMapper Behavior:
- ✓ Only maps fields that exist in the destination class (DTO)
- ✓ Skips fields not in the destination (e.g., password)
- ✓ Matches fields by name (and compatible type)
- ⚠️ For different field names, manual configuration is required

### Key Distinction:

| Library | Purpose | Usage |
|---------|---------|-------|
| **ModelMapper** | Java Object ↔ Java Object | User ↔ UserDto conversion |
| **ObjectMapper** | JSON ↔ Java Object | JSON serialization/deserialization |

> **Note:** In Spring Boot REST APIs, you usually don't call `ObjectMapper` yourself. Spring automatically uses it behind the scenes for `@RequestBody` and response objects.

---

## 🔄 @Transactional - Database Transactions

> **What does @Transactional do?**
>
> Makes a method execute inside a database transaction. All operations either succeed together (COMMIT) or fail together (ROLLBACK), ensuring data consistency.

### Transaction Behavior:

```
✅ All operations succeed → COMMIT
❌ Any operation fails → ROLLBACK everything
```

### Two Main Types:

#### 1️⃣ `@Transactional` (Read-Write)
Used for operations that modify data:

```java
@Transactional
public void createUser(User user) {
    userRepository.save(user);
    emailRepository.save(email);
    // If both succeed → COMMIT
    // If either fails → ROLLBACK (nothing saved)
}
```

#### 2️⃣ `@Transactional(readOnly = true)`
Used for read-only operations:

```java
@Transactional(readOnly = true)
public User getUser(Long id) {
    return userRepository.findById(id);
}
```

### Optimization for `readOnly = true`:
- ✓ Tells Spring/Hibernate no data will be modified
- ✓ Hibernate skips unnecessary change tracking
- ✓ Improves performance for SELECT queries
- ✓ Use for methods like: `getUser()`, `getAllUsers()`, `findByEmail()`

### Scenario Example:

**Successful Transaction:**
```
save User      ✅
save Email     ✅
─────────────────
COMMIT         ✅
(Both operations saved)
```

**Failed Transaction:**
```
save User      ✅
save Email     ❌ (Exception thrown)
─────────────────
ROLLBACK
(Nothing is saved - previous operation undone)
```

---

> 💡 **Summary:** `@Transactional` ensures your database operations are atomic - either everything happens or nothing happens. This prevents data corruption and inconsistencies.

