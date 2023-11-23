package com.nadia.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nadia.library.models.User;
import com.nadia.library.repositories.UserRepository;

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
 * Unit tests for UserService class.
 */
class UserServiceTest {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
      MockitoAnnotations.openMocks(this);
  }

  /**
   * Test to retrieve a list of all users from the repository.
   *
   * This test verifies the retrieval of all users from the repository.
   * It ensures that the returned list of users is not null and matches the expected list.
   */
  @Test
  void testGetAllUsers() {
    List<User> users = new ArrayList<>();
    when(userRepository.findAll()).thenReturn(users);

    List<User> result = userService.getAllUsers();

    assertNotNull(result);
    assertEquals(users, result);
  }

  /**
   * Test to retrieve a user by their ID when the user exists.
   *
   * This test validates the retrieval of a user by a given ID when the user exists in the repository.
   * It ensures that a successful response with the user information is returned.
   */
  @Test
  void testGetUserByIdWhenUserExists() {
    Long userId = 1L;
    User user = new User();
    user.setId(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    ResponseEntity<User> result = userService.getUserById(userId);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(user, result.getBody());
  }

  /**
   * Test to retrieve a user by their ID when the user does not exist.
   *
   * This test checks the behavior when attempting to retrieve a user by a non-existent ID.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testGetUserByIdWhenUserDoesNotExist() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    ResponseEntity<User> result = userService.getUserById(userId);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  /**
   * Test to create a new user.
   *
   * This test validates the creation of a new user in the repository.
   * It ensures that a CREATED status response with the created user information is returned.
   */
  @Test
  void testCreateUser() {
      User user = new User();
      when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
      when(userRepository.save(user)).thenReturn(user);

      ResponseEntity<?> result = userService.createUser(user);

      assertEquals(HttpStatus.CREATED, result.getStatusCode());
      assertEquals(user, result.getBody());
  }

  /**
   * Test to handle creating a user when the user already exists.
   *
   * This test validates the behavior when attempting to create a user that already exists.
   * It ensures that a CONFLICT status response with an appropriate error message is returned.
   */
  @Test
  void testCreateUserWhenUserAlreadyExists() {
    User existingUser = new User();
    existingUser.setEmail("existing@example.com");
    when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(existingUser);

    ResponseEntity<?> result = userService.createUser(existingUser);

    assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    assertEquals("User with this email already exists", result.getBody());
  }

  /**
   * Test to update an existing user.
   *
   * This test validates the update operation of an existing user in the repository.
   * It ensures that the user information is correctly updated and an OK status response is returned.
   */
  @Test
  void testUpdateUserWhenUserExists() {
    Long userId = 1L;
    User existingUser = new User();
    existingUser.setId(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

    User updatedUser = new User();
    updatedUser.setName("Updated name");
    updatedUser.setAddress("Updated address");
    updatedUser.setEmail("updated@example.com");

    when(userRepository.save(existingUser)).thenReturn(updatedUser);

    ResponseEntity<User> result = userService.updateUser(userId, updatedUser);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(updatedUser, result.getBody());
    assertEquals("Updated name", existingUser.getName()); // Additional validation for updated fields
    assertEquals("Updated address", existingUser.getAddress()); // Additional validation for updated fields
    assertEquals("updated@example.com", existingUser.getEmail()); // Additional validation for updated fields
  }

  /**
   * Test to update a user when the user does not exist.
   *
   * This test validates the behavior when attempting to update a user that does not exist.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testUpdateUserWhenUserDoesNotExist() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    User updatedUser = new User();
    updatedUser.setName("Updated name");
    updatedUser.setAddress("Updated address");
    updatedUser.setEmail("updated@example.com");

    ResponseEntity<User> result = userService.updateUser(userId, updatedUser);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  /**
   * Test to delete a user by their ID when the user exists.
   *
   * This test validates the deletion of a user by their ID from the repository.
   * It ensures that the user is successfully deleted, and a NO_CONTENT status response is returned.
   */
  @Test
  void testDeleteUserWhenUserExists() {
    Long userId = 1L;
    User userToDelete = new User();
    userToDelete.setId(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.of(userToDelete));

    ResponseEntity<HttpStatus> result = userService.deleteUser(userId);

    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    verify(userRepository, times(1)).delete(userToDelete);
  }

  /**
   * Test to delete a user when the user does not exist.
   *
   * This test validates the behavior when attempting to delete a user that does not exist.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testDeleteUserWhenUserDoesNotExist() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    ResponseEntity<HttpStatus> result = userService.deleteUser(userId);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    verify(userRepository, never()).delete(any());
    }
}
