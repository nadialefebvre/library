package com.nadia.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {
	// `main` is the entry point of the application
	public static void main(String[] args) {
		// this line is responsible for starting the Spring Boot application
		SpringApplication.run(LibraryApplication.class, args);
	}
}
