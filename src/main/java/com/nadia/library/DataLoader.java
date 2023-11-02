package com.nadia.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// provides a method (`run`) that will be executed when the app starts
@Component
public class DataLoader implements CommandLineRunner {
  private final BookRepository bookRepository;

  public DataLoader(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public void run(String... args) {
    // create some `Book` objects
    Book book1 = new Book("Edgar All Poe", "The Narrative of Arthur Gordon Pym of Nantucket", false, null);
    Book book2 = new Book("Selma Lagerlöf", "Holgerssons underbara resa genom Sverige", false, null);
    Book book3 = new Book("Albert Camus", "L'étranger", false, null);

    // save these objects to the database
    bookRepository.save(book1);
    bookRepository.save(book2);
    bookRepository.save(book3);
  }
}
