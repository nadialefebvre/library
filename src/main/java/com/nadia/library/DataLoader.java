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
   * This method is executed when the application starts and populates the database with initial data.
   * It creates and saves Book, Inventory, User, Author, and Loan entries.
   * It also associates Inventory entries with Books and Loan entries with Books and Users.
   * @param args Command-line arguments (not used in this method).
   */
  @Override
  public void run(String... args) {
    Book book1 = new Book("Edgar All Poe", "The Narrative of Arthur Gordon Pym of Nantucket");
    Book book2 = new Book("Selma Lagerlöf", "Holgerssons underbara resa genom Sverige");
    Book book3 = new Book("Albert Camus", "L'étranger");

    bookRepository.save(book1);
    bookRepository.save(book2);
    bookRepository.save(book3);

    Inventory inventory1 = new Inventory(book1.getId(), 4);
    Inventory inventory2 = new Inventory(book2.getId(), 4);
    Inventory inventory3 = new Inventory(book3.getId(), 4);

    inventoryRepository.save(inventory1);
    inventoryRepository.save(inventory2);
    inventoryRepository.save(inventory3);

    User user1 = new User("Sofia B", "123 Main St.", "sofia@example.com");
    User user2 = new User("Freja L", "456 Main St.", "freja@example.com");

    userRepository.save(user1);
    userRepository.save(user2);

    Author author1 = new Author("Selma Lagerlöf", "Sweden");
    Author author2 = new Author("Edgar Allan Poe", "United Kingdom");
    Author author3 = new Author("Albert Camus", "France");

    authorRepository.save(author1);
    authorRepository.save(author2);
    authorRepository.save(author3);

    Loan loan1 = new Loan(book1.getId(), user1.getId(), Status.NEW_LOAN, LocalDate.parse("2023-10-30"));
    Loan loan2 = new Loan(book2.getId(), user2.getId(), Status.NEW_LOAN, LocalDate.parse("2023-10-05"));
    Loan loan3 = new Loan(book1.getId(), user2.getId(), Status.RENEWAL, LocalDate.now());

    loanRepository.save(loan1);
    loanRepository.save(loan2);
    loanRepository.save(loan3);
  }
}
