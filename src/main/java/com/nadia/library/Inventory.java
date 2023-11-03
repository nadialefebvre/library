package com.nadia.library;

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

  private Long itemId;

  // Define a Many-to-One relationship with the `Book` entity
  @ManyToOne
  // ! the following line does nothing, needs to investigate...
  @JoinColumn(name = "bookId", referencedColumnName="id") // Name of the foreign key column
  private Book book;

  private int inStock;

  public Inventory() {}

  public Inventory(Book book, int inStock) {
    this.book = book;
    this.inStock = inStock;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public int getInStock() {
    return inStock;
  }

  public void setInStock(int inStock) {
    this.inStock = inStock;
  }
}
