package com.nadia.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// marks the class as a Spring MVC controller, which is used for processing HTTP requests
@RestController
// specifies that this controller will handle requests mapped to the `/books` endpoint
// all the methods in this controller will be relative to this base path
@RequestMapping("/books")
public class BookController {
  // used to inject the `BookRepository` into the `BookController` class
  @Autowired
  private BookRepository bookRepository;

  //controller methods handling HTTP GET/POST/PATCH/DELETE requests
  @GetMapping("")
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
    Book book = bookRepository.findById(id).orElse(null);

    if (book == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    // set a due date 21 days later
    if (book.getIsBorrowed() == true) {
      book.setDueDate(LocalDate.now().plusDays(21));
    }

    Book savedBook = bookRepository.save(book);

    return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
    Book currentBook = bookRepository.findById(id).orElse(null);

    if (currentBook == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // ensure that each field is changed only if new value is sent
    if (book.getAuthor() != null) {
      currentBook.setAuthor(book.getAuthor());
    }
    if (book.getTitle() != null) {
      currentBook.setTitle(book.getTitle());
    }
    if (book.getIsBorrowed() != null) {
      currentBook.setIsBorrowed(book.getIsBorrowed());
      if (book.getIsBorrowed() == true) {
        currentBook.setDueDate(LocalDate.now().plusDays(21));
      } else {
        currentBook.setDueDate(null);
      }
    }

    Book updatedBook = bookRepository.save(currentBook);

    return new ResponseEntity<>(updatedBook, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") Long id) {
    Book book = bookRepository.findById(id).orElse(null);

    if (book == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    bookRepository.delete(book);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
