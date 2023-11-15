# Library application

This is a project started to learn Java, first project with this language (automn 2023)

## Overview

This is a simple Java application that serves as a basic library system, allowing to manage a collection of books. It provides a set of RESTful API endpoints for performing CRUD (Create, Read, Update, Delete) operations on books, authors, users and loans. The application is built using the Spring Boot framework and utilizes Spring Data JPA for database interactions.

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

### Books

- `GET /books`: Retrieve a list of all books.
- `GET /books/{id}`: Retrieve a specific book by its ID.
- `POST /books`: Create a new book.
- `PATCH /books/{id}`: Update an existing book (supports partial updates).
- `DELETE /books/{id}`: Delete a book by its ID.

### Inventory

- `GET /inventory`: Retrieve a list of all inventory items.
- `GET /inventory/{id}`: Retrieve a specific inventory item by its ID.
- `PATCH /inventory`: Update the stock of an inventory item by book ID.

### Loans

- `GET /loans`: Retrieve a list of all loans.
- `GET /loans/late`: Retrieve a list of all late loans.
- `GET /loans/{id}`: Retrieve a specific loan by its ID.
- `POST /loans`: Create a new loan.
- `PATCH /loans/{id}`: Renew an existing loan (if renewable).
- `DELETE /loans/{id}`: Delete a loan (when the user returns a copy).

### Users

- `GET /users`: Retrieve a list of all users.
- `GET /users/{id}`: Retrieve a specific user by their ID.
- `POST /users`: Create a new user.
- `PATCH /users/{id}`: Update an existing user.
- `DELETE /users/{id}`: Delete a user.

### Authors

- `GET /authors`: Retrieve a list of all authors.
- `GET /authors/{id}`: Retrieve a specific author by their ID.
- `POST /authors`: Create a new author.
- `PATCH /authors/{id}`: Update an existing author.
- `DELETE /authors/{id}`: Delete an author.

## Example usage

Here are some example requests you can make to interact with the API:

### Books

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
    "title": "Molloy"
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

### Inventory

- Retrieve all inventory items:

  ```
  GET http://localhost:8080/inventory
  ```

- Retrieve a specific inventory item by ID:

  ```
  GET http://localhost:8080/inventory/{id}
  ```

- Update the stock of an inventory item by book ID:
  ```
  PATCH http://localhost:8080/inventory?bookId={bookId}
  Body:
  {
    "inStock": 10
  }
  ```

### Loans

- Retrieve all loans:

  ```
  GET http://localhost:8080/loans
  ```

- Retrieve all late loans:

  ```
  GET http://localhost:8080/loans/late
  ```

- Retrieve a specific loan by ID:

  ```
  GET http://localhost:8080/loans/{id}
  ```

- Create a new loan:

  ```
  POST http://localhost:8080/loans
  Body:
  {
    "userId": 1,
    "bookId": 2
  }
  ```

- Renew an existing loan (if renewable):

  ```
  PATCH http://localhost:8080/loans/{id}
  ```

- Delete a loan (when the user returns a copy):
  ```
  DELETE http://localhost:8080/loans/{id}
  ```

### Users

- Retrieve all users:

  ```
  GET http://localhost:8080/users
  ```

- Retrieve a specific user by ID:

  ```
  GET http://localhost:8080/users/{id}
  ```

- Create a new user:

  ```
  POST http://localhost:8080/users
  Body:
  {
    "name": "John Doe"
    "address": "789 Main St."
  }
  ```

- Update an existing user:

  ```
  PATCH http://localhost:8080/users/{id}
  Body:
  {
    "name": "Updated name"
  }
  ```

- Delete a user:
  ```
  DELETE http://localhost:8080/users/{id}
  ```

### Authors

- Retrieve all authors:

  ```
  GET http://localhost:8080/authors
  ```

- Retrieve a specific author by ID:

  ```
  GET http://localhost:8080/authors/{id}
  ```

- Create a new author:

  ```
  POST http://localhost:8080/authors
  Body:
  {
    "name": "Jane Doe"
    "country": "Sweden"
  }
  ```

- Update an existing author:

  ```
  PATCH http://localhost:8080/authors/{id}
  Body:
  {
    "name": "Updated name"
  }
  ```

- Delete an author:
  ```
  DELETE http://localhost:8080/authors/{id}
  ```

## Tech

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
