package org.openclassrooms.mdd.exceptions;


/**
 * Exception class for API exceptions.
 */
public class ApiException extends RuntimeException {

  /**
   * Constructor for the ApiException class.
   *
   * @param message The message to include in the exception.
   */
  public ApiException(String message) {
    super(message);
  }


  /**
   * Exception class for a not found exception.
   */
  public static class NotFoundException extends ApiException {
    public NotFoundException(String message) {
      super(message);
    }
  }

  /**
   * Exception class for a bad request exception.
   */
  public static class BadRequestException extends ApiException {
    public BadRequestException(String message) {
      super(message);
    }
  }
}
