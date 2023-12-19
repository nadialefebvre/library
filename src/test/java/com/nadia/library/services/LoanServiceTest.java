package com.nadia.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nadia.library.models.Loan;
import com.nadia.library.models.Loan.Status;
import com.nadia.library.repositories.BookRepository;
import com.nadia.library.repositories.InventoryRepository;
import com.nadia.library.repositories.LoanRepository;
import com.nadia.library.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for LoanService class.
 */
public class LoanServiceTest {
  @Mock
  private LoanRepository loanRepository;

  @Mock
  private InventoryRepository inventoryRepository;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private LoanService loanService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Existing mocked loans data for testing.
   */
  private final long EXISTING_LOAN_ID = 1L;
  private final long EXISTING_BOOK_ID = 1L;
  private final long EXISTING_USER_ID = 1L;

  private Loan createMockLoan() {
    Loan loan = new Loan();
    loan.setId(EXISTING_LOAN_ID);
    loan.setBookId(EXISTING_BOOK_ID);
    loan.setUserId(EXISTING_USER_ID);
    loan.setStatus(Status.NEW_LOAN);
    loan.setLoanDate(LocalDate.now().minusDays(5));
    return loan;
  }

  private Loan createMockLateLoan() {
    Loan loan = new Loan();
    loan.setId(EXISTING_LOAN_ID);
    loan.setBookId(EXISTING_BOOK_ID);
    loan.setUserId(EXISTING_USER_ID);
    loan.setStatus(Status.NEW_LOAN);
    loan.setLoanDate(LocalDate.parse("2022-12-31"));
    return loan;
  }

  /**
   * Test to verify the functionality of retrieving all loans from the repository.
   *
   * This test checks if the service retrieves all loans from the repository.
   * It ensures that the retrieved list is not null and matches the expected list of loans.
   */
  @Test
  void testGetAllLoans() {
    List<Loan> loans = new ArrayList<>();
    when(loanRepository.findAll()).thenReturn(loans);

    List<Loan> result = loanService.getAllLoans();

    assertNotNull(result);
    assertEquals(loans, result);
  }

