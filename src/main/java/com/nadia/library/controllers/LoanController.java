package com.nadia.library.controllers;

import com.nadia.library.models.Loan;
import com.nadia.library.services.LoanService;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling Loan-related operations.
 *
 * A controller class responsible for managing Loan entities.
 */
@RestController
@RequestMapping("/loans")
public class LoanController {
  @Autowired
  private LoanService loanService;

  /**
   * Get a list of all loans.
   *
   * @return A list of Loan entities.
   */
  @GetMapping("")
  public List<Loan> getAllLoans() {
    return loanService.getAllLoans();
  }


  /**
   * Get a list of all late loans.
   *
   * @return A list of late Loan entities.
   */
  @GetMapping("/late")
  public List<Loan> getAllLateLoans() {
    return loanService.getAllLateLoans();
  }

  /**
   * Get a loan by its ID.
   *
   * @param id The ID of the loan to retrieve.
   * @return A ResponseEntity containing the Loan entity if found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Loan> getLoanById(@PathVariable("id") Long id) {
    return loanService.getLoanById(id);
  }

  /**
   * Create a new Loan.
   *
   * @param loan The Loan entity to create.
   * @return A ResponseEntity containing the created Loan entity.
   */
  @PostMapping("")
  public ResponseEntity<Loan> createLoan(@Valid @RequestBody Loan loan) {
    return loanService.createLoan(loan);
  }

  /**
   * Renew an existing loan by its ID (if renewable).
   *
   * @param id The ID of the loan to renew.
   * @return A ResponseEntity containing the renewed Loan entity.
   */
  @PatchMapping("/{id}")
  public ResponseEntity<Loan> renewLoan(@PathVariable("id") Long id) {
    return loanService.renewLoan(id);
  }

  /**
   * Delete a loan when the user returns the copy.
   *
   * @param id The ID of the loan to delete.
   * @return A ResponseEntity with HTTP status indicating the result of the delete operation.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteLoan(@PathVariable("id") Long id) {
    return loanService.deleteLoan(id);
  }
}
