package com.nadia.library.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nadia.library.models.Loan;
import com.nadia.library.models.Loan.Status;
import com.nadia.library.repositories.InventoryRepository;
import com.nadia.library.repositories.LoanRepository;

@Service
public class LoanService {
  @Autowired
  private LoanRepository loanRepository;
  @Autowired
  private InventoryRepository inventoryRepository;

  public List<Loan> getAllLoans() {
    List<Loan> loans = loanRepository.findAll();
    return loans;
  }

  public List<Loan> getAllLateLoans() {
    List<Loan> loans = loanRepository.findAll();
    // filter the list to include only late loans
    List<Loan> lateLoans = loans.stream()
      .filter(loan -> loanRepository.isLate(loan))
      .collect(Collectors.toList());

    return lateLoans;
  }

  public ResponseEntity<Loan> getLoanById(Long id) {
    Loan loan = loanRepository.findById(id).orElse(null);

    if (loan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(loan, HttpStatus.OK);
  }

  public ResponseEntity<Loan> createLoan(Loan loan) {
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

  public ResponseEntity<Loan> renewLoan(Long id) {
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

  public ResponseEntity<HttpStatus> deleteLoan(Long id) {
    Loan loan = loanRepository.findById(id).orElse(null);

    if (loan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    inventoryRepository.incrementInventory(loan.getBookId());
    loanRepository.delete(loan);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
