package com.prakash.app.exception;

import com.prakash.app.dto.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CraftControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorMessage> handleException(Exception exception) {
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + "");
    errorMessage.setMessage(exception.getMessage());
    return ResponseEntity.internalServerError().body(errorMessage);
  }
}
