package org.openclassrooms.mdd.security.utils;

import java.util.regex.Pattern;


/**
 * Utility class for email validation.
 */
public class EmailValidation {
  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

  /**
   * Checks if the input string is a valid email address.
   *
   * @param input the string to check
   * @return true if the input is a valid email address, false otherwise
   */
  public static boolean isEmail(String input) {
    return Pattern.compile(EMAIL_REGEX).matcher(input).matches();
  }
}
