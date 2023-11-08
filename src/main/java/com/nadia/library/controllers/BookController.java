package com.nadia.library.controllers;

import com.nadia.library.models.Book;
import com.nadia.library.services.BookService;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling Book-related operations.
 *
 * A controller class responsible for managing Book entities.
 */
@RestController
@RequestMapping("/books")
public class BookController {
  @Autowired
  private BookService bookService;

  /**
   * Get a list of all books.
   *
   * @return A list of Book entities.
   */
  @GetMapping("")
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  /**
   * Get a book by its ID.
   *
   * @param id The ID of the book to retrieve.
   * @return A ResponseEntity containing the Book entity if found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
    return bookService.getBookById(id);
  }

  /**
   * Add a new Book.
   *
   * @param book The Book entity to create.
   * @return A ResponseEntity containing the created Book entity.
   */
  @PostMapping("")
  public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
    return bookService.addBook(book);
  }

  /**
   * Update an existing Book by its ID.
   *
   * @param id   The ID of the book to update.
   * @param book The updated Book entity.
   * @return A ResponseEntity containing the updated Book entity.
   */
  @PatchMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @Valid @RequestBody Book book) {
    return bookService.updateBook(id, book);
  }

  /**
   * Delete all copies of a book (if no copy is currently loaned).
   *
   * @param id The ID of the book to delete.
   * @return A ResponseEntity with HTTP status indicating the result of the delete operation.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteAllBookCopies(@PathVariable("id") Long id) {
    return bookService.deleteAllBookCopies(id);
  }
}
