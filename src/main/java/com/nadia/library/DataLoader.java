package com.nadia.library;

import com.nadia.library.models.*;
import com.nadia.library.models.Loan.Status;
import com.nadia.library.repositories.*;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataLoader is a component that provides a method (`run`) that will be executed when the application starts.
 */
@Component
public class DataLoader implements CommandLineRunner {
  private final BookRepository bookRepository;
  private final InventoryRepository inventoryRepository;
  private final UserRepository userRepository;
  private final LoanRepository loanRepository;
  private final AuthorRepository authorRepository;

/**
 * Constructor for DataLoader.
 * @param bookRepository The repository for managing Book entities.
 * @param inventoryRepository The repository for managing Inventory entities.
 * @param userRepository The repository for managing User entities.
 * @param loanRepository The repository for managing Loan entities.
 * @param authorRepository The repository for managing Author entities.
 */
  public DataLoader(
    BookRepository bookRepository,
    InventoryRepository inventoryRepository,
    UserRepository userRepository,
    LoanRepository loanRepository,
    AuthorRepository authorRepository
    ) {
    this.bookRepository = bookRepository;
    this.inventoryRepository = inventoryRepository;
    this.userRepository = userRepository;
    this.loanRepository = loanRepository;
    this.authorRepository = authorRepository;
  }

  /**
   * Helper method to create and save a book with associated inventory.
   * @param authorId The ID of the author of the book.
   * @param title The title of the book.
   * @param inventoryCount The count of books in inventory.
   * @return The created Book entity.
   */
  private Book createAndSaveBook(Long authorId, String title, int inventoryCount) {
    Book book = new Book(authorId, title);
    bookRepository.save(book);

    Inventory inventory = new Inventory(book.getId(), inventoryCount);
    inventoryRepository.save(inventory);

    return book;
  }

  /**
   * Helper method to create and save a user.
   * @param name The name of the user.
   * @param address The address of the user.
   * @param email The email of the user.
   * @return The created User entity.
   */
  private User createAndSaveUser(String name, String address, String email) {
    User user = new User(name, address, email);
    userRepository.save(user);
    return user;
  }

  /**
   * Helper method to create and save an author.
   * @param name The name of the author.
   * @param country The country of the author.
   * @return The created Author entity.
   */
  private Author createAndSaveAuthor(String name, String country) {
    Author author = new Author(name, country);
    authorRepository.save(author);
    return author;
  }

  /**
  * Helper method to create and save a loan.
  * @param bookId The ID of the book for the loan.
  * @param userId The ID of the user for the loan.
  * @param status The status of the loan.
  * @param loanDate The date of the loan.
  * @return The created Loan entity.
  */
  private Loan createAndSaveLoan(Long bookId, Long userId, Status status, LocalDate loanDate) {
    Loan loan = new Loan(bookId, userId, status, loanDate);
    loanRepository.save(loan);
    return loan;
  }

  /**
   * This method is executed when the application starts and populates the database with initial data.
   * It creates and saves Book, Inventory, User, Author, and Loan entries.
   * It also associates Inventory entries with Books and Loan entries with Books and Users.
   * @param args Command-line arguments (not used in this method).
   */
  @Override
  public void run(String... args) {
    Author author1 = createAndSaveAuthor("Selma Lagerlöf", "Sweden");
    Author author2 = createAndSaveAuthor("Edgar Allan Poe", "United Kingdom");
    Author author3 = createAndSaveAuthor("Albert Camus", "France");

    Book book1 = createAndSaveBook(author2.getId(), "The Narrative of Arthur Gordon Pym of Nantucket", 4);
    Book book2 = createAndSaveBook(author1.getId(), "Holgerssons underbara resa genom Sverige", 4);
    Book book3 = createAndSaveBook(author3.getId(), "L'étranger", 4);

    User user1 = createAndSaveUser("Sofia B", "123 Main St.", "sofia@example.com");
    User user2 = createAndSaveUser("Freja L", "456 Main St.", "freja@example.com");

    createAndSaveLoan(book1.getId(), user1.getId(), Status.NEW_LOAN, LocalDate.parse("2023-10-30"));
    createAndSaveLoan(book2.getId(), user2.getId(), Status.NEW_LOAN, LocalDate.parse("2023-10-05"));
    createAndSaveLoan(book1.getId(), user2.getId(), Status.RENEWAL, LocalDate.now());
    createAndSaveLoan(book3.getId(), user2.getId(), Status.NEW_LOAN, LocalDate.parse("2023-11-07"));
  }
}
