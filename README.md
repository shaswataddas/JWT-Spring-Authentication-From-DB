# Spring Boot JWT Authentication Application

## Overview
This application is a Spring Boot project that provides authentication and authorization using JSON Web Tokens (JWT). It secures endpoints and retrieves user details from a MySQL database instead of using in-memory user storage.

## Features
- JWT Authentication: Secure endpoints using JWT tokens.
- User Management: Manage users stored in a MySQL database.
- Spring Security: Integrated with Spring Security for robust security management.
- REST API: Provides a RESTful API for authentication and user operations.

## Technologies Used
- Spring Boot: Main framework for building the application.
- Spring Security: For implementing authentication and authorization.
- JWT: For token-based security.
- MySQL: Database for storing user details.
- Hibernate: ORM framework for database operations.
- Maven: For project management and dependency management.

## Getting Started
### Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- MySQL 8.0 or higher

## Installation
### Clone the repository:
```
git clone https://github.com/your-username/spring-boot-jwt-authentication.git
cd spring-boot-jwt-authentication
```

### Configure the database:
  1. Create a MySQL database named your_database_name.
  2. Update the database connection settings in src/main/resources/application.properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
spring.jpa.hibernate.ddl-auto=update
```

### Build the project:
```
mvn clean install
```

### Run the application:
```
mvn spring-boot:run
```

## User Endpoints
### Public Endpoints
Two endpoints are given to the user to interact with the application.
---
Create User
**EndPoint** - *http://localhost:8081/auth/create-user*
**Request Payload -**
```
{
    "name":"Shaswata",
    "email":"shaswata@abc.com",
    "password":"shas1234",
    "aadharNo":"1234873891"
}
```
**Response -**
```
{
    "userId": "22",
    "name": "Shaswata",
    "email": "shaswata@abc.com",
    "password": "$2a$10$mVDQKGawwl7JT5gv.9TeGeOKprr8IfmHdg4CQM.iE1AAzKC1170le",
    "aadharNo": "1234873891",
    "enabled": true,
    "credentialsNonExpired": true,
    "username": "rohit@abc.com",
    "authorities": null,
    "accountNonExpired": true,
    "accountNonLocked": true
}
```

---
User Login
**EndPoint** - *http://localhost:8081/auth/login*
**Request Payload -**
```
{
    "email":"shaswata@abc.com",
    "password":"shas1234"
}
```
**Response -**
```
{
    "jwtToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2hpdEBhYmMuY29tIiwiaWF0IjoxNzE3MjcxNzA4LCJleHAiOjE3MTcyODk3MDh9.JSNkwknHhCFa5rJmChzSg-4UspUagnxNGr76Bo9MPdrqxGHBCy0rNZ8rPPw6kGegQt3tBmNDWBQpyojhHQZjKQ",
    "username": "shaswata@abc.com"
}
```


### Secure Endpoints
Two endpoints are given to the user to interact with the application with the JWT Token.
Without JWT Token the user is not allowed to get the desire response.
---
Home Route
**EndPoint** - *http://localhost:8081/home/user*
**Authentication** - {Set the jwt token which you get after hit the /login url}
**Response -**
```
[
   {
        "userId": "22",
        "name": "Shaswata",
        "email": "shaswata@abc.com",
        "password": "$2a$10$mVDQKGawwl7JT5gv.9TeGeOKprr8IfmHdg4CQM.iE1AAzKC1170le",
        "aadharNo": "1234873891",
        "enabled": true,
        "credentialsNonExpired": true,
        "username": "rohit@abc.com",
        "authorities": null,
        "accountNonExpired": true,
        "accountNonLocked": true
    }
]
```

---
Current User
**EndPoint** - *http://localhost:8081/home/current-user*
**Authentication** - {Set the jwt token which you get after hit the /login url}
**Response -**
```
shaswata@abc.com is currently logged in
```


