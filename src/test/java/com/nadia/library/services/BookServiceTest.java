package com.nadia.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nadia.library.models.Book;
import com.nadia.library.models.Inventory;
import com.nadia.library.repositories.AuthorRepository;
import com.nadia.library.repositories.BookRepository;
import com.nadia.library.repositories.InventoryRepository;
import com.nadia.library.repositories.LoanRepository;

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
 * Unit tests for BookService class.
 */
public class BookServiceTest {
  @Mock
  private BookRepository bookRepository;

  @Mock
  private InventoryRepository inventoryRepository;

  @Mock
  private LoanRepository loanRepository;

  @Mock
  private AuthorRepository authorRepository;

  @InjectMocks
  private BookService bookService;

  @BeforeEach
  void setUp() {
      MockitoAnnotations.openMocks(this);
  }

  /**
   * Existing mocked book and inventory data for testing.
   */
  private final long EXISTING_BOOK_ID = 1L;
  private final long EXISTING_AUTHOR_ID = 1L;

  private Book createMockBook() {
    Book book = new Book();
    book.setId(EXISTING_BOOK_ID);
    book.setTitle("Test book");
    book.setAuthorId(EXISTING_AUTHOR_ID);
    return book;
  }

  private Inventory createMockInventory() {
    Inventory inventory = new Inventory();
    inventory.setBookId(EXISTING_BOOK_ID);
    inventory.setInStock(2);
    return inventory;
  }

  /**
   * Test to verify the functionality of retrieving all books from the repository.
   *
   * This test checks if the service retrieves all books from the repository.
   * It ensures that the retrieved list is not null and matches the expected list of books.
   */
  @Test
  void testGetAllBooks() {
    List<Book> books = new ArrayList<>();
    when(bookRepository.findAll()).thenReturn(books);

    List<Book> result = bookService.getAllBooks();

    assertNotNull(result);
    assertEquals(books, result);
  }

  /**
   * Test to retrieve a book by ID when the book exists in the repository.
   *
   * This test validates the retrieval of a book by a given ID from the repository.
   * It ensures that an OK status response with the book information is returned.
   */
  @Test
  void testGetBookByIdWhenBookExists() {
    Book existingBook = createMockBook();
    when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.of(existingBook));

