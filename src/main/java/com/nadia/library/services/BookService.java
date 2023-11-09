package com.nadia.library.services;

import com.nadia.library.models.Book;
import com.nadia.library.models.Inventory;
import com.nadia.library.repositories.AuthorRepository;
import com.nadia.library.repositories.BookRepository;
import com.nadia.library.repositories.InventoryRepository;
import com.nadia.library.repositories.LoanRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Book entities.
 */
@Service
public class BookService {
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private InventoryRepository inventoryRepository;
  @Autowired
  private LoanRepository loanRepository;
  @Autowired
  private AuthorRepository authorRepository;

  /**
   * Get a list of all books.
   *
   * @return A list of Book entities.
   */
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  /**
   * Get a book by its ID.
   *
   * @param id The ID of the book to retrieve.
   * @return A ResponseEntity containing the Book entity if found.
   */
  public ResponseEntity<Book> getBookById(Long id) {
    Book book = findBookById(id);

    if (book == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  /**
   * Add a book to the library's collection.
   * If the book already exists, increment the in-stock value; otherwise, create a new entry.
   *
   * @param book The Book entity to add.
   * @return A ResponseEntity containing the added Book entity.
   */
  public ResponseEntity<Book> addBook(Book book) {
    Book bookEntry = bookRepository.findByAuthorIdAndTitle(book.getAuthorId(), book.getTitle());

    if (!doesAuthorExistById(book.getAuthorId())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    if (bookEntry != null) {
      inventoryRepository.incrementInventory(bookEntry.getId());
      return new ResponseEntity<>(bookEntry, HttpStatus.OK);
    }

    Book savedBook = bookRepository.save(book);
    inventoryRepository.addInventoryItem(savedBook.getId());
    return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
  }

  /**
   * Update an existing Book by its ID.
   *
   * @param id   The ID of the book to update.
   * @param book The updated Book entity.
   * @return A ResponseEntity containing the updated Book entity.
   */
  public ResponseEntity<Book> updateBook(Long id, Book book) {
    Book currentBook = findBookById(id);

    if (currentBook == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    currentBook.setAuthorId(book.getAuthorId());
    currentBook.setTitle(book.getTitle());
    Book updatedBook = bookRepository.save(currentBook);
    return new ResponseEntity<>(updatedBook, HttpStatus.OK);
  }

  /**
   * Delete all copies of a book (if no copies are currently loaned).
   *
   * @param id The ID of the book to delete.
   * @return A ResponseEntity with HTTP status indicating the result of the delete operation.
   */
  public ResponseEntity<HttpStatus> deleteAllBookCopies(Long id) {
    Book book = findBookById(id);
    Inventory inventory = inventoryRepository.findByBookId(id);

    if (book == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (isACopyLoaned(id)) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    inventoryRepository.delete(inventory);
    bookRepository.delete(book);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Helper method to find a book by its ID.
   *
   * @param id The ID of the book to find.
   * @return The found Book entity, or null if not found.
   */
  private Book findBookById(Long id) {
    return bookRepository.findById(id).orElse(null);
  }

  /**
   * Check if an author with the given ID exists in the repository.
   * @param id The ID of the author to check.
   * @return True if the author exists, false otherwise.
   */
  private boolean doesAuthorExistById(Long id) {
    return authorRepository.existsById(id);
  }

  /**
   * Checks if any copy of a book is currently loaned.
   *
   * @param id The ID of the book to check for loaned copies.
   * @return True if at least one copy is loaned, false otherwise.
   */
  private boolean isACopyLoaned(Long id) {
    return loanRepository.existsByBookId(id);
  }
}
