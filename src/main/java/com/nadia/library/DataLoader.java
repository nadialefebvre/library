package com.nadia.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        Book book1 = new Book("Edgar All Poe", "The Narrative of Arthur Gordon Pym of Nantucket", true);
        Book book2 = new Book("Selma Lagerlöf", "Holgerssons underbara resa genom Sverige", false);
        Book book3 = new Book("Albert Camus", "L'étranger", false);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }
}