    ResponseEntity<Book> result = bookService.getBookById(EXISTING_BOOK_ID);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(existingBook, result.getBody());
  }

  /**
   * Test to retrieve a book by ID when the book does not exist in the repository.
   *
   * This test validates the behavior when attempting to retrieve a book by a non-existent ID.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testGetBookByIdWhenBookDoesNotExist() {
    when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.empty());

    ResponseEntity<Book> result = bookService.getBookById(EXISTING_BOOK_ID);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  /**
   * Test to add a book when the author exists and the book does not already exist.
   *
   * This test checks the addition of a new book when the author exists and the book does not exist.
   * It ensures that the book is successfully added and a CREATED status response is returned.
   */
  @Test
  void testAddBook() {
    Book newBook = createMockBook();
    when(authorRepository.existsById(EXISTING_AUTHOR_ID)).thenReturn(true);
    when(bookRepository.findByAuthorIdAndTitle(newBook.getAuthorId(), newBook.getTitle())).thenReturn(null);
    when(bookRepository.save(newBook)).thenReturn(newBook);

    ResponseEntity<Book> result = bookService.addBook(newBook);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals(newBook, result.getBody());
  }

  /**
   * Test to add a book when the author exists and the book already exists.
   *
   * This test verifies the behavior when adding a book where the author exists,
   * and the book with the same title by the same author already exists.
   * It ensures that the existing book is found, the inventory is incremented,
   * and an OK status response with the existing book entity is returned.
   */
  @Test
  void testAddBookWhenBookAlreadyExists() {
    Book existingBook = createMockBook();
    when(authorRepository.existsById(EXISTING_AUTHOR_ID)).thenReturn(true);
    when(bookRepository.findByAuthorIdAndTitle(existingBook.getAuthorId(), existingBook.getTitle())).thenReturn(existingBook);

    ResponseEntity<Book> result = bookService.addBook(existingBook);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(existingBook, result.getBody());
    verify(inventoryRepository, times(1)).incrementInventory(existingBook.getId());
  }

  /**
   * Test to add a book when the author does not exist.
   *
   * This test validates the behavior when attempting to add a book where the author does not exist.
   * It ensures that a BAD_REQUEST status response is returned as expected.
   */
  @Test
  void testAddBookWhenAuthorDoesNotExist() {
    Book newBook = createMockBook();
    when(authorRepository.existsById(EXISTING_AUTHOR_ID)).thenReturn(false);

    ResponseEntity<Book> result = bookService.addBook(newBook);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  /**
   * Test to update a book when the book exists in the repository.
   *
   * This test checks the update of an existing book.
   * It ensures that the book is successfully updated and an OK status response is returned.
   */
  @Test
  void testUpdateBookWhenBookExists() {
    Book existingBook = createMockBook();
    when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.of(existingBook));

    Book updatedBook = new Book();
    updatedBook.setAuthorId(2L);
    updatedBook.setTitle("Updated title");

    when(bookRepository.save(existingBook)).thenReturn(updatedBook);

    ResponseEntity<Book> result = bookService.updateBook(EXISTING_BOOK_ID, updatedBook);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(updatedBook, result.getBody());
    assertEquals(2L, existingBook.getAuthorId());
    assertEquals("Updated title", existingBook.getTitle());
  }

  /**
   * Test to update a book when the book does not exist in the repository.
   *
   * This test verifies the behavior when attempting to update a non-existent book.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testUpdateBookWhenBookDoesNotExist() {
    when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.empty());

    Book updatedBook = new Book();
    updatedBook.setAuthorId(2L);
    updatedBook.setTitle("Updated title");

    ResponseEntity<Book> result = bookService.updateBook(EXISTING_BOOK_ID, updatedBook);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  /**
   * Test to delete all copies of a book from the inventory when the book exists, and no copies are loaned.
   *
   * This test checks the deletion of all copies of a book from the inventory
   * when the book exists and no copies are currently loaned.
   * It ensures that the book and its inventory are successfully deleted,
   * and a NO_CONTENT status response is returned.
   */
  @Test
  void testDeleteAllBookCopies() {
    Book existingBook = createMockBook();
    Inventory existingInventory = createMockInventory();

    when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.of(existingBook));
    when(inventoryRepository.findByBookId(EXISTING_BOOK_ID)).thenReturn(existingInventory);
    when(loanRepository.existsByBookId(EXISTING_BOOK_ID)).thenReturn(false);

    ResponseEntity<HttpStatus> result = bookService.deleteAllBookCopies(EXISTING_BOOK_ID);

    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    verify(bookRepository, times(1)).delete(existingBook);
    verify(inventoryRepository, times(1)).delete(existingInventory);
  }

  /**
   * Test to delete all copies of a book when the book does not exist in the repository.
   *
   * This test validates the behavior when attempting to delete copies of a non-existent book.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testDeleteAllBookCopiesWhenBookDoesNotExist() {
    when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.empty());

    ResponseEntity<HttpStatus> result = bookService.deleteAllBookCopies(EXISTING_BOOK_ID);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    verify(authorRepository, never()).delete(any());
  }

  /**
   * Test to delete all copies of a book when copies of the book are loaned.
   *
   * This test checks the deletion of copies of a book when at least one copy is currently loaned.
   * It ensures that a CONFLICT status response is returned as expected
   * and the deletion operations are not performed.
   */
  @Test
  void testDeleteAllBookCopiesWhenCopiesAreLoaned() {
    Book existingBook = createMockBook();
    when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.of(existingBook));
    when(loanRepository.existsByBookId(EXISTING_BOOK_ID)).thenReturn(true);

    ResponseEntity<HttpStatus> result = bookService.deleteAllBookCopies(EXISTING_BOOK_ID);

    assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    verify(authorRepository, never()).delete(any());
  }
}
