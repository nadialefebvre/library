package com.nadia.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nadia.library.models.User;
import com.nadia.library.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public ResponseEntity<User> getUserById(Long id) {
      User user = userRepository.findById(id).orElse(null);

      if (user == null) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      return new ResponseEntity<>(user, HttpStatus.OK);
  }

  public ResponseEntity<User> createUser(User user) {
    User savedUser = userRepository.save(user);

    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
  }

  public ResponseEntity<User> updateUser(Long id, User user) {
    User currentUser = userRepository.findById(id).orElse(null);

    if (currentUser == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (user.getName() != null) {
        currentUser.setName(user.getName());
    }

    if (user.getAddress() != null) {
        currentUser.setAddress(user.getAddress());
    }

    User updatedUser = userRepository.save(currentUser);

    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
}

  public ResponseEntity<HttpStatus> deleteUser(Long id) {
    User user = userRepository.findById(id).orElse(null);

    if (user == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    userRepository.delete(user);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
