package com.nadia.library.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

// TODO: change the following code of setters/getters methods to use Lombok instead (less verbose)

/**
 * Represents a Book entity.
 *
 * An entity class representing a book in the database.
 */
@Entity
public class Book {
  /**
   * The primary key for the book, generated using an identity strategy.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The author of the book.
   */
  @NotNull(message = "`author` is a mandatory field")
  private String author;

  /**
   * The title of the book.
   */
  @NotNull(message = "`title` is a mandatory field")
  private String title;

  /**
   * Default constructor for the Book class.
   */
  public Book() {}

  /**
   * Constructor to create a Book with an author and title.
   *
   * @param author The author of the book.
   * @param title  The title of the book.
   */
  public Book(String author, String title) {
    this.author = author;
    this.title = title;
  }

  /**
   * Get the ID of the book.
   *
   * @return The ID of the book.
   */
  public Long getId() {
    return id;
  }

  /**
   * Set the ID of the book.
   *
   * @param id The ID of the book.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get the author of the book.
   *
   * @return The author of the book.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Set the author of the book.
   *
   * @param author The author of the book.
   */
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * Get the title of the book.
   *
   * @return The title of the book.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set the title of the book.
   *
   * @param title The title of the book.
   */
  public void setTitle(String title) {
    this.title = title;
  }
}
