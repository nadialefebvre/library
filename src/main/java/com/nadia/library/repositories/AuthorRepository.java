package com.nadia.library.repositories;

import com.nadia.library.models.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for handling Author entities.
 *
 * A repository interface for managing Author entities in the database.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
  /**
   * Find an author by their name and country.
   *
   * @param name The name of the author to search for.
   * @param country The country of the author to search for.
   * @return The Author entity with the specified name and country, if found.
   */
  Author findByNameAndCountry(String name, String country);
}
