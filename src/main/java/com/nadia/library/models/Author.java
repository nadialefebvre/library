package com.nadia.library.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

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
  @NotBlank(message = "`country` is a mandatory field: must not be null and must contain at least one non-whitespace character")
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
