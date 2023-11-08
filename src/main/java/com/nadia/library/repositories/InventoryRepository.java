package com.nadia.library.repositories;

import com.nadia.library.models.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for handling Inventory entities.
 *
 * A repository interface for managing Inventory entities in the database.
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  /**
   * Find an inventory item by the book ID.
   *
   * @param bookId The ID of the book associated with the inventory item to search for.
   * @return The Inventory entity with the specified book ID, if found.
   */
  Inventory findByBookId(Long bookId);

  /**
   * Get the inventory in-stock value for a book.
   *
   * @param bookId The ID of the book associated with the inventory item.
   * @return The in-stock value for the book.
   */
  public default Integer inventoryInStockValue(Long bookId) {
    Inventory inventoryEntry = findByBookId(bookId);
    return inventoryEntry.getInStock();
  }

  /**
   * Add a new inventory item for a book with an initial in-stock value of 1.
   *
   * @param bookId The ID of the book for which an inventory item is added.
   */
  public default void addInventoryItem(Long bookId) {
    Inventory inventoryItem = new Inventory();
    inventoryItem.setBookId(bookId);
    inventoryItem.setInStock(1);
    save(inventoryItem);
  }

  /**
   * Increment the in-stock value for a book's inventory.
   *
   * @param bookId The ID of the book associated with the inventory item.
   */
  public default void incrementInventory(Long bookId) {
    Inventory inventoryEntry = findByBookId(bookId);
    inventoryEntry.setInStock(inventoryEntry.getInStock() + 1);
    save(inventoryEntry);
  }

  /**
   * Decrement the in-stock value for a book's inventory.
   *
   * @param bookId The ID of the book associated with the inventory item.
   */
  public default void decrementInventory(Long bookId) {
    Inventory inventoryEntry = findByBookId(bookId);
    inventoryEntry.setInStock(inventoryEntry.getInStock() - 1);
    save(inventoryEntry);
  }
}
