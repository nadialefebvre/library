package com.nadia.library.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Inventory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  // define a many-to-one relationship with the `Book` entity
  @ManyToOne
  @JoinColumn(name = "bookId", referencedColumnName = "id", insertable = false, updatable = false)
  private Book book;
  private Long bookId;

  private int inStock;

  public Inventory() {}

  public Inventory(Long bookId, int inStock) {
    this.bookId = bookId;
    this.inStock = inStock;
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

  public int getInStock() {
    return inStock;
  }

  public void setInStock(int inStock) {
    this.inStock = inStock;
  }
}