  /**
   * Test to verify the functionality of retrieving all late loans from the repository.
   *
   * This test checks if the service retrieves only and all late loans from the repository.
   * It ensures that the retrieved list is not null and its size matches the expected size.
   */
  @Test
  void testGetAllLateLoans() {
    List<Loan> loans = new ArrayList<>();
    Loan lateLoan = createMockLateLoan();
    loans.add(lateLoan);
    when(loanRepository.findAll()).thenReturn(loans);

    List<Loan> result = loanService.getAllLateLoans();

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  /**
   * Test to retrieve a loan by ID when the loan exists in the repository.
   *
   * This test validates the retrieval of a loan by a given ID from the repository.
   * It ensures that an OK status response with the loan information is returned.
   */
  @Test
  void testGetLoanByIdWhenLoanExists() {
    Loan existingLoan = createMockLoan();
    when(loanRepository.findById(EXISTING_LOAN_ID)).thenReturn(Optional.of(existingLoan));

    ResponseEntity<Loan> result = loanService.getLoanById((EXISTING_LOAN_ID));

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(existingLoan, result.getBody());
  }

  /**
   * Test to retrieve a loan by ID when the loan does not exist in the repository.
   *
   * This test validates the behavior when attempting to retrieve a loan by a non-existent ID.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testGetLoanByIdWhenLoanDoesNotExist() {
    when(loanRepository.findById(EXISTING_LOAN_ID)).thenReturn(Optional.empty());

    ResponseEntity<Loan> result = loanService.getLoanById((EXISTING_LOAN_ID));

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  /**
   * Test to create a loan when the book and the user exist, and the book is available for loan in the inventory.
   *
   * This test checks the addition of a new loan when the book exists, the user exists and the book can be loaned.
   * It ensures that the loan is successfully added and a CREATED status response is returned.
   */
  @Test
  void testCreateLoan() {
    Loan newLoan = createMockLoan();
    when(bookRepository.existsById(EXISTING_BOOK_ID)).thenReturn(true);
    when(userRepository.existsById(EXISTING_USER_ID)).thenReturn(true);
    when(inventoryRepository.inventoryInStockValue(1L)).thenReturn(1);
    when(loanRepository.save(newLoan)).thenReturn(newLoan);

    ResponseEntity<Loan> result = loanService.createLoan(newLoan);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals(newLoan, result.getBody());
  }

  /**
   * Test to create a loan when the book does not exist.
   *
   * This test validates the behavior when attempting to create a loan where the book does not exist.
   * It ensures that a BAD_REQUEST status response is returned as expected.
   */
  @Test
  void testCreateLoanWhenBookDoesNotExist() {
    Loan newLoan = createMockLoan();
    when(bookRepository.existsById(EXISTING_BOOK_ID)).thenReturn(false);
    when(userRepository.existsById(EXISTING_USER_ID)).thenReturn(true);

    ResponseEntity<Loan> result = loanService.createLoan(newLoan);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  /**
   * Test to create a loan when the user does not exist.
   *
   * This test validates the behavior when attempting to create a loan where the user does not exist.
   * It ensures that a BAD_REQUEST status response is returned as expected.
   */
  @Test
  void testCreateLoanWhenUserDoesNotExist() {
    Loan newLoan = createMockLoan();
    when(bookRepository.existsById(EXISTING_BOOK_ID)).thenReturn(true);
    when(userRepository.existsById(EXISTING_USER_ID)).thenReturn(false);

    ResponseEntity<Loan> result = loanService.createLoan(newLoan);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  /**
   * Test to create a loan when the book is not available for loan.
   *
   * This test validates the behavior when attempting to create a loan where the book can not be loaned.
   * It ensures that a FORBIDDEN status response is returned as expected.
   */
  @Test
  void testCreateLoanWhenBookIsNotAvailableForLoan() {
    Loan newLoan = createMockLoan();
    when(bookRepository.existsById(EXISTING_BOOK_ID)).thenReturn(true);
    when(userRepository.existsById(EXISTING_USER_ID)).thenReturn(true);
    when(inventoryRepository.inventoryInStockValue(1L)).thenReturn(0);

    ResponseEntity<Loan> result = loanService.createLoan(newLoan);

    assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
  }

  /**
   * Test to renew a loan when the loan exists in the repository.
   *
   * This test checks the update of an existing loan.
   * It ensures that the loan is successfully updated and an OK status response is returned.
   */
  @Test
  void testRenewLoan() {
    Loan existingLoan = createMockLoan();
    when(loanRepository.findById(EXISTING_LOAN_ID)).thenReturn(Optional.of(existingLoan));

    Loan updatedLoan = new Loan();
    updatedLoan.setStatus(Status.RENEWAL);
    updatedLoan.setLoanDate(LocalDate.now());

    when(loanRepository.save(existingLoan)).thenReturn(updatedLoan);

    ResponseEntity<Loan> result = loanService.renewLoan(EXISTING_LOAN_ID);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(updatedLoan, result.getBody());
    assertEquals(Status.RENEWAL, existingLoan.getStatus());
    assertEquals(LocalDate.now(), existingLoan.getLoanDate());
  }

  /**
   * Test to renew a loan when the loan does not exist in the repository.
   *
   * This test verifies the behavior when attempting to update a non-existent loan.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testRenewLoanWhenLoanDoesNotExist() {
    when(loanRepository.findById(EXISTING_LOAN_ID)).thenReturn(Optional.empty());

    Loan updatedLoan = new Loan();
    updatedLoan.setStatus(Status.RENEWAL);
    updatedLoan.setLoanDate(LocalDate.now());

    ResponseEntity<Loan> result = loanService.renewLoan(EXISTING_LOAN_ID);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  /**
   * Test to renew a loan when the loan is not available for loan.
   *
   * This test verifies the behavior when attempting to update a non-renewable loan.
   * It ensures that a FORBIDDEN status response is returned as expected.
   */
  @Test
  void testRenewLoanWhenLoanIsNotRenewable() {
    Loan existingLateLoan = createMockLateLoan();
    when(loanRepository.findById(EXISTING_LOAN_ID)).thenReturn(Optional.of(existingLateLoan));

    ResponseEntity<Loan> result = loanService.renewLoan(EXISTING_LOAN_ID);

    assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
  }

  /**
   * Test to delete an existing loan from the repository when the loan exists.
   *
   * This test validates the deletion of an existing loan from the repository.
   * It ensures that the loan is successfully deleted and a NO_CONTENT status response is returned.
   */
  @Test
  void testDeleteLoan() {
    Loan loanToDelete = createMockLoan();
    when(loanRepository.findById(EXISTING_LOAN_ID)).thenReturn(Optional.of(loanToDelete));

    ResponseEntity<HttpStatus> result = loanService.deleteLoan(EXISTING_LOAN_ID);

    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    verify(loanRepository, times(1)).delete(loanToDelete);
  }

  /**
   * Test to delete a loan from the repository when the loan does not exist.
   *
   * This test verifies the behavior when attempting to delete a loan that does not exist.
   * It ensures that a NOT_FOUND status response is returned as expected.
   */
  @Test
  void testDeleteLoanWhenLoanDoesNotExist() {
    when(loanRepository.findById(EXISTING_LOAN_ID)).thenReturn(Optional.empty());

    ResponseEntity<HttpStatus> result = loanService.deleteLoan(EXISTING_LOAN_ID);

    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    verify(loanRepository, never()).delete(any());
  }
}
