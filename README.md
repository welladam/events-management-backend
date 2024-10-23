# Events Management Backend

This is the backend application for managing events, built with Java, Spring Boot, and PostgreSQL. It provides APIs for creating, updating, viewing, and deleting events, and integrates with a file upload service for handling images.

## Table of Contents
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Running the Project](#running-the-project)
- [Environment Variables](#environment-variables)
- [PostgreSQL Configuration](#postgreSQL-configuration)
- [Fivemanage API Integration](#fivemanage-api-integration)
- [Project Structure](#project-structure)
- [Pending Improvements](#pending-improvements)

## Technologies Used
The project uses the following technologies:
- **Java**: For backend development.
- **Spring Boot**: For creating RESTful APIs.
- **PostgreSQL**: As the relational database.
- **Hibernate**: For ORM (Object Relational Mapping).
- **Lombok**: For reducing boilerplate code.

## Installation
To set up the project locally, follow these steps:

### Prerequisites
- [Java 11 or higher](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)

### Steps
1. Clone the repository:
   ```
   git clone https://github.com/welladam/events-management-backend.git
   ```
2. Navigate to the project directory:
   ```
   cd events-management-backend
   ```
3. Install the dependencies using Maven:
   ```
   mvn clean install
   ```

## Running the Project
You can run the project using one of the following methods:

### Using Maven
```
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8080`.

## Environment Variables
The backend uses environment variables to manage configuration settings. Create an `application.properties` file in the resources folder with the following variables:

```
spring.application.name=events-management-backend
spring.datasource.url=jdbc:postgresql://localhost:5432/events
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.servlet.multipart.max-file-size=6MB
spring.servlet.multipart.max-request-size=6MB
cors.allowed-origins=http://localhost:3000
fivemanage.api.url=
fivemanage.api.token=
```
You can see all the variables in the `application.properties.example` file.

## PostgreSQL Configuration
To set up **PostgreSQL** for this project:

1. **Install PostgreSQL**: Download and install [PostgreSQL](https://www.postgresql.org/download/) if it’s not already installed.
2. **Create a Database**: After installing PostgreSQL, create a database for the project:
   ```
   CREATE DATABASE events;
   ```
3. **Configure Database User**: Ensure you have a user with the necessary permissions. If needed, create a new user and grant permissions:
   ```
   CREATE USER your_db_username WITH PASSWORD 'your_db_password';
   GRANT ALL PRIVILEGES ON DATABASE events_db TO your_db_username;
   ```
4. **Update Environment Variables**: Set the following variables in the `application.properties` file:
   ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/events
    spring.datasource.username=your_db_username
    spring.datasource.password=your_db_password
   ```

## Fivemanage API Integration
The project integrates with **Fivemanage API** to handle file uploads:

1. **API Key Setup**: Obtain an API key from [Fivemanage](https://www.fivemanage.com/upload) to authenticate your requests.
2. **Set the API Environment Variables**: Add the following variables to the `application.properties` file:
   ```
    fivemanage.api.url=https://api.fivemanage.com/upload
    fivemanage.api.token=your_fivemanage_api_token
   ```
3. **File Upload Handling**:
    - When creating or updating events with images, the backend sends a request to Fivemanage to upload the file, retrieves the file URL from the response, and stores it in the database.

## Project Structure
The main folders and files are organized as follows:
```
src/
│
├── main/
│   ├── java/
│   │   └── com/
│   │       └── welladam/
│   │           └── events_management_backend/
│   │               ├── config/         # Config override classes
│   │               ├── controllers/    # REST controllers
│   │               ├── models/         # Entity classes
│   │               ├── repositories/   # Database repositories
│   │               └── services/       # Business logic
│   │
│   └── resources/
│       ├── application.properties      # Spring Boot properties
│       └── static/                     # Static files (if any)
│
└── test/                               # Test files
```

## Pending Improvements
Here are some potential improvements that can be made to the project:
1. **Improve Error Handling**: Add more descriptive error messages, especially for API requests.
2. **Implement Unit Tests**: Add unit tests using JUnit and Mockito to improve code reliability.
3. **Enhance API Documentation**: Integrate Swagger for better API documentation.
4. **Implement Caching**: Use caching mechanisms (e.g., Redis) to improve response times for frequently accessed data.
5. **Improve Security**: Add JWT authentication and authorization for API endpoints.

