package com.nadia.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nadia.library.models.Author;
import com.nadia.library.repositories.AuthorRepository;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {
        Author author = authorRepository.findById(id).orElse(null);

        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author savedAuthor = authorRepository.save(author);

        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") Long id, @RequestBody Author author) {
        Author currentAuthor = authorRepository.findById(id).orElse(null);

        if (currentAuthor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (author.getName() != null) {
            currentAuthor.setName(author.getName());
        }

        Author updatedAuthor = authorRepository.save(currentAuthor);

        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") Long id) {
        Author author = authorRepository.findById(id).orElse(null);

        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorRepository.delete(author);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
