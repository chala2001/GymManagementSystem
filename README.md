# Gym Management System

A full-stack web application designed to streamline gym operations by supporting role-based access for Admins, Trainers, and Clients.

## System Overview
The system enables efficient management of gym users, trainer-client relationships, and workout planning through a centralized web platform. It follows a modular architecture with a React frontend and a Spring Boot backend, ensuring scalability and maintainability.

## Key Features
- Role-based access for Admin, Trainer, and Client users
- Admin can manage trainers and system users
- Clients can register and access assigned workout schedules
- Trainers can manage assigned clients and create personalized workout plans
- Event-driven email notifications using Apache Kafka

## Architecture
- Frontend communicates with backend via RESTful APIs
- Backend handles business logic, security, and data persistence
- Apache Kafka is used for asynchronous event-driven notifications
- MySQL database ensures reliable relational data storage

## Tech Stack
- Backend: Spring Boot
- Frontend: React
- Database: MySQL
- Messaging: Apache Kafka
- Security: HTTP-based security

## Repository Structure
- `Frontend/` – React frontend application
- `GymManagementBackend/` – Spring Boot backend services
- `docker-compose.yml` – Docker configuration for dependent services
