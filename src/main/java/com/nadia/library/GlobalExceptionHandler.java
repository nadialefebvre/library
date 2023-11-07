package com.nadia.library;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> validationErrors = new HashMap<>();

    for (FieldError fieldError : ex.getFieldErrors()) {
      validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    String errorMessage = "Validation error(s): " + validationErrors;
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }
}
