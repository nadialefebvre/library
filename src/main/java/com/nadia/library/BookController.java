package com.nadia.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
  @Autowired
  private BookRepository bookRepository;
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
    Book savedBook = bookRepository.save(book);
    return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
    Book currentBook = bookRepository.findById(id).orElse(null);
    if (currentBook == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (book.getAuthor() != null) {
      currentBook.setAuthor(book.getAuthor());
    }
    if (book.getTitle() != null) {
      currentBook.setTitle(book.getTitle());
    }
    if (book.getIsBorrowed() != null) {
      currentBook.setIsBorrowed(book.getIsBorrowed());
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