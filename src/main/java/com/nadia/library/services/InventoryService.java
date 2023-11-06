package com.nadia.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.nadia.library.models.Inventory;
import com.nadia.library.repositories.InventoryRepository;

@Service
public class InventoryService {
  @Autowired
  private InventoryRepository inventoryRepository;

  public List<Inventory> getAllInventory() {
    return inventoryRepository.findAll();
  }

  public ResponseEntity<Inventory> getInventoryItemById(Long id) {
    Inventory inventoryItem = inventoryRepository.findById(id).orElse(null);

    if (inventoryItem == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(inventoryItem, HttpStatus.OK);
  }

  public ResponseEntity<Inventory> updateStockofInventoryItemByBookId(Long bookId, Inventory inventory) {
    Inventory currentInventory = inventoryRepository.findByBookId(bookId);

    if (currentInventory != null) {
      currentInventory.setInStock(inventory.getInStock());
      currentInventory = inventoryRepository.save(currentInventory);
      return new ResponseEntity<>(currentInventory, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
