package com.nadia.library.repositories;
import com.nadia.library.models.Loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
  Loan findByBookId(Long bookId);
  Loan findByUserId(Long userId);
  boolean existsByBookId(Long bookId);

  // maybe add a parameter to allow passing a loan length instead of setting it here?
  public default boolean isLate(Loan loan) {
    LocalDate currentDate = LocalDate.now();
    LocalDate borrowingDate = loan.getBorrowingDate();
    long daysDifference = ChronoUnit.DAYS.between(borrowingDate, currentDate);
    if (daysDifference > 21) {
      return true;
    } else {
      return false;
    }
  }
}
