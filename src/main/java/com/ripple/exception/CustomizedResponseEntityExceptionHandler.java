package com.ripple.exception;

import com.ripple.model.ApplicationCode;
import com.ripple.model.ApplicationResponse;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EmailNotUniqueException.class)
  public final ResponseEntity<ApplicationResponse> handleEmailNotUniqueException(EmailNotUniqueException ex, WebRequest request) {

    ApplicationResponse rep = new ApplicationResponse(ApplicationCode.INVALID_EMAIL, ex.getMessage());
    return new ResponseEntity<>(rep, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MobileNoNotUniqueException.class)
  public final ResponseEntity<ApplicationResponse> handleMobilenoNotUniqueException(MobileNoNotUniqueException ex, WebRequest request) {

    ApplicationResponse rep = new ApplicationResponse(ApplicationCode.INVALID_MOBILE, ex.getMessage());
    return new ResponseEntity<>(rep, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(VmNotFoundException.class)
  public final ResponseEntity<ApplicationResponse> handleVmNotFoundException(VmNotFoundException ex, WebRequest request) {

    ApplicationResponse rep = new ApplicationResponse(ApplicationCode.VM_NOT_FOUND, ex.getMessage());
    return new ResponseEntity<>(rep, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public final ResponseEntity<ApplicationResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

    ApplicationResponse rep = new ApplicationResponse(ApplicationCode.USER_NOT_FOUND, ex.getMessage());
    return new ResponseEntity<>(rep, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SignatureException.class)
  public final ResponseEntity<ApplicationResponse> handleUserTokenTemperedException(SignatureException ex, WebRequest request) {

    ApplicationResponse rep = new ApplicationResponse(ApplicationCode.USER_TOKEN_TEMPERED, ex.getMessage());
    return new ResponseEntity<>(rep, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InvalidUserTokenException.class)
  public final ResponseEntity<ApplicationResponse> handleInvalidUserTokenException(InvalidUserTokenException ex, WebRequest request) {

    ApplicationResponse rep = new ApplicationResponse(ApplicationCode.INVALID_USER_TOKEN, ex.getMessage());
    return new ResponseEntity<>(rep, HttpStatus.UNAUTHORIZED);
  }

}