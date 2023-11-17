package com.nadia.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
   * A many-to-one relationship with the `Author` entity.
   */
  @ManyToOne
  @JoinColumn(name = "authorId", referencedColumnName = "id", insertable = false, updatable = false)
  private Author author;

  /**
   * The ID of the associated author.
   */
  @NotNull(message = "`authorId` is a mandatory field: must not be null")
  private Long authorId;

  /**
   * The title of the book.
   */
  @NotBlank(message = "`title` is a mandatory field: must not be null and must contain at least one non-whitespace character")
  private String title;

  /**
   * Default constructor for the Book class.
   */
  public Book() {}

  /**
   * Constructor to create a Book with an authorId and title.
   *
   * @param authorId The ID of the author of the book.
   * @param title  The title of the book.
   */
  public Book(Long authorId, String title) {
    this.authorId = authorId;
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
   * Get the authot ID associated with this book.
   *
   * @return The author ID.
   */
  public Long getAuthorId() {
    return authorId;
  }

  /**
   * Set the authot ID associated with this book.
   *
   * @param author The author ID.
   */
  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
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
