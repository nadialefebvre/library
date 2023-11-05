package com.nadia.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nadia.library.models.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  Inventory findByBookId(Long bookId);

  public default int inventoryInStockValue(Long bookId) {
    Inventory inventoryEntry = findByBookId(bookId);
    return inventoryEntry.getInStock();
  }

  public default void addInventoryItem(Long bookId) {
    Inventory inventoryItem = new Inventory();
    inventoryItem.setBookId(bookId);
    inventoryItem.setInStock(1);
    save(inventoryItem);
  }

  public default void incrementInventory(Long bookId) {
    Inventory inventoryEntry = findByBookId(bookId);
    inventoryEntry.setInStock(inventoryEntry.getInStock() + 1);
    save(inventoryEntry);
  }

  public default void decrementInventory(Long bookId) {
    Inventory inventoryEntry = findByBookId(bookId);
    inventoryEntry.setInStock(inventoryEntry.getInStock() - 1);
    save(inventoryEntry);
  }
}
