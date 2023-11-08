package com.nadia.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LibraryApplication is the main class of the library management application.
 * It uses Spring Boot to start the application.
 */
@SpringBootApplication
public class LibraryApplication {
	/**
	 * The main method is the entry point of the application.
	 *
	 * @param args Command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}
}
