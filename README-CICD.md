# Spring Boot Oracle CI/CD Implementation

This document provides a comprehensive guide for implementing CI/CD with Jenkins for your Spring Boot Oracle application.

## 🏗️ Architecture Overview

The CI/CD pipeline includes:

- **Spring Boot Application** with Oracle Database integration
- **Docker Containerization** with multi-stage builds
- **Jenkins Pipeline** with automated testing and deployment
- **Multi-environment Support** (Development, Staging, Production)
- **Security Scanning** and code quality checks
- **Health Monitoring** with Spring Boot Actuator

## 📁 Project Structure

```
spring-oracle/
├── src/main/java/                    # Spring Boot application source
├── src/main/resources/               # Configuration files
│   ├── application.properties        # Default configuration
│   ├── application-docker.properties # Docker environment
│   ├── application-staging.properties # Staging environment
│   └── application-production.properties # Production environment
├── Dockerfile                        # Multi-stage Docker build
├── docker-compose.yml               # Development environment
├── docker-compose.staging.yml       # Staging environment
├── docker-compose.prod.yml          # Production environment
├── Jenkinsfile                      # Jenkins CI/CD pipeline
├── .dockerignore                    # Docker ignore file
├── scripts/                         # Utility scripts
│   ├── start-dev.sh                # Start development environment
│   └── stop-dev.sh                 # Stop development environment
├── jenkins-setup.md                # Detailed Jenkins setup guide
└── README-CICD.md                  # This file
```

## 🚀 Quick Start

### 1. Prerequisites

- Docker and Docker Compose
- Java 21
- Maven 3.9.6
- Git

### 2. Start Development Environment

```bash
# Make scripts executable (if not already done)
chmod +x scripts/*.sh

# Start all services
./scripts/start-dev.sh
```

This will start:
- Oracle Database (port 1521)
- Spring Boot Application (port 8080)
- Jenkins (port 8081)

### 3. Access Services

- **Spring Boot App**: http://localhost:8080
- **Jenkins**: http://localhost:8081
- **Health Check**: http://localhost:8080/actuator/health

## 🔧 CI/CD Pipeline Features

### Pipeline Stages

1. **Checkout**: Git repository checkout
2. **Build**: Maven compilation
3. **Unit Tests**: JUnit tests with JaCoCo coverage
4. **Code Quality**: SpotBugs static analysis
5. **Integration Tests**: Database integration tests
6. **Package**: JAR file creation
7. **Docker Build**: Container image creation
8. **Security Scan**: Trivy vulnerability scanning
9. **Deploy to Staging**: Automatic deployment to staging
10. **Deploy to Production**: Manual approval deployment

### Quality Gates

- **Test Coverage**: Minimum 80% code coverage
- **Static Analysis**: SpotBugs with low threshold
- **Security**: OWASP dependency check
- **Performance**: Build time monitoring

## 🐳 Docker Configuration

### Multi-Stage Build

The Dockerfile uses a multi-stage build for optimization:

```dockerfile
# Build stage
FROM maven:3.9.6-openjdk-21-slim AS build
# ... build steps

# Runtime stage
FROM openjdk:21-jre-slim
# ... runtime configuration
```

### Environment-Specific Configurations

- **Development**: Full logging, debug mode
- **Staging**: Debug logging, test data
- **Production**: Minimal logging, optimized performance

## 🔐 Security Features

### Container Security

- Non-root user execution
- Minimal base images
- Security scanning with Trivy
- Dependency vulnerability checks

### Application Security

- Environment-specific configurations
- Secure credential management
- Health check endpoints
- Network isolation

## 📊 Monitoring and Observability

### Health Checks

- Application health: `/actuator/health`
- Database connectivity
- Docker container health

### Metrics

- JVM metrics
- Application metrics
- Database connection pool
- Custom business metrics

### Logging

- Structured logging
- Environment-specific log levels
- Centralized log collection ready

## 🚀 Deployment Strategies

### Staging Deployment

- Triggered on `develop` branch
- Automatic deployment
- Full test suite execution
- Performance testing

### Production Deployment

- Triggered on `main` branch
- Manual approval required
- Blue-green deployment ready
- Rollback capability

## 🛠️ Development Workflow

### 1. Feature Development

```bash
# Create feature branch
git checkout -b feature/new-feature

# Make changes and test locally
./scripts/start-dev.sh

# Run tests
mvn test

# Commit and push
git add .
git commit -m "feat: add new feature"
git push origin feature/new-feature
```

### 2. Code Review

- Create pull request
- Automated CI checks run
- Code review process
- Merge to develop branch

### 3. Staging Deployment

- Automatic deployment to staging
- Integration testing
- User acceptance testing
- Performance validation

### 4. Production Release

- Merge to main branch
- Manual approval in Jenkins
- Production deployment
- Monitoring and validation

## 🔧 Configuration Management

### Environment Variables

| Environment | Database | Port | Profile |
|-------------|----------|------|---------|
| Development | oracle-db:1521 | 8080 | default |
| Staging | oracle-db-staging:1521 | 8082 | staging |
| Production | oracle-db-prod:1521 | 8083 | production |

### Secrets Management

- Database passwords via environment variables
- API keys via Jenkins credentials
- Docker registry credentials
- Telegram bot tokens

## 📈 Performance Optimization

### Build Optimization

- Maven dependency caching
- Docker layer caching
- Parallel test execution
- Incremental builds

### Runtime Optimization

- JVM tuning
- Database connection pooling
- Resource limits
- Health check optimization

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Issues**
   ```bash
   # Check database status
   docker-compose logs oracle-db
   
   # Test connection
   docker exec -it spring-oracle-db sqlplus demo/demo_password@//localhost:1521/XE
   ```

2. **Jenkins Build Failures**
   ```bash
   # Check Jenkins logs
   docker-compose logs jenkins
   
   # Verify Maven configuration
   docker exec -it jenkins mvn --version
   ```

3. **Application Startup Issues**
   ```bash
   # Check application logs
   docker-compose logs spring-oracle-app
   
   # Verify health endpoint
   curl http://localhost:8080/actuator/health
   ```

### Debug Commands

```bash
# View all service logs
docker-compose logs -f

# Check service status
docker-compose ps

# Restart specific service
docker-compose restart spring-oracle-app

# Access container shell
docker exec -it spring-oracle-app bash
```

## 📚 Additional Resources

- [Jenkins Setup Guide](jenkins-setup.md)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Docker Documentation](https://docs.docker.com/)
- [Oracle Database Documentation](https://docs.oracle.com/en/database/)

## 🤝 Contributing

1. Follow the existing code style
2. Write unit tests for new features
3. Update documentation as needed
4. Follow conventional commit messages
5. Ensure CI/CD pipeline passes

## 📝 License

This project is licensed under the MIT License.

---

For detailed Jenkins setup instructions, see [jenkins-setup.md](jenkins-setup.md).
