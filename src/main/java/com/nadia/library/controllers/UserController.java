package com.nadia.library.controllers;

import com.nadia.library.models.User;
import com.nadia.library.services.UserService;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling User-related operations.
 *
 * A controller class responsible for managing User entities.
 */
@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;

  /**
   * Get a list of all users.
   *
   * @return A list of User entities.
   */
  @GetMapping("")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  /**
   * Get a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   * @return A ResponseEntity containing the User entity if found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
    return userService.getUserById(id);
  }

  /**
   * Create a new User.
   *
   * @param user The User entity to create.
   * @return A ResponseEntity containing the created User entity.
   */
  @PostMapping("")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    return userService.createUser(user);
  }

  /**
   * Update an existing User by their ID.
   *
   * @param id   The ID of the user to update.
   * @param user The updated User entity.
   * @return A ResponseEntity containing the updated User entity.
   */
  @PatchMapping("/{id}")
  public ResponseEntity<User> updateUser(
    @PathVariable("id") Long id,
    @Valid @RequestBody User user
  ) {
    return userService.updateUser(id, user);
  }

  /**
   * Delete a user by their ID.
   *
   * @param id The ID of the user to delete.
   * @return A ResponseEntity with HTTP status indicating the result of the delete operation.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
    return userService.deleteUser(id);
  }
}
