package com.nadia.library.services;

import com.nadia.library.models.Inventory;
import com.nadia.library.repositories.InventoryRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Inventory entities.
 */
@Service
public class InventoryService {
  @Autowired
  private InventoryRepository inventoryRepository;

  /**
   * Get a list of all inventory items.
   *
   * @return A list of Inventory entities.
   */
  public List<Inventory> getAllInventory() {
    return inventoryRepository.findAll();
  }

  /**
   * Get an inventory item by its ID.
   *
   * @param id The ID of the inventory item to retrieve.
   * @return A ResponseEntity containing the Inventory entity if found.
   */
  public ResponseEntity<Inventory> getInventoryItemById(Long id) {
    Inventory inventory = inventoryRepository.findById(id).orElse(null);

    if (inventory == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(inventory, HttpStatus.OK);
  }


  /**
   * Update the stock of an inventory item by book ID.
   *
   * @param bookId    The book ID associated with the inventory item to update.
   * @param inventory The updated Inventory entity.
   * @return A ResponseEntity containing the updated Inventory entity.
   */
  public ResponseEntity<Inventory> updateStockofInventoryItemByBookId(Long bookId, Inventory inventory) {
    Inventory currentInventory = inventoryRepository.findByBookId(bookId);

    if (currentInventory == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    currentInventory.setInStock(inventory.getInStock());
    Inventory updatedInventory = inventoryRepository.save(currentInventory);
    return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
  }
}
