## librarySsr

Server-side rendered library management system built with **Spring Boot 3**, **Thymeleaf**, **Spring Security**, **Spring Data JPA**, **Flyway**, and **MySQL**. The app provides basic library features such as managing books and users with authentication/authorization and server-side rendered HTML views.


### Tech stack

- **Language**: Java 21 (configured via Gradle toolchains, Amazon Corretto)
- **Framework**: Spring Boot 3.5.x
- **View layer**: Thymeleaf + Thymeleaf Spring Security extras
- **Persistence**: Spring Data JPA, MySQL
- **Database migrations**: Flyway (`src/main/resources/db/migration`)
- **Security**: Spring Security
- **Build tool / package manager**: Gradle (Groovy DSL)


### Requirements

- **Java**: JDK 21 (or compatible, Gradle will use configured toolchain)
- **Gradle**: Not required explicitly – project includes `gradlew` / `gradlew.bat`
- **Database**: MySQL 8+ instance accessible from the app
- **OS**: Any OS supported by Java 21 and Gradle (developed on Windows)


### Getting started

Clone the repo:

```bash
git clone <your-repo-url>
cd librarySsr
```

Build the application:

```bash
./gradlew build        # Linux / macOS
gradlew.bat build      # Windows
```

Run the application (dev profile by default):

```bash
./gradlew bootRun      # Linux / macOS
gradlew.bat bootRun    # Windows
```

Or run the packaged jar:

```bash
./gradlew bootJar
java -jar build/libs/libraryapp.jar
```

By default the app is a standard Spring Boot web application and will be available on:

- **URL**: `http://localhost:8080`


### Gradle scripts / tasks

Commonly used tasks:

- **Run in dev mode**: `./gradlew bootRun`
- **Build jar**: `./gradlew bootJar`
- **Full build**: `./gradlew build`
- **Run tests**: `./gradlew test`

Notes:

- `bootJar` is configured to produce `libraryapp.jar`.
- Tests use **JUnit 5** through `spring-boot-starter-test`.


### Configuration & environment variables

Spring Boot configuration files:

- `src/main/resources/application.properties`
  - Sets the active profile to `dev` by default:
    - `spring.profiles.active=dev`
- `src/main/resources/application-dev.properties`
- `src/main/resources/application-staging.properties`
- `src/main/resources/application-prod.properties`

**Dev profile database configuration** (`application-dev.properties`):

```properties
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DB:libraryssr}?useSSL=true&serverTimezone=UTC
spring.datasource.username=${MYSQL_USER:stefpro}
spring.datasource.password=${MYSQL_PASSWORD:12345}

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.open-in-view=false

spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

spring.web.locale=el
spring.web.locale-resolver=fixed

spring.thymeleaf.cache=false
```

#### Environment variables

The following environment variables are supported (with defaults in `application-dev.properties`):

- **`MYSQL_HOST`**: MySQL host (default: `localhost`)
- **`MYSQL_PORT`**: MySQL port (default: `3306`)
- **`MYSQL_DB`**: MySQL database name (default: `libraryssr`)
- **`MYSQL_USER`**: MySQL username (default: `stefpro`)
- **`MYSQL_PASSWORD`**: MySQL password (default: `12345`)

You can also override the active Spring profile at runtime:

- **`SPRING_PROFILES_ACTIVE`**: profile name (`dev`, `staging`, `prod`, …)

Examples:

```bash
# Run with dev profile (explicit)
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun

# Run jar against a remote MySQL instance
SPRING_PROFILES_ACTIVE=prod \
MYSQL_HOST=my-db-host \
MYSQL_PORT=3306 \
MYSQL_DB=libraryssr \
MYSQL_USER=myuser \
MYSQL_PASSWORD=mypassword \
java -jar build/libs/libraryapp.jar
```

On Windows PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
gradlew.bat bootRun
```


### Project structure

Key directories and files:

- **`build.gradle`**: Gradle build configuration (dependencies, Java toolchain, tasks)
- **`settings.gradle`**: Gradle settings (root project name, toolchains plugin)
- **`src/main/java/cf/library/libraryapp/LibraryAppSsrApplication.java`**: Spring Boot main entry point
- **`src/main/java/cf/library/libraryapp/controller/*`**: MVC controllers (e.g. `BookController`, `UserController`, `LoginController`)
- **`src/main/java/cf/library/libraryapp/service/*`**: Service layer interfaces and implementations
- **`src/main/java/cf/library/libraryapp/repository/*`**: Spring Data JPA repositories
- **`src/main/java/cf/library/libraryapp/model/*`**: JPA entities (e.g. `Role`, `User`, `Book`, etc.)
- **`src/main/java/cf/library/libraryapp/authentication/*`**: Spring Security configuration and handlers
- **`src/main/java/cf/library/libraryapp/validator/*`**: Custom validators for user and book operations
- **`src/main/java/cf/library/libraryapp/dto/*`**: DTOs for API/view models
- **`src/main/resources/templates/*`**: Thymeleaf templates (index, login, books list, forms, success pages, fragments)
- **`src/main/resources/db/migration/*`**: Flyway database migration scripts
- **`src/main/resources/messages*.properties`**: Message bundles for internationalization
- **`src/test/java/cf/library/libraryapp/LibraryAppSsrApplicationTests.java`**: Basic Spring Boot context load test


### Running tests

Run the full test suite:

```bash
./gradlew test        # Linux / macOS
gradlew.bat test      # Windows
```

Tests are written with JUnit 5 (`spring-boot-starter-test`) and use the standard Spring Boot testing support (`@SpringBootTest`).


### License

This project does not currently specify an explicit license. If you plan to publish or reuse this code, consider adding a `LICENSE` file (for example, MIT, Apache 2.0, or another license of your choice).
