package com.nadia.library.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a Loan entity.
 *
 * An entity class representing a book loan in the database.
 */
@Entity
public class Loan {
  /**
   * The unique identifier for the loan.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * A many-to-one relationship with the `Book` entity.
   */
  @ManyToOne
  @JoinColumn(name = "bookId", referencedColumnName = "id", insertable = false, updatable = false)
  private Book book;

  /**
   * The ID of the associated book.
   */
  @NotNull(message = "`bookId` is a mandatory field")
  private Long bookId;

  /**
   * A many-to-one relationship with the `User` entity.
   */
  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
  private User user;

  /**
   * The ID of the user who borrowed the book.
   */
  @NotNull(message = "`userId` is a mandatory field")
  private Long userId;

  /**
   * Enumeration representing the status of the loan (NEW_LOAN or RENEWAL).
   */
  public enum Status {
    NEW_LOAN,
    RENEWAL
  }

  /**
   * The status of the loan.
   */
  private Status status;

  /**
   * The date on which the book was borrowed.
   */
  private LocalDate borrowingDate;

  /**
   * Default constructor for the Loan class.
   */
  public Loan() {}

  /**
   * Constructor to create a Loan with book ID, user ID, loan status, and borrowing date.
   *
   * @param bookId        The ID of the associated book.
   * @param userId        The ID of the user who borrowed the book.
   * @param status        The status of the loan (NEW_LOAN or RENEWAL).
   * @param borrowingDate The date on which the book was borrowed.
   */
  public Loan(Long bookId, Long userId, Status status, LocalDate borrowingDate) {
    this.bookId = bookId;
    this.userId = userId;
    this.status = status;
    this.borrowingDate = borrowingDate;
  }


  /**
   * Get the ID of the loan.
   *
   * @return The ID of the loan.
   */
  public Long getId() {
    return id;
  }

  /**
   * Set the ID of the loan.
   *
   * @param id The ID of the loan.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get the book ID associated with this loan.
   *
   * @return The book ID.
   */
  public Long getBookId() {
    return bookId;
  }

  /**
   * Set the book ID associated with this loan.
   *
   * @param bookId The book ID.
   */
  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

  /**
   * Get the user ID associated with this loan.
   *
   * @return The user ID.
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Set the user ID associated with this loan.
   *
   * @param userId The user ID.
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Get the status of the loan.
   *
   * @return The status of the loan.
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Set the status of the loan.
   *
   * @param status The status of the loan.
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * Get the date on which the book was borrowed.
   *
   * @return The borrowing date.
   */
  public LocalDate getBorrowingDate() {
    return borrowingDate;
  }

  /**
   * Set the date on which the book was borrowed.
   *
   * @param borrowingDate The borrowing date.
   */
  public void setBorrowingDate(LocalDate borrowingDate) {
    this.borrowingDate = borrowingDate;
  }
}
