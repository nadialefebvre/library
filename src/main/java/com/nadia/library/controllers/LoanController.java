package com.nadia.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nadia.library.models.Loan;
import com.nadia.library.models.Loan.Status;
import com.nadia.library.repositories.InventoryRepository;
import com.nadia.library.repositories.LoanRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans")
public class LoanController {
  @Autowired
  private LoanRepository loanRepository;
  @Autowired
  private InventoryRepository inventoryRepository;

  @GetMapping("")
  public List<Loan> getAllLoans() {
    List<Loan> loans = loanRepository.findAll();
    return loans;
  }

  @GetMapping("/late")
  public List<Loan> getAllLateLoans() {
    List<Loan> loans = loanRepository.findAll();
    // filter the list to include only late loans
    List<Loan> lateLoans = loans.stream()
      .filter(loan -> loanRepository.isLate(loan))
      .collect(Collectors.toList());

    return lateLoans;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Loan> getLoanById(@PathVariable("id") Long id) {
    Loan loan = loanRepository.findById(id).orElse(null);

    if (loan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(loan, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
    boolean isACompleteRequest = loan.getUserId() != null && loan.getBookId() != null;
    boolean isAvailableForALoan = inventoryRepository.inventoryInStockValue(loan.getBookId()) > 0;
    if (!isACompleteRequest) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else if (!isAvailableForALoan) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } else {
      inventoryRepository.decrementInventory(loan.getBookId());
      loan.setStatus(Status.NEW_LOAN);
      loan.setBorrowingDate(LocalDate.now());

      Loan savedLoan = loanRepository.save(loan);

      return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
    }
  }

  // renewal: extends loan length if isRenewable
  @PatchMapping("/{id}")
  public ResponseEntity<Loan> renewLoan(@PathVariable("id") Long id) {
    Loan currentLoan = loanRepository.findById(id).orElse(null);

    if (currentLoan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    boolean isRenewable = currentLoan.getStatus() == Status.NEW_LOAN && !loanRepository.isLate(currentLoan);
    if (!isRenewable) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } else {
      currentLoan.setStatus(Status.RENEWAL);
      currentLoan.setBorrowingDate(LocalDate.now());
      Loan updatedLoan = loanRepository.save(currentLoan);
      return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
    }
  }

  // deletes the loan when the user brings back its copy
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteLoan(@PathVariable("id") Long id) {
    Loan loan = loanRepository.findById(id).orElse(null);

    if (loan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    inventoryRepository.incrementInventory(loan.getBookId());
    loanRepository.delete(loan);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
