package com.nadia.library;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// indicates that the class is an entity = it will be mapped to a database table
@Entity
// the `Book` class represents a table in a database
public class Book {
  // specifies the primary jey and how it's generated
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  // set the fields for the `Book` class
  private Long id;
  private String author;
  private String title;
  // use `Boolean` instead of `boolean` for allowing `null` value for comparison purpose on `patch`
  private Boolean isBorrowed;
  private LocalDate dueDate;

  // default constructor
  public Book() {}

  // constructor with parameters, sets fields of the `Book`object when an instance is created
  public Book(String author, String title, Boolean isBorrowed, LocalDate dueDate) {
    this.author = author;
    this.title = title;
    this.isBorrowed = isBorrowed;
    this.dueDate = dueDate;
  }

  // TODO: change the following code of setters/getters methods to use Lombok instead (less verbose)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getIsBorrowed() {
    return isBorrowed;
  }

  public void setIsBorrowed(Boolean isBorrowed) {
    this.isBorrowed = isBorrowed;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }
}
