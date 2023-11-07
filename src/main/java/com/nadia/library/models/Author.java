package com.nadia.library.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Author extends Person {
  @NotNull(message = "`country` is a mandatory field")
  private String country;

  public Author() {}

  public Author(String name, String country) {
    super(name);
    this.country = country;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
