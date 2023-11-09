package com.nadia.library.services;

import com.nadia.library.models.Loan;
import com.nadia.library.models.Loan.Status;
import com.nadia.library.repositories.InventoryRepository;
import com.nadia.library.repositories.LoanRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Loan entities.
 */
@Service
public class LoanService {
  @Autowired
  private LoanRepository loanRepository;
  @Autowired
  private InventoryRepository inventoryRepository;

  /**
   * Get a list of all loans.
   *
   * @return A list of Loan entities.
   */
  public List<Loan> getAllLoans() {
    List<Loan> loans = loanRepository.findAll();
    return loans;
  }

  /**
   * Get a list of all late loans.
   *
   * @return A list of late Loan entities.
   */
  public List<Loan> getAllLateLoans() {
    List<Loan> loans = loanRepository.findAll();

    List<Loan> lateLoans = loans.stream()
      .filter(loan -> loanRepository.isLate(loan))
      .collect(Collectors.toList());

    return lateLoans;
  }


  /**
   * Get a loan by its ID.
   *
   * @param id The ID of the loan to retrieve.
   * @return A ResponseEntity containing the Loan entity if found.
   */
  public ResponseEntity<Loan> getLoanById(Long id) {
    Loan loan = findLoanById(id);

    if (loan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(loan, HttpStatus.OK);
  }

  /**
   * Create a new loan.
   *
   * @param loan The Loan entity to create.
   * @return A ResponseEntity containing the created Loan entity.
   */
  public ResponseEntity<Loan> createLoan(Loan loan) {
    if (!isBookAvailableForLoan(loan.getBookId())) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    inventoryRepository.decrementInventory(loan.getBookId());
    Loan savedLoan = loanRepository.save(loan);
    return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
  }

  /**
   * Renew an existing loan.
   *
   * @param id The ID of the loan to renew.
   * @return A ResponseEntity containing the renewed Loan entity.
   */
  public ResponseEntity<Loan> renewLoan(Long id) {
    Loan currentLoan = findLoanById(id);

    if (currentLoan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    if (!isLoanRenewable(currentLoan)) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    currentLoan.setStatus(Status.RENEWAL);
    currentLoan.setLoanDate(LocalDate.now());
    Loan updatedLoan = loanRepository.save(currentLoan);
    return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
  }

  /**
   * Delete a loan by its ID.
   *
   * @param id The ID of the loan to delete.
   * @return A ResponseEntity with HTTP status indicating the result of the delete operation.
   */
  public ResponseEntity<HttpStatus> deleteLoan(Long id) {
    Loan loan = findLoanById(id);

    if (loan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    inventoryRepository.incrementInventory(loan.getBookId());
    loanRepository.delete(loan);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Helper method to find a loan by its ID.
   *
   * @param id The ID of the loan to find.
   * @return The found Loan entity, or null if not found.
   */
  private Loan findLoanById(Long id) {
    return loanRepository.findById(id).orElse(null);
  }

  /**
   * Check if a book is available for loan.
   *
   * @param bookId The ID of the book to check.
   * @return True if the book is available, false otherwise.
   */
  private boolean isBookAvailableForLoan(Long bookId) {
    return inventoryRepository.inventoryInStockValue(bookId) > 0;
  }

  /**
   * Check if a loan is renewable.
   *
   * @param loan The Loan entity to check.
   * @return True if the loan is renewable, false otherwise.
   */
  private boolean isLoanRenewable(Loan loan) {
    return loan.getStatus() == Status.NEW_LOAN && !loanRepository.isLate(loan);
  }
}
