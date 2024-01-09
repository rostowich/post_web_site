package org.example.application.technical.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ApiError(int errorCode, String errorMessage, String timestamp) {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  public ApiError(int errorCode, String errorMessage) {
    this(errorCode, errorMessage, FORMATTER.format(LocalDateTime.now()));
  }
}
