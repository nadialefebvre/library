package com.nadia.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nadia.library.models.Author;
import com.nadia.library.repositories.AuthorRepository;

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
 * Unit tests for AuthorService class.
 */
public class AuthorServiceTest {
  @Mock
  private AuthorRepository authorRepository;

  @InjectMocks
  private AuthorService authorService;

  @BeforeEach
  void setUp() {
      MockitoAnnotations.openMocks(this);
  }

  /**
   * Test to verify the functionality of retrieving all authors from the repository.
   *
   * This test checks if the service retrieves all available authors from the repository.
   * It ensures that the retrieved list is not null and matches the expected list of authors.
   */
  @Test
  void testGetAllAuthors() {
    List<Author> authors = new ArrayList<>();
    when(authorRepository.findAll()).thenReturn(authors);

    List<Author> result = authorService.getAllAuthors();

    assertNotNull(result);
    assertEquals(authors, result);
  }

  /**
   * Test to retrieve an author by ID when the author exists in the repository.
   *
   * This test validates the retrieval of an author by a given ID from the repository.
   * It ensures that an OK status response with the author information is returned.
   */
  @Test
  void testGetAuthorByIdWhenAuthorExists() {
    Long authorId = 1L;
    Author author = new Author();
    author.setId(authorId);
    when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

    ResponseEntity<Author> result = authorService.getAuthorById(authorId);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(author, result.getBody());
  }

  /**
   * Test to retrieve an author by ID when the author does not exist in the repository.
   *
   * This test validates the behavior when attempting to retrieve an author by a non-existent ID.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testGetAuthorByIdWhenAuthorDoesNotExist() {
    Long authorId = 1L;
    when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

    ResponseEntity<Author> result = authorService.getAuthorById(authorId);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  /**
   * Test to create a new author and add it to the repository.
   *
   * This test validates the successful creation of an author and its addition to the repository.
   * It ensures that a CREATED status response with the newly created author information is returned.
   */
  @Test
  void testCreateAuthor() {
    Author author = new Author();
    when(authorRepository.findByNameAndCountry(author.getName(), author.getCountry())).thenReturn(null);
    when(authorRepository.save(author)).thenReturn(author);

    ResponseEntity<?> result = authorService.createAuthor(author);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals(author, result.getBody());
  }

  /**
   * Test to handle creating an author when the author already exists.
   *
   * This test validates the behavior when attempting to create an author that already exists.
   * It ensures that a CONFLICT status response with an appropriate error message is returned.
   */
  @Test
  void testCreateAuthorWhenAuthorAlreadyExists() {
    Author existingAuthor = new Author();
    existingAuthor.setName("John Doe");
    existingAuthor.setCountry("USA");
    when(authorRepository.findByNameAndCountry(existingAuthor.getName(), existingAuthor.getCountry()))
            .thenReturn(existingAuthor);

    ResponseEntity<?> result = authorService.createAuthor(existingAuthor);

    assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    assertEquals("This author already exists", result.getBody());
  }

  /**
   * Test to update an existing author in the repository when the author exists.
   *
   * This test validates the update operation of an existing author in the repository.
   * It ensures that the author information is correctly updated and an OK status response is returned.
   */
  @Test
  void testUpdateAuthorWhenAuthorExists() {
    Long authorId = 1L;
    Author existingAuthor = new Author();
    existingAuthor.setId(authorId);
    when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));

    Author updatedAuthor = new Author();
    updatedAuthor.setName("Updated name");
    updatedAuthor.setCountry("Updated country");

    when(authorRepository.save(existingAuthor)).thenReturn(updatedAuthor);

    ResponseEntity<Author> result = authorService.updateAuthor(authorId, updatedAuthor);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(updatedAuthor, result.getBody());
    assertEquals("Updated name", existingAuthor.getName());
    assertEquals("Updated country", existingAuthor.getCountry());
  }

  /**
   * Test to update an author in the repository when the author does not exist.
   *
   * This test checks the behavior when attempting to update an author that does not exist.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testUpdateAuthorWhenAuthorDoesNotExist() {
    Long authorId = 1L;
    when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

    Author updatedAuthor = new Author();
    updatedAuthor.setName("Updated name");
    updatedAuthor.setCountry("Updated country");

    ResponseEntity<Author> result = authorService.updateAuthor(authorId, updatedAuthor);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  /**
   * Test to delete an existing author from the repository when the author exists.
   *
   * This test validates the deletion of an existing author from the repository.
   * It ensures that the author is successfully deleted and a NO_CONTENT status response is returned.
   */
  @Test
  void testDeleteAuthorWhenAuthorExists() {
    Long authorId = 1L;
    Author authorToDelete = new Author();
    authorToDelete.setId(authorId);
    when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorToDelete));

    ResponseEntity<HttpStatus> result = authorService.deleteAuthor(authorId);

    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    verify(authorRepository, times(1)).delete(authorToDelete);
  }

  /**
   * Test to delete an author from the repository when the author does not exist.
   *
   * This test verifies the behavior when attempting to delete an author that does not exist.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testDeleteAuthorWhenAuthorDoesNotExist() {
    Long authorId = 1L;
    when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

    ResponseEntity<HttpStatus> result = authorService.deleteAuthor(authorId);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    verify(authorRepository, never()).delete(any());
  }
}
