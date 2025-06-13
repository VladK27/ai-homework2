# AI Homework 2 - User Management System

A Spring Boot application that provides user management functionality with JWT authentication.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Authentication Flow](#authentication-flow)
- [REST API Documentation](#rest-api-documentation)
- [Testing](#testing)

## Features
- User management (CRUD operations)
- JWT-based authentication
- Role-based authorization (ADMIN and USER roles)
- Global exception handling
- Comprehensive test coverage

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

## Getting Started

### Database Setup
1. Create a PostgreSQL database named `ai_homework2`
2. Update `application.yml` with your database credentials if needed

### Running the Application
```bash
mvn spring-boot:run
```

The application will start on port 8080 by default.

## Authentication Flow

### 1. User Registration
1. Send a POST request to `/api/auth/register` with user details
2. System validates the input and creates a new user
3. Returns the created user details (without password)

### 2. User Login
1. Send a POST request to `/api/auth/login` with credentials
2. System validates credentials and generates JWT token
3. Returns the JWT token and user details

### 3. Accessing Protected Resources
1. Include the JWT token in the Authorization header:
   ```
   Authorization: Bearer <your_jwt_token>
   ```
2. System validates the token and grants access based on user role

## REST API Documentation

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
    "username": "newuser",
    "email": "user@example.com",
    "password": "password123"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "username": "user@example.com",
    "password": "password123"
}
```

### User Management Endpoints

#### Get All Users
```http
GET /api/users
Authorization: Bearer <your_jwt_token>
```

#### Get User by ID
```http
GET /api/users/{id}
Authorization: Bearer <your_jwt_token>
```

#### Create User
```http
POST /api/users
Authorization: Bearer <your_jwt_token>
Content-Type: application/json

{
    "username": "newuser",
    "email": "user@example.com",
    "password": "password123"
}
```

#### Update User
```http
PUT /api/users/{id}
Authorization: Bearer <your_jwt_token>
Content-Type: application/json

{
    "username": "updateduser",
    "email": "updated@example.com"
}
```

#### Delete User
```http
DELETE /api/users/{id}
Authorization: Bearer <your_jwt_token>
```

### Response Codes
- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

## Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserControllerTest

# Run specific test method
mvn test -Dtest=UserControllerTest#testGetUserById
```

### Test Coverage
The project includes:
- Unit tests for services and controllers
- Integration tests for API endpoints
- Security configuration tests
- Exception handling tests

## Security Considerations
- Passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours
- Role-based access control for sensitive operations
- Input validation for all endpoints
- Global exception handling for security-related errors
