package com.nadia.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Inventory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne // defines a many-to-one relationship with the `Book` entity
  @JoinColumn(name = "bookId", referencedColumnName = "id", insertable = false, updatable = false)
  private Book book;
  private Long bookId;

  @Min(value=0, message = "`inStock` can't have a value < 0")
  @NotNull(message = "`inStock` is a mandatory field")
  private Integer inStock; // uses `Integer` instead of `int` to allow a `null` value

  public Inventory() {}

  public Inventory(Long bookId, Integer inStock) {
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

  public Integer getInStock() {
    return inStock;
  }

  public void setInStock(Integer inStock) {
    this.inStock = inStock;
  }
}
