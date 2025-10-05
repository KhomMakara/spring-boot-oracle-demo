# Jenkins CI/CD Setup Guide

This guide will help you set up Jenkins for CI/CD with your Spring Boot Oracle application.

## Prerequisites

1. **Docker and Docker Compose** installed on your system
2. **Java 21** installed
3. **Maven 3.9.6** installed
4. **Git** installed

## Quick Start

### 1. Start the Development Environment

```bash
# Start all services (Oracle DB, Spring Boot App, Jenkins)
docker-compose up -d

# Check if all services are running
docker-compose ps
```

### 2. Access Jenkins

1. Open your browser and go to `http://localhost:8081`
2. Get the initial admin password:
   ```bash
   docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
   ```
3. Follow the Jenkins setup wizard
4. Install recommended plugins

### 3. Configure Jenkins

#### Install Required Plugins

Go to **Manage Jenkins** → **Manage Plugins** and install:

- **Pipeline Plugin** (usually pre-installed)
- **Docker Pipeline Plugin**
- **Maven Integration Plugin**
- **JaCoCo Plugin**
- **HTML Publisher Plugin**
- **SpotBugs Plugin**
- **OWASP Dependency Check Plugin**
- **Blue Ocean** (optional, for better UI)

#### Configure Tools

Go to **Manage Jenkins** → **Global Tool Configuration**:

1. **JDK**: Add JDK 21 installation
2. **Maven**: Add Maven 3.9.6 installation
3. **Docker**: Ensure Docker is available in PATH

#### Configure Credentials

Go to **Manage Jenkins** → **Manage Credentials**:

1. Add Docker Registry credentials (if using private registry)
2. Add database credentials for testing
3. Add any other service credentials

### 4. Create Jenkins Job

#### Option 1: Pipeline Job (Recommended)

1. Click **New Item**
2. Enter job name: `spring-oracle-pipeline`
3. Select **Pipeline**
4. Click **OK**
5. In the Pipeline section:
   - Definition: **Pipeline script from SCM**
   - SCM: **Git**
   - Repository URL: Your Git repository URL
   - Script Path: `Jenkinsfile`
6. Click **Save**

#### Option 2: Freestyle Job

1. Click **New Item**
2. Enter job name: `spring-oracle-freestyle`
3. Select **Freestyle project**
4. Click **OK**
5. Configure:
   - **Source Code Management**: Git
   - **Build Triggers**: GitHub hook trigger
   - **Build**: Add Maven build step
   - **Post-build Actions**: Archive artifacts, Publish test results

### 5. Pipeline Stages Overview

The Jenkinsfile includes the following stages:

1. **Checkout**: Get source code from Git
2. **Build**: Compile the application
3. **Unit Tests**: Run unit tests with coverage
4. **Code Quality**: Static analysis with SpotBugs
5. **Integration Tests**: Test with Oracle database
6. **Package**: Create JAR file
7. **Docker Build**: Build and push Docker image
8. **Security Scan**: Scan for vulnerabilities
9. **Deploy to Staging**: Deploy to staging environment
10. **Deploy to Production**: Deploy to production (with approval)

### 6. Environment-Specific Deployments

#### Staging Environment
- Triggered on `develop` branch
- Uses `docker-compose.staging.yml`
- Accessible at `http://localhost:8082`

#### Production Environment
- Triggered on `main` branch
- Requires manual approval
- Uses `docker-compose.prod.yml`
- Accessible at `http://localhost:8083`

### 7. Monitoring and Health Checks

The application includes Spring Boot Actuator endpoints:

- **Health Check**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`
- **Prometheus**: `/actuator/prometheus`

### 8. Security Considerations

1. **Secrets Management**: Use Jenkins credentials store
2. **Docker Registry**: Use private registry for production
3. **Database Passwords**: Use environment variables
4. **Network Security**: Use Docker networks for isolation
5. **Image Scanning**: Trivy scans for vulnerabilities

### 9. Troubleshooting

#### Common Issues

1. **Database Connection Issues**:
   ```bash
   # Check if Oracle container is running
   docker-compose logs oracle-db
   
   # Test database connection
   docker exec -it spring-oracle-db sqlplus demo/demo_password@//localhost:1521/XE
   ```

2. **Jenkins Build Failures**:
   - Check Jenkins console output
   - Verify Maven and JDK configurations
   - Check Docker daemon is running

3. **Docker Build Issues**:
   ```bash
   # Test Docker build locally
   docker build -t spring-oracle:test .
   
   # Check Docker logs
   docker logs spring-oracle-app
   ```

### 10. Best Practices

1. **Branch Strategy**: Use GitFlow or GitHub Flow
2. **Code Reviews**: Require pull request reviews
3. **Testing**: Maintain high test coverage
4. **Monitoring**: Set up application monitoring
5. **Backup**: Regular database and Jenkins backups
6. **Updates**: Keep dependencies and plugins updated

### 11. Advanced Configuration

#### Custom Jenkins Configuration

Create a `jenkins.yaml` file for Jenkins Configuration as Code:

```yaml
jenkins:
  systemMessage: "Spring Oracle CI/CD Pipeline"
  numExecutors: 2
  scmCheckoutRetryCount: 3

security:
  remotingSecurity:
    enabled: true

unclassified:
  location:
    url: "http://localhost:8081/"
```

#### Webhook Configuration

For GitHub integration:

1. Go to your GitHub repository settings
2. Add webhook: `http://your-jenkins-url/github-webhook/`
3. Select events: Push, Pull Request

### 12. Performance Optimization

1. **Parallel Stages**: Use parallel execution for independent tasks
2. **Caching**: Cache Maven dependencies and Docker layers
3. **Resource Limits**: Set appropriate memory limits
4. **Build Optimization**: Use multi-stage Docker builds

## Support

For issues and questions:
1. Check Jenkins logs: `docker-compose logs jenkins`
2. Review application logs: `docker-compose logs spring-oracle-app`
3. Check database logs: `docker-compose logs oracle-db`
