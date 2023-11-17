package com.nadia.library.repositories;

import com.nadia.library.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for handling User entities.
 *
 * A repository interface for managing User entities in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Find a user by their email.
   *
   * @param email The email of the user to search for.
   * @return The User entity with the specified email, if found.
   */
  User findByEmail(String email);
}
