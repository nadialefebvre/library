# Library application

## Overview

This is a simple Java application that serves as a basic library system, allowing to manage a collection of books. It provides a set of RESTful API endpoints for performing CRUD (Create, Read, Update, Delete) operations on books. The application is built using the Spring Boot framework and utilizes Spring Data JPA for database interactions.

## Getting started

To run the Library application:

1. Java and Maven need to be installed on your system.

2. Clone the repository:

   ```
   git clone <repository_url>
   ```

3. Navigate to the project directory:

   ```
   cd LibraryApplication
   ```

4. Build the application using Maven:

   ```
   mvn clean install
   ```

5. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start and the API endpoints may be reached using a tool like Postman.

## Configuration and environment variables

Before running the application, ensure the following configurations and environment variables are set up:

1. **Database configuration**: The application is configured to use MySQL as the database. You need to have MySQL installed and running. Create a MySQL database, and make sure to replace `"mydatabase"` in the `spring.datasource.url` property in `application.properties` with the name of your database.

2. **MySQL username and password**: The application's properties file includes the MySQL username, which is set as `"root"` in the provided configuration. Replace it with your actual MySQL username. The password is loaded from the `MYSQL_PASSWORD` environment variable, which you should set in your system configuration (e.g., `.zshrc`).

3. **Spring data source configuration**: Verify that the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties in `application.properties` match your local MySQL setup.

## API endpoints

The application provides the following API endpoints for managing books:

- `GET /books`: Retrieve a list of all books.
- `GET /books/{id}`: Retrieve a specific book by its ID.
- `POST /books`: Create a new book.
- `PATCH /books/{id}`: Update an existing book (supports partial updates).
- `DELETE /books/{id}`: Delete a book by its ID.

## Example usage

Here are some example requests you can make to interact with the API:

- Retrieve all books:

  ```
  GET http://localhost:8080/books
  ```

- Retrieve a specific book by ID:

  ```
  GET http://localhost:8080/books/{id}
  ```

- Create a new book:

  ```
  POST http://localhost:8080/books
  Body:
  {
    "author": "Samuel Beckett",
    "title": "Molloy",
    "isBorrowed": false
  }
  ```

- Update an existing book (partial update):

  ```
  PATCH http://localhost:8080/books/{id}
  Body:
  {
    "title": "Updated title"
  }
  ```

- Delete a book by ID:
  ```
  DELETE http://localhost:8080/books/{id}
  ```

## Tech

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
