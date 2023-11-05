package com.nadia.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nadia.library.models.Book;

// defines a Java interface `BookRepository` that extends the `JpaRepository` interface
// the `BookRepository` interface is used to interact with a database and perform CRUD operations on `Book` instances
// the `Long` type parameter specifies the type of the primary key for the `Book` entity (which is `id`)
// by extending `JpaRepository`, the interface inherits a set of methods (i.e. saving, retrieving, and deleting) for common database operations
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  Book findByAuthorAndTitle(String author, String title);
}
