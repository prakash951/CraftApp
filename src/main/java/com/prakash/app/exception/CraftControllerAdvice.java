package com.prakash.app.exception;

import com.prakash.app.dto.response.ErrorMessage;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CraftControllerAdvice {

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ErrorMessage> handleInValidBindException(BindException exception) {
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setCode(HttpStatus.BAD_REQUEST.value() + "");
    errorMessage.setMessage(
        exception.getFieldErrors().stream()
            .map(e -> String.join(": ", e.getField(), e.getDefaultMessage()))
            .collect(Collectors.joining(", ")));
    return ResponseEntity.badRequest().body(errorMessage);
  }
}
