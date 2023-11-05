package com.nadia.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nadia.library.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
