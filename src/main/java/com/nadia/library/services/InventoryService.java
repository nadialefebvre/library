package com.nadia.library.services;

import com.nadia.library.models.Inventory;
import com.nadia.library.repositories.InventoryRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
  @Autowired
  private InventoryRepository inventoryRepository;

  public List<Inventory> getAllInventory() {
    return inventoryRepository.findAll();
  }

  public ResponseEntity<Inventory> getInventoryItemById(Long id) {
    Inventory inventory = inventoryRepository.findById(id).orElse(null);

    if (inventory == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(inventory, HttpStatus.OK);
  }

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
