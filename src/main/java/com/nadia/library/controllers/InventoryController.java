package com.nadia.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nadia.library.models.Inventory;
import com.nadia.library.repositories.InventoryRepository;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
  @Autowired
  private InventoryRepository inventoryRepository;

  @GetMapping("")
  public List<Inventory> getAllInventory() {
    return inventoryRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Inventory> getInventoryItemById(@PathVariable("id") Long id) {
    Inventory inventoryItem = inventoryRepository.findById(id).orElse(null);

    if (inventoryItem == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(inventoryItem, HttpStatus.OK);
  }

  // updates the number of copies that are available for a loan
  @PatchMapping("")
  public ResponseEntity<Inventory> updateStock(
    @RequestParam Long bookId,
    @RequestBody Inventory inventory
  ) {
    Inventory currentInventory = inventoryRepository.findByBookId(bookId);

    if (currentInventory != null) {
      currentInventory.setInStock(inventory.getInStock());;
      currentInventory = inventoryRepository.save(currentInventory);
      return new ResponseEntity<>(currentInventory, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
