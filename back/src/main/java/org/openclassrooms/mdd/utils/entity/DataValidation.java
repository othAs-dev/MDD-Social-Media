package org.openclassrooms.mdd.utils.entity;

import java.util.regex.Pattern;


/**
 * Utility class for email validation.
 */
public class DataValidation {
  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
  private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";


  /**
   * Checks if the input string is a valid email address.
   *
   * @param input the string to check
   * @return true if the input is a valid email address, false otherwise
   */
  public static boolean isEmail(String input) {
    return Pattern.compile(EMAIL_REGEX).matcher(input).matches();
  }

    /**
     * Checks if the input string is a valid password.
     *
     * @param input the string to check
     * @return true if the input is a valid password, false otherwise
     */
    public static boolean isPasswordCorrect(String input) { return Pattern.compile(PASSWORD_PATTERN).matcher(input).matches(); }
}
