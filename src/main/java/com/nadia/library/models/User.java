package com.nadia.library.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
public class User extends Person {
  @NotNull(message = "`address` is a mandatory field")
  private String address;

  @Email(
    message = "`email` must have a valid email format, such as name@example.com",
    regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
  @NotNull(message = "`email` is a mandatory field")
  private String email;

  public User() {}

  public User(String name, String address, String email) {
    super(name);
    this.address = address;
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
