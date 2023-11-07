package com.nadia.library.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Loan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne // define a many-to-one relationship with the `Book` entity
  @JoinColumn(name = "bookId", referencedColumnName = "id", insertable = false, updatable = false)
  private Book book;
  @NotNull(message = "`bookId` is a mandatory field")
  private Long bookId;

  @ManyToOne // define a many-to-one relationship with the `User` entity
  @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
  private User user;
  @NotNull(message = "`userId` is a mandatory field")
  private Long userId;

  public enum Status {
    NEW_LOAN,
    RENEWAL
  }
  private Status status;

  private LocalDate borrowingDate;

  public Loan() {}

  public Loan(Long bookId, Long userId, Status status, LocalDate borrowingDate) {
    this.bookId = bookId;
    this.userId = userId;
    this.status = status;
    this.borrowingDate = borrowingDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBookId() {
    return bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public LocalDate getBorrowingDate() {
    return borrowingDate;
  }

  public void setBorrowingDate(LocalDate borrowingDate) {
    this.borrowingDate = borrowingDate;
  }
}
