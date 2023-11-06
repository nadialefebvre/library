package com.nadia.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nadia.library.models.Book;
import com.nadia.library.services.BookService;

import java.util.List;

// marks the class as a Spring MVC controller, which is used for processing HTTP requests
@RestController
// specifies that this controller will handle requests mapped to the `/books` endpoint
// all the methods in this controller will be relative to this base path
@RequestMapping("/books")
public class BookController {
  // used to inject the `BookRepository` into the `BookController` class
  @Autowired
  private BookService bookService;

  //controller methods handling HTTP GET/POST/PATCH/DELETE requests
  @GetMapping("")
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
    return bookService.getBookById(id);
  }

  @PostMapping("")
  public ResponseEntity<Book> addBook(@RequestBody Book book) {
    return bookService.addBook(book);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
    return bookService.updateBook(id, book);
  }

  // deletes all copies of one book (but only if no copy is currently loaned)
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteAllBookCopies(@PathVariable("id") Long id) {
    return bookService.deleteAllBookCopies(id);
  }
}
