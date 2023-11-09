package com.nadia.library.repositories;

import com.nadia.library.models.Loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for handling Loan entities.
 *
 * A repository interface for managing Loan entities in the database.
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
  /**
   * Find a loan by the book ID.
   *
   * @param bookId The ID of the book associated with the loan to search for.
   * @return The Loan entity with the specified book ID, if found.
   */
  Loan findByBookId(Long bookId);

  /**
   * Find a loan by the user ID.
   *
   * @param userId The ID of the user associated with the loan to search for.
   * @return The Loan entity with the specified user ID, if found.
   */
  Loan findByUserId(Long userId);

  /**
   * Check if a loan exists for a given book ID.
   *
   * @param bookId The ID of the book to check for loan existence.
   * @return true if a loan exists for the book, false otherwise.
   */
  boolean existsByBookId(Long bookId);

  /**
   * Check if a loan is late based on the loan date and current date.
   *
   * @param loan The Loan entity to check for lateness.
   * @return true if the loan is late, false otherwise.
   */
  // TODO: maybe add a parameter to allow passing a loan length instead of setting it here?
  public default boolean isLate(Loan loan) {
    LocalDate currentDate = LocalDate.now();
    LocalDate loanDate = loan.getLoanDate();
    long daysDifference = ChronoUnit.DAYS.between(loanDate, currentDate);
    if (daysDifference > 21) {
      return true;
    } else {
      return false;
    }
  }
}
