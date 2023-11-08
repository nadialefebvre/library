package com.nadia.library.repositories;

import com.nadia.library.models.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository for handling Book entities.
 *
 * A repository interface for managing Book entities in the database.
 *
 * Defines a Java interface `BookRepository` that extends the `JpaRepository` interface.
 * The `BookRepository` interface is used to interact with a database and perform CRUD operations on `Book` instances.
 * The `Long` type parameter specifies the type of the primary key for the `Book` entity (which is `id`).
 * By extending `JpaRepository`, the interface inherits a set of methods (i.e. saving, retrieving, and deleting) for common database operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  /**
   * Find a book by the author and title.
   *
   * @param author The author of the book to search for.
   * @param title  The title of the book to search for.
   * @return The Book entity with the specified author and title, if found.
   */
  Book findByAuthorAndTitle(String author, String title);
}
