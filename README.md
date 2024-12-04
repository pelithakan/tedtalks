
# TedTalks API

This is a Spring Boot application for managing TED Talks. It provides APIs for CRUD operations on TED Talk data and supports importing data from a CSV file.

## Features
- **Import Data**: Import TED Talk data from a `data.csv` file.
- **CRUD Operations**: Perform CRUD (Create, Read, Update, Delete) operations on TED Talks.
- **Dockerized**: Easily run the application with Docker and Docker Compose.

## Requirements
- **JDK 17 or later**
- **Docker** and **Docker Compose** (for containerized environment)
- **Maven** (for building and running the application)

## Setup and Run Instructions

### 1. Build the Application with Maven
To build the application and install the required dependencies using Maven, run the following command:
```bash
mvn clean install
```

This will clean the previous build and install the required dependencies.

### 2. Run with Maven
To run the application using Maven, use the following command:
```bash
mvn spring-boot:run
```

This will start the Spring Boot application on the default port `8080`.

### 3. Run with Docker Compose
To run the application with Docker Compose, follow these steps:

1. **Build the Docker images** (if not already built):
   ```bash
   docker-compose build
   ```

2. **Start the containers**:
   ```bash
   docker-compose up -d
   ```

This will start the PostgreSQL database and the Spring Boot application in the background.

### 4. Import Data from CSV
To import data from a `data.csv` file, use the `/admin/import` endpoint. You can trigger this endpoint with a `curl` command:
```bash
curl -X GET http://localhost:8080/admin/import
```

This will initiate the import process. Ensure the `data.csv` file is available in the application's resources or as a configuration source.

### 5. CRUD Operations

You can access the following CRUD APIs using `curl` commands:

- **Get all TED Talks** (with pagination):
   ```bash
   curl -X GET "http://localhost:8080/api/ted-talks?page=0&size=10"
   ```

- **Get a TED Talk by ID**:
   ```bash
   curl -X GET "http://localhost:8080/api/ted-talks/{id}"
   ```

  Replace `{id}` with the desired TED Talk ID.

- **Create a new TED Talk**:
   ```bash
   curl -X POST "http://localhost:8080/api/ted-talks"    -H "Content-Type: application/json"    -d '{
         "title": "Talk Title",
         "author": "Author Name",
         "date": "2023-05-01",
         "views": 1000000,
         "likes": 50000,
         "link": "http://link.to/talk"
       }'
   ```

- **Update an existing TED Talk**:
   ```bash
   curl -X PUT "http://localhost:8080/api/ted-talks/{id}"    -H "Content-Type: application/json"    -d '{
         "title": "Updated Title",
         "author": "Updated Author",
         "date": "2023-06-01",
         "views": 1500000,
         "likes": 60000,
         "link": "http://updatedlink.to/talk"
       }'
   ```

  Replace `{id}` with the TED Talk ID you want to update.

- **Delete a TED Talk**:
   ```bash
   curl -X DELETE "http://localhost:8080/api/ted-talks/{id}"
   ```

  Replace `{id}` with the TED Talk ID you want to delete.

### 6. Database Configuration
The application uses PostgreSQL for data storage. The database is configured in the `docker-compose.yml` file. By default, it will use the following credentials:
- **Database Name**: `tedtalkdb`
- **Username**: `user`
- **Password**: `password`

The PostgreSQL database will be available on port `5432`.

## Docker Compose Configuration

The `docker-compose.yml` file defines the following services:
- **PostgreSQL Database**: For storing TED Talk data.
- **Application**: The Spring Boot application that interacts with the database.

---

### Maven Configuration

The project uses Maven for dependency management and building. The `pom.xml` includes the following:
- **Spring Boot** for the core application.
- **Kotlin** for the language and annotations.
- **PostgreSQL JDBC Driver** for database connectivity.
- **OpenCSV** for CSV file parsing.
- **Kotlin Coroutines** for asynchronous operations.

Make sure to configure your environment for JDK 17, and Maven will handle the rest.
