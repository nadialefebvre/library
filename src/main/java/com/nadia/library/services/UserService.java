package com.nadia.library.services;

import com.nadia.library.models.User;
import com.nadia.library.repositories.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class for managing User entities.
 */
@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  /**
   * Get a list of all users.
   *
   * @return A list of User entities.
   */
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Get a user by their ID.
   *
   * @param id The ID of the user to retrieve.
   * @return A ResponseEntity containing the User entity if found.
   */
  public ResponseEntity<User> getUserById(Long id) {
      User user = findUserById(id);

      if (user == null) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      return new ResponseEntity<>(user, HttpStatus.OK);
  }

  /**
   * Create a new user.
   * If the user already exists, display an error message; otherwise, create a new entry.
   *
   * @param user The User entity to create.
   * @return A ResponseEntity containing the created User entity.
   */
  public ResponseEntity<?> createUser(User user) {
    User currentUser = userRepository.findByEmail(user.getEmail());

    if(currentUser != null) {
      return new ResponseEntity<>("User with this email already exists", HttpStatus.CONFLICT);
    }

    User savedUser = userRepository.save(user);
    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
  }

  /**
   * Update an existing user.
   *
   * @param id   The ID of the user to update.
   * @param user The updated User entity.
   * @return A ResponseEntity containing the updated User entity.
   */
  public ResponseEntity<User> updateUser(Long id, User user) {
    User currentUser = findUserById(id);

    if (currentUser == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    currentUser.setName(user.getName());
    currentUser.setAddress(user.getAddress());
    currentUser.setEmail(user.getEmail());
    User updatedUser = userRepository.save(currentUser);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }

  /**
   * Delete a user by their ID.
   *
   * @param id The ID of the user to delete.
   * @return A ResponseEntity indicating the result of the delete operation.
   */
  public ResponseEntity<HttpStatus> deleteUser(Long id) {
    User user = findUserById(id);

    if (user == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    userRepository.delete(user);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Helper method to find a user by its ID.
   *
   * @param id The ID of the user to find.
   * @return The found User entity, or null if not found.
   */
  private User findUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }
}
