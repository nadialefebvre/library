package com.nadia.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Represents an Inventory entity.
 *
 * An entity class representing inventory of books in the database.
 */
@Entity
public class Inventory {
  /**
   * The unique identifier for the inventory.
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
  private Long bookId;

  /**
   * The quantity of the book in stock, must not be less than 0.
   */
  @Min(value=0, message = "`inStock` can't have a value < 0")
  @NotNull(message = "`inStock` is a mandatory field: must not be null and must contain at least one non-whitespace character")
  private Integer inStock;

  /**
   * Default constructor for the Inventory class.
   */
  public Inventory() {}

  /**
   * Constructor to create an Inventory with a book ID and in-stock quantity.
   *
   * @param bookId   The ID of the associated book.
   * @param inStock  The quantity of the book in stock.
   */
  public Inventory(Long bookId, Integer inStock) {
    this.bookId = bookId;
    this.inStock = inStock;
  }

  /**
   * Get the ID of the inventory.
   *
   * @return The ID of the inventory.
   */
  public Long getId() {
    return id;
  }

  /**
   * Set the ID of the inventory.
   *
   * @param id The ID of the inventory.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get the book ID associated with this inventory.
   *
   * @return The book ID.
   */
  public Long getBookId() {
    return bookId;
  }

  /**
   * Set the book ID associated with this inventory.
   *
   * @param bookId The book ID.
   */
  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

  /**
   * Get the quantity of the book in stock.
   *
   * @return The quantity of the book in stock.
   */
  public Integer getInStock() {
    return inStock;
  }

  /**
   * Set the quantity of the book in stock.
   *
   * @param inStock The quantity of the book in stock.
   */
  public void setInStock(Integer inStock) {
    this.inStock = inStock;
  }
}
