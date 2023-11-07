package com.nadia.library.controllers;

import com.nadia.library.models.Loan;
import com.nadia.library.services.LoanService;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {
  @Autowired
  private LoanService loanService;

  @GetMapping("")
  public List<Loan> getAllLoans() {
    return loanService.getAllLoans();
  }

  @GetMapping("/late")
  public List<Loan> getAllLateLoans() {
    return loanService.getAllLateLoans();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Loan> getLoanById(@PathVariable("id") Long id) {
    return loanService.getLoanById(id);
  }

  @PostMapping("")
  public ResponseEntity<Loan> createLoan(@Valid @RequestBody Loan loan) {
    return loanService.createLoan(loan);
  }

  // renewal: extends loan length if isRenewable
  @PatchMapping("/{id}")
  public ResponseEntity<Loan> renewLoan(@PathVariable("id") Long id) {
    return loanService.renewLoan(id);
  }

  // deletes the loan when the user brings back its copy
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteLoan(@PathVariable("id") Long id) {
    return loanService.deleteLoan(id);
  }
}
