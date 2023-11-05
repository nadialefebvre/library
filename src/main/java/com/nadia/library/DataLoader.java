package com.nadia.library;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.nadia.library.models.Book;
import com.nadia.library.models.Inventory;
import com.nadia.library.models.Loan;
import com.nadia.library.models.User;
import com.nadia.library.models.Loan.Status;
import com.nadia.library.repositories.BookRepository;
import com.nadia.library.repositories.InventoryRepository;
import com.nadia.library.repositories.LoanRepository;
import com.nadia.library.repositories.UserRepository;

// provides a method (`run`) that will be executed when the app starts
@Component
public class DataLoader implements CommandLineRunner {
  private final BookRepository bookRepository;
  private final InventoryRepository inventoryRepository;
  private final UserRepository userRepository;
  private final LoanRepository loanRepository;

  public DataLoader(
    BookRepository bookRepository,
    InventoryRepository inventoryRepository,
    UserRepository userRepository,
    LoanRepository loanRepository
    ) {
    this.bookRepository = bookRepository;
    this.inventoryRepository = inventoryRepository;
    this.userRepository = userRepository;
    this.loanRepository = loanRepository;
  }

  @Override
  public void run(String... args) {
    // create some `Book` entries
    Book book1 = new Book("Edgar All Poe", "The Narrative of Arthur Gordon Pym of Nantucket");
    Book book2 = new Book("Selma Lagerlöf", "Holgerssons underbara resa genom Sverige");
    Book book3 = new Book("Albert Camus", "L'étranger");

    // save these `Book` entries to the database
    bookRepository.save(book1);
    bookRepository.save(book2);
    bookRepository.save(book3);

    // create `Inventory` entries and associate them with books
    Inventory inventory1 = new Inventory(book1.getId(), 4);
    Inventory inventory2 = new Inventory(book2.getId(), 4);
    Inventory inventory3 = new Inventory(book3.getId(), 4);

    // save these `Inventory` entries
    inventoryRepository.save(inventory1);
    inventoryRepository.save(inventory2);
    inventoryRepository.save(inventory3);

    // create `User` entries
    User user1 = new User("Sofia B");
    User user2 = new User("Freja L");

    // save these `User` entries
    userRepository.save(user1);
    userRepository.save(user2);

    // create `Loan` entries and associate them with books and users
    Loan loan1 = new Loan(book1.getId(), user1.getId(), Status.NEW_LOAN, LocalDate.now().minusDays(8));
    Loan loan2 = new Loan(book2.getId(), user2.getId(), Status.NEW_LOAN, LocalDate.now().minusDays(30));
    Loan loan3 = new Loan(book1.getId(), user2.getId(), Status.RENEWAL, LocalDate.now());

    // save these `Loan` entries
    loanRepository.save(loan1);
    loanRepository.save(loan2);
    loanRepository.save(loan3);
  }
}
