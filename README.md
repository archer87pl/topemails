# Domain Counter Service

A Java application for reading emails and counting domain occurrences from their contents. The project follows a layered architecture (`application`, `domain`, `infrastructure`) with a clean separation of responsibilities.

---

## 📁 Project Structure

```
src/
├── main/
│   └── java/org/kahoot/
│       ├── application/           # Business logic (DomainCounterService)
│       ├── domain/                # Core domain models (Email)
│       ├── infrastructure/        # External services (EmailReader)
│       └── Main.java              # Application entry point
│
└── test/
    └── java/org/kahoot/
        ├── application/           # Unit tests for application layer
        ├── domain/                # Unit tests for domain models
        ├── infrastructure/        # Unit tests for infrastructure
        └── integration/           # Integration tests
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+ (or your target version)
- Gradle or Maven
- IDE (e.g., IntelliJ IDEA)

### Build and Run

```bash
./gradlew build      # build the project
./gradlew test       # run all tests
./gradlew run        # run the main class
```

Or, using your IDE, run the `Main` class located at `org.kahoot.Main`.

---

## 🧪 Testing

- Unit tests are located in their respective mirrored packages under `src/test/java`.
- Integration tests are in `integration/`.

Run tests with:

```bash
./gradlew test
```

---

## 🧱 Technologies Used

- Java
- JUnit (for testing)
- Gradle or Maven (for build automation)
- [Any Email Reading Library, e.g., JavaMail] (if applicable)

---

## 🧼 Architecture

This project follows a **Clean Architecture** approach:

- `domain` — Pure business objects
- `application` — Use-case orchestration logic
- `infrastructure` — Implementation of I/O operations like reading emails
- `integration` — Tests ensuring components work together

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
