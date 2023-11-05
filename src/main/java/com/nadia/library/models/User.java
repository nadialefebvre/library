package com.nadia.library.models;

import jakarta.persistence.Entity;

@Entity
public class User extends Person {
  private String address; // New field for address

  public User() {}

  public User(String name, String address) {
    super(name);
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
