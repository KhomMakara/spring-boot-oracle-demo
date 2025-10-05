#!/bin/bash

# Spring Oracle Development Environment Stop Script

echo "🛑 Stopping Spring Oracle Development Environment..."

# Stop all services
docker-compose down

# Remove unused volumes (optional)
read -p "🗑️  Remove unused volumes? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    docker-compose down -v
    echo "✅ Volumes removed"
fi

# Remove unused images (optional)
read -p "🗑️  Remove unused Docker images? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    docker image prune -f
    echo "✅ Unused images removed"
fi

echo "✅ Development environment stopped"
