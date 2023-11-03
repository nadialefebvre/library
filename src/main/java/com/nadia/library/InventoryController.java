package com.nadia.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Inventory> getInventoryById(@PathVariable("id") Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);

        if (inventory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);

        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable("id") Long id, @RequestBody Inventory inventory) {
        Inventory currentInventory = inventoryRepository.findById(id).orElse(null);

        if (currentInventory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (inventory.getInStock() >= 0) {
            currentInventory.setInStock(inventory.getInStock());
        }

        Inventory updatedInventory = inventoryRepository.save(currentInventory);

        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteInventory(@PathVariable("id") Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);

        if (inventory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        inventoryRepository.delete(inventory);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
