package com.nadia.library.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a User entity.
 *
 * An entity class that extends the Person class representing a user in the database.
 */
@Entity
public class User extends Person {
  /**
   * The address of the user.
   */
  @NotNull(message = "`address` is a mandatory field")
  private String address;

  /**
   * The email address of the user, validated for a valid email format.
   */
  @Email(
    message = "`email` must have a valid email format, such as name@example.com",
    regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
  @NotNull(message = "`email` is a mandatory field")
  private String email;

  /**
   * Default constructor for the User class.
   */
  public User() {}

  /**
   * Constructor to create a User with a name, address, and email.
   *
   * @param name    The name of the user.
   * @param address The address of the user.
   * @param email   The email address of the user.
   */
  public User(String name, String address, String email) {
    super(name);
    this.address = address;
    this.email = email;
  }

  /**
   * Get the address of the user.
   *
   * @return The address of the user.
   */
  public String getAddress() {
    return address;
  }

  /**
   * Set the address of the user.
   *
   * @param address The address of the user.
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Get the email address of the user.
   *
   * @return The email address of the user.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set the email address of the user.
   *
   * @param email The email address of the user.
   */
  public void setEmail(String email) {
    this.email = email;
  }

}
