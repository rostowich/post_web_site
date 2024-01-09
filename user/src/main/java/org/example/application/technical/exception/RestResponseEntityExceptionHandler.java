package org.example.application.technical.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.common.exceptions.AlreadyExistException;
import org.example.domain.common.exceptions.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  private static final String GENERIC_ERROR_MESSAGE =
      "Erreur survenue lors de l'exécution de l'opération";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    log.error(ex.getMessage(), ex);
    StringBuilder allErrorMessage = new StringBuilder();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(error -> allErrorMessage.append(error.getDefaultMessage()).append(" "));
    ApiError apiError = new ApiError(BAD_REQUEST.value(), allErrorMessage.toString());
    return buildResponseEntity(apiError);
  }

  /**
   * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter
   * is missing.
   *
   * @param ex exception thrown
   * @return the ApiError object
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    log.error(ex.getMessage(), ex);
    String error = ex.getParameterName() + " parameter is missing";
    return buildResponseEntity(new ApiError(BAD_REQUEST.value(), error));
  }

  /**
   * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
   *
   * @param ex the ConstraintViolationException
   * @return the ApiError object
   */
  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
    log.error(ex.getMessage(), ex);
    StringBuilder allErrorMessage = new StringBuilder();
    ex.getConstraintViolations()
        .forEach(error -> allErrorMessage.append(error.getMessage()).append(" "));
    ApiError apiError = new ApiError(BAD_REQUEST.value(), allErrorMessage.toString());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
    log.error(ex.getMessage(), ex);
    ApiError apiError = new ApiError(NOT_FOUND.value(), ex.getLocalizedMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(AlreadyExistException.class)
  protected ResponseEntity<Object> handleAlreadyExistException(AlreadyExistException ex) {
    log.error(ex.getMessage(), ex);
    ApiError apiError = new ApiError(420, ex.getLocalizedMessage());
    return buildResponseEntity(apiError);
  }
  @ExceptionHandler(BadCredentialsException.class)
  protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
    log.error(ex.getMessage(), ex);
    ApiError apiError = new ApiError(401, ex.getLocalizedMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
    log.error(ex.getMessage(), ex);
    ApiError apiError = new ApiError(403, ex.getLocalizedMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler({Exception.class, RuntimeException.class})
  protected ResponseEntity<Object> handleGenericException(Exception ex) {
    log.error(ex.getMessage(), ex);
    ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR.value(), GENERIC_ERROR_MESSAGE);
    return buildResponseEntity(apiError);
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, valueOf(apiError.errorCode()));
  }
}
