package com.nadia.library.controllers;

import com.nadia.library.models.Author;
import com.nadia.library.services.AuthorService;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {
  @Autowired
  private AuthorService authorService;

  @GetMapping("")
  public List<Author> getAllAuthors() {
    return authorService.getAllAuthors();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {
    return authorService.getAuthorById(id);
  }

  @PostMapping("")
  public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
    return authorService.createAuthor(author);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Author> updateAuthor(@PathVariable("id") Long id, @Valid @RequestBody Author author) {
    return authorService.updateAuthor(id, author);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") Long id) {
    return authorService.deleteAuthor(id);
  }
}
