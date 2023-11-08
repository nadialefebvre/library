package com.nadia.library.services;

import com.nadia.library.models.Author;
import com.nadia.library.repositories.AuthorRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Author entities.
 */
@Service
public class AuthorService {
  @Autowired
  private AuthorRepository authorRepository;

  /**
   * Get a list of all authors.
   *
   * @return A list of Author entities.
   */
  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  /**
   * Get an author by their ID.
   *
   * @param id The ID of the author to retrieve.
   * @return A ResponseEntity containing the Author entity if found.
   */
  public ResponseEntity<Author> getAuthorById(Long id) {
    Author author = authorRepository.findById(id).orElse(null);

    if (author == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(author, HttpStatus.OK);
  }

  /**
   * Create a new Author.
   *
   * @param author The Author entity to create.
   * @return A ResponseEntity containing the created Author entity.
   */
  public ResponseEntity<Author> createAuthor(Author author) {
    Author savedAuthor = authorRepository.save(author);
    return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
  }

  /**
   * Update an existing Author by their ID.
   *
   * @param id     The ID of the author to update.
   * @param author The updated Author entity.
   * @return A ResponseEntity containing the updated Author entity.
   */
  public ResponseEntity<Author> updateAuthor(Long id, Author author) {
    Author currentAuthor = authorRepository.findById(id).orElse(null);

    if (currentAuthor == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    currentAuthor.setName(author.getName());
    currentAuthor.setCountry(author.getCountry());
    Author updatedAuthor = authorRepository.save(currentAuthor);
    return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
  }

  /**
   * Delete an author by their ID.
   *
   * @param id The ID of the author to delete.
   * @return A ResponseEntity with HTTP status indicating the result of the delete operation.
   */
  public ResponseEntity<HttpStatus> deleteAuthor(Long id) {
    Author author = authorRepository.findById(id).orElse(null);

    if (author == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    authorRepository.delete(author);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
