package com.nadia.library.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a Person entity.
 *
 * An entity class representing a person in the database.
 */
@Entity
public class Person {
  /**
   * The unique identifier for the person.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The name of the person.
   */
  @NotBlank(message = "`name` is a mandatory field: must not be null and must contain at least one non-whitespace character")
  private String name;

  /**
   * Default constructor for the Person class.
   */
  public Person() {}

  /**
   * Constructor to create a Person with a name.
   *
   * @param name The name of the person.
   */
  public Person(String name) {
    this.name = name;
  }

  /**
   * Get the unique identifier of the person.
   *
   * @return The unique identifier of the person.
   */
  public Long getId() {
    return id;
  }

  /**
   * Set the unique identifier of the person.
   *
   * @param id The unique identifier of the person.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get the name of the person.
   *
   * @return The name of the person.
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of the person.
   *
   * @param name The name of the person.
   */
  public void setName(String name) {
    this.name = name;
  }
}
