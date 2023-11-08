package com.nadia.library.controllers;

import com.nadia.library.models.Inventory;
import com.nadia.library.services.InventoryService;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling Inventory-related operations.
 *
 * A controller class responsible for managing Inventory entities.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
  @Autowired
  private InventoryService inventoryService;

  /**
   * Get a list of all inventory items.
   *
   * @return A list of Inventory entities.
   */
  @GetMapping("")
  public List<Inventory> getAllInventory() {
    return inventoryService.getAllInventory();
  }

  /**
   * Get an inventory item by its ID.
   *
   * @param id The ID of the inventory item to retrieve.
   * @return A ResponseEntity containing the Inventory entity if found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Inventory> getInventoryItemById(@PathVariable("id") Long id) {
    return inventoryService.getInventoryItemById(id);
  }

  /**
   * Update the number of copies that are available for loan by book ID.
   *
   * @param bookId   The ID of the book associated with the inventory item.
   * @param inventory The updated Inventory entity.
   * @return A ResponseEntity containing the updated Inventory entity.
   */
  @PatchMapping("")
  public ResponseEntity<Inventory> updateStockofInventoryItemByBookId(
    @RequestParam Long bookId,
    @Valid @RequestBody Inventory inventory
  ) {
    return inventoryService.updateStockofInventoryItemByBookId(bookId, inventory);
  }
}
