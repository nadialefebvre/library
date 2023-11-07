package com.nadia.library.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

// indicates that the class is an entity = it will be mapped to a database table
@Entity
// the `Book` class represents a table in a database
public class Book {
  // specifies the primary key and how it's generated
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "`author` is a mandatory field")
  private String author;

  @NotNull(message = "`title` is a mandatory field")
  private String title;

  // default constructor
  public Book() {}

  // constructor with parameters, sets fields of the `Book`object when an instance is created
  public Book(String author, String title) {
    this.author = author;
    this.title = title;
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
}
