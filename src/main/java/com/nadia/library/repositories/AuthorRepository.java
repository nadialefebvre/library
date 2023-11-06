package com.nadia.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nadia.library.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
  Author findByName(String name);
}