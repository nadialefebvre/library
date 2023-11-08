package com.nadia.library.controllers;

import com.nadia.library.models.Author;
import com.nadia.library.services.AuthorService;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling Author-related operations.
 *
 * A controller class responsible for managing Author entities.
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {
  @Autowired
  private AuthorService authorService;

  /**
   * Get a list of all authors.
   *
   * @return A list of Author entities.
   */
  @GetMapping("")
  public List<Author> getAllAuthors() {
    return authorService.getAllAuthors();
  }

  /**
   * Get an author by their ID.
   *
   * @param id The ID of the author to retrieve.
   * @return A ResponseEntity containing the Author entity if found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {
    return authorService.getAuthorById(id);
  }

  /**
   * Create a new Author.
   *
   * @param author The Author entity to create.
   * @return A ResponseEntity containing the created Author entity.
   */
  @PostMapping("")
  public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
    return authorService.createAuthor(author);
  }

  /**
   * Update an existing Author by their ID.
   *
   * @param id     The ID of the author to update.
   * @param author The updated Author entity.
   * @return A ResponseEntity containing the updated Author entity.
   */
  @PatchMapping("/{id}")
  public ResponseEntity<Author> updateAuthor(@PathVariable("id") Long id, @Valid @RequestBody Author author) {
    return authorService.updateAuthor(id, author);
  }

  /**
   * Delete an author by their ID.
   *
   * @param id The ID of the author to delete.
   * @return A ResponseEntity with HTTP status indicating the result of the delete operation.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") Long id) {
    return authorService.deleteAuthor(id);
  }
}
