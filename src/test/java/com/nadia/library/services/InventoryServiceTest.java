package com.nadia.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.nadia.library.models.Inventory;
import com.nadia.library.repositories.InventoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for InventoryService class.
 */
public class InventoryServiceTest {
  @Mock
  private InventoryRepository inventoryRepository;

  @InjectMocks
  private InventoryService inventoryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Test to verify the functionality of retrieving all inventory items from the repository.
   *
   * This test checks if the service retrieves all inventory items from the repository.
   * It ensures that the retrieved list is not null and matches the expected list of inventory items.
   */
   @Test
   void testGetAllInventory() {
    List<Inventory> allInventory = new ArrayList<>();
    when(inventoryRepository.findAll()).thenReturn(allInventory);

    List<Inventory> result = inventoryService.getAllInventory();

    assertNotNull(result);
    assertEquals(allInventory, result);
   }

  /**
   * Test to retrieve an inventory item by ID when the inventory item exists in the repository.
   *
   * This test validates the retrieval of an inventory item by a given ID from the repository.
   * It ensures that an OK status response with the inventory item is returned.
   */
  @Test
  void testGetInventoryItemByIdWhenItemExists() {
    Long inventoryId = 1L;
    Inventory inventory = new Inventory();
    inventory.setId(inventoryId);
    when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(inventory));

    ResponseEntity<Inventory> result = inventoryService.getInventoryItemById(inventoryId);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(inventory, result.getBody());
  }

  /**
   * Test to retrieve an inventory item by ID when the inventory item does not exist in the repository.
   *
   * This test validates the behavior when attempting to retrieve an inventory item by a non-existent ID.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testGetInventoryItemByIdWhenItemDoesNotExist() {
    Long inventoryId = 1L;
    when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.empty());

    ResponseEntity<Inventory> result = inventoryService.getInventoryItemById(inventoryId);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  /**
   * Test to update the stock of an existing inventory item.
   *
   * This test validates the behavior when updating the stock of an inventory item.
   * It ensures that an OK status response is returned along with the updated entity.
   */
  @Test
  void testUpdateStockofInventoryItemByBookIdWhenItemExists() {
    Long bookId = 1L;
    Inventory existingInventory = new Inventory();
    existingInventory.setBookId(bookId);
    existingInventory.setInStock(10);

    when(inventoryRepository.findByBookId(bookId)).thenReturn(existingInventory);

    Inventory updatedInventory = new Inventory();
    updatedInventory.setBookId(bookId);
    updatedInventory.setInStock(20);

    when(inventoryRepository.save(existingInventory)).thenReturn(updatedInventory);

    ResponseEntity<Inventory> result = inventoryService.updateStockofInventoryItemByBookId(bookId, updatedInventory);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(updatedInventory, result.getBody());
    assertEquals(20, existingInventory.getInStock()); // Additional validation for updated stock
  }

  /**
   * Test to update the stock of a non-existing inventory item.
   *
   * This test validates the behavior when attempting to update the stock of a non-existing inventory item.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testUpdateStockofInventoryItemByBookIdWhenItemDoesNotExist() {
    Long bookId = 1L;
    when(inventoryRepository.findByBookId(bookId)).thenReturn(null);

    Inventory updatedInventory = new Inventory();
    updatedInventory.setBookId(bookId);
    updatedInventory.setInStock(20);

    ResponseEntity<Inventory> result = inventoryService.updateStockofInventoryItemByBookId(bookId, updatedInventory);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }
}
