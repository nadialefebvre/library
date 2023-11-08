package com.nadia.library.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

/**
 * Represents an Author entity.
 *
 * An entity class that extends the Person class representing an author in the database.
 */
@Entity
public class Author extends Person {
  /**
   * The country of the author.
   */
  @NotNull(message = "`country` is a mandatory field")
  private String country;

  /**
   * Default constructor for the Author class.
   */
  public Author() {}

  /**
   * Constructor to create an Author with a name and country.
   *
   * @param name    The name of the author.
   * @param country The country of the author.
   */
  public Author(String name, String country) {
    super(name);
    this.country = country;
  }

  /**
   * Get the country of the author.
   *
   * @return The country of the author.
   */
  public String getCountry() {
    return country;
  }


  /**
   * Set the country of the author.
   *
   * @param country The country of the author.
   */
  public void setCountry(String country) {
    this.country = country;
  }
}
