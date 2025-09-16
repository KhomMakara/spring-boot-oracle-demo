# Spring Oracle Demo

A Spring Boot application demonstrating integration with Oracle Database using MyBatis and JPA.
### Application UI

| Feature | Screenshot |
|---------|------------|
| Contact List | ![Contact List](docs/images/contact-list.png) |
| Contact Form | ![Contact Form](docs/images/contact-register-form.png) |
## 🚀 Features

- **Spring Boot 3.4.9** with Java 21
- **Oracle Database** integration with JDBC
- **MyBatis** for SQL mapping
- **JPA/Hibernate** for ORM
- **Thymeleaf** for templating
- **Lombok** for reducing boilerplate code
- **Contact Management** functionality

## 📋 Prerequisites

Before running this application, make sure you have:

- **Java 21** or higher
- **Maven 3.6+**
- **Oracle Database** (version 11g or higher)
- **Oracle JDBC Driver** (ojdbc11)

## 🛠️ Installation & Setup

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd spring-oracle
```

### 2. Database Setup

1. Install and configure Oracle Database
2. Create a database user:
   ```sql
   CREATE USER demo IDENTIFIED BY demo_password;
   GRANT CONNECT, RESOURCE TO demo;
   GRANT CREATE TABLE TO demo;
   ```

3. Update database configuration in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/ORCLPDB1
   spring.datasource.username=demo
   spring.datasource.password=demo_password
   ```

### 3. Build and Run

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080`

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dao/            # Data Access Objects
│   │   ├── exception/      # Custom exceptions
│   │   ├── mapper/         # MyBatis mappers
│   │   ├── model/          # Data models
│   │   ├── service/        # Business logic
│   │   └── util/           # Utility classes
│   └── resources/
│       ├── mybatis/mapper/ # MyBatis XML mappings
│       ├── static/         # Static web resources
│       └── templates/      # Thymeleaf templates
└── test/                   # Test classes
```

## 🔧 Configuration

### Database Configuration

The application uses both JPA and MyBatis for database operations:

- **JPA/Hibernate**: For basic CRUD operations
- **MyBatis**: For complex SQL queries and custom mappings

### Key Configuration Files

- `application.properties`: Database and application configuration
- `MyBatisConfig.java`: MyBatis configuration
- `ContactInfoMapper.xml`: SQL mappings

## 📚 API Endpoints

### Contact Management

- `GET /contact-list` - Retrieve all contacts
- `POST /contact-register` - Create a new contact

## 🧪 Testing

Run tests using Maven:

```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## 🚀 Deployment

### Using Maven

```bash
# Create executable JAR
mvn clean package

# Run the JAR
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Using Docker (Optional)

```dockerfile
FROM openjdk:21-jdk-slim
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🛠️ Development

### Code Style

This project follows:
- **Java 17+** features (Optional, Stream API, Lambda expressions)
- **Lombok** annotations for reducing boilerplate
- **Spring Boot** conventions
- **Clean imports** (no wildcard imports)
- **Proper logging** with appropriate log levels

### Key Dependencies

- `spring-boot-starter-web`: Web application framework
- `spring-boot-starter-data-jpa`: JPA/Hibernate integration
- `mybatis-spring-boot-starter`: MyBatis integration
- `spring-boot-starter-thymeleaf`: Template engine
- `ojdbc11`: Oracle JDBC driver
- `lombok`: Code generation
- `commons-lang3`: Apache Commons utilities

## 📝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Support

If you have any questions or need help, please:

1. Check the [Issues](https://github.com/your-username/spring-oracle/issues) page
2. Create a new issue if your problem isn't already reported
3. Contact the maintainers

## 📚 Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [MyBatis Documentation](https://mybatis.org/mybatis-3/)
- [Oracle Database Documentation](https://docs.oracle.com/en/database/)
- [Lombok Documentation](https://projectlombok.org/features/all)
