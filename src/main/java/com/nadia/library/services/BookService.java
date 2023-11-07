package com.nadia.library.services;

import com.nadia.library.models.Book;
import com.nadia.library.models.Inventory;
import com.nadia.library.repositories.BookRepository;
import com.nadia.library.repositories.InventoryRepository;
import com.nadia.library.repositories.LoanRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private InventoryRepository inventoryRepository;
  @Autowired
  private LoanRepository loanRepository;

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public ResponseEntity<Book> getBookById(Long id) {
    Book book = bookRepository.findById(id).orElse(null);

    if (book == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  public ResponseEntity<Book> addBook(Book book) {
    Book bookEntry = bookRepository.findByAuthorAndTitle(book.getAuthor(), book.getTitle());

    if (bookEntry != null) {
      // automatically increment the inventory entry inStock value by 1
      inventoryRepository.incrementInventory(bookEntry.getId());
      return new ResponseEntity<>(bookEntry, HttpStatus.OK);
    } else {
      // automatically add the book to the inventory with inStock value of 1
      Book savedBook = bookRepository.save(book);
      inventoryRepository.addInventoryItem(savedBook.getId());
      return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
  }

  public ResponseEntity<Book> updateBook(Long id, Book book) {
    Book currentBook = bookRepository.findById(id).orElse(null);

    if (currentBook == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    currentBook.setAuthor(book.getAuthor());
    currentBook.setTitle(book.getTitle());
    Book updatedBook = bookRepository.save(currentBook);
    return new ResponseEntity<>(updatedBook, HttpStatus.OK);
  }

  public ResponseEntity<HttpStatus> deleteAllBookCopies(Long id) {
    Book book = bookRepository.findById(id).orElse(null);
    Inventory inventory = inventoryRepository.findByBookId(id);

    if (book == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    boolean isACopyLoaned = loanRepository.existsByBookId(id);

    if (isACopyLoaned) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } else {
      inventoryRepository.delete(inventory);
      bookRepository.delete(book);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
