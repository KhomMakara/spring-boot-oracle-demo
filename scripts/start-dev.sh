#!/bin/bash

# Spring Oracle Development Environment Startup Script

echo "🚀 Starting Spring Oracle Development Environment..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Check if Docker Compose is available
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed."
    exit 1
fi

# Stop any existing containers
echo "🛑 Stopping existing containers..."
docker-compose down

# Start the development environment
echo "🏗️  Building and starting services..."
docker-compose up -d --build

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 30

# Check service health
echo "🔍 Checking service health..."

# Check Oracle Database
if docker-compose exec -T oracle-db sqlplus -L system/oracle123@//localhost:1521/XE @/dev/null > /dev/null 2>&1; then
    echo "✅ Oracle Database is ready"
else
    echo "⚠️  Oracle Database is starting up..."
fi

# Check Spring Boot Application
if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ Spring Boot Application is ready"
else
    echo "⚠️  Spring Boot Application is starting up..."
fi

# Check Jenkins
if curl -f http://localhost:8081 > /dev/null 2>&1; then
    echo "✅ Jenkins is ready"
else
    echo "⚠️  Jenkins is starting up..."
fi

echo ""
echo "🎉 Development environment is starting up!"
echo ""
echo "📋 Service URLs:"
echo "   Spring Boot App: http://localhost:8080"
echo "   Jenkins:         http://localhost:8081"
echo "   Oracle DB:       localhost:1521"
echo ""
echo "🔑 Jenkins Initial Password:"
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword 2>/dev/null || echo "   Jenkins is still starting up..."
echo ""
echo "📖 For detailed setup instructions, see: jenkins-setup.md"
echo ""
echo "🛠️  Useful commands:"
echo "   View logs:        docker-compose logs -f"
echo "   Stop services:    docker-compose down"
echo "   Restart app:      docker-compose restart spring-oracle-app"
echo ""
