package com.nadia.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nadia.library.models.Author;
import com.nadia.library.repositories.AuthorRepository;

@Service
public class AuthorService {
  @Autowired
  private AuthorRepository authorRepository;

  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  public ResponseEntity<Author> getAuthorById(Long id) {
    Author author = authorRepository.findById(id).orElse(null);

    if (author == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(author, HttpStatus.OK);
  }

  public ResponseEntity<Author> createAuthor(Author author) {
    Author savedAuthor = authorRepository.save(author);

    return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
  }

  public ResponseEntity<Author> updateAuthor(Long id, Author author) {
    Author currentAuthor = authorRepository.findById(id).orElse(null);

    if (currentAuthor == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (author.getName() != null) {
      currentAuthor.setName(author.getName());
    }

    if (author.getCountry() != null) {
      currentAuthor.setCountry(author.getCountry());
    }

    Author updatedAuthor = authorRepository.save(currentAuthor);

    return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
  }

  public ResponseEntity<HttpStatus> deleteAuthor(Long id) {
    Author author = authorRepository.findById(id).orElse(null);

    if (author == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    authorRepository.delete(author);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
