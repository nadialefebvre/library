package com.nadia.library;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String title;
    private Boolean isBorrowed;

public Book() {}

public Book(String author, String title, Boolean isBorrowed) {
    this.author = author;
    this.title = title;
    this.isBorrowed = isBorrowed;
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getAuthor() {
    return author;
}

public void setAuthor(String author) {
    this.author = author;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public Boolean getIsBorrowed() {
    return isBorrowed;
}

public void setIsBorrowed(Boolean isBorrowed) {
    this.isBorrowed = isBorrowed;
}

}