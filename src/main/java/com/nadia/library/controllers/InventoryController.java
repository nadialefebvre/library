package com.nadia.library.controllers;

import com.nadia.library.models.Inventory;
import com.nadia.library.services.InventoryService;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
  @Autowired
  private InventoryService inventoryService;

  @GetMapping("")
  public List<Inventory> getAllInventory() {
    return inventoryService.getAllInventory();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Inventory> getInventoryItemById(@PathVariable("id") Long id) {
    return inventoryService.getInventoryItemById(id);
  }

  // updates the number of copies that are available for a loan
  @PatchMapping("")
  public ResponseEntity<Inventory> updateStockofInventoryItemByBookId(
    @RequestParam Long bookId,
    @Valid @RequestBody Inventory inventory
  ) {
    return inventoryService.updateStockofInventoryItemByBookId(bookId, inventory);
  }
}
