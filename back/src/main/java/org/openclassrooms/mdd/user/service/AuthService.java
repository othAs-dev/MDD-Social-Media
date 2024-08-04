package org.openclassrooms.mdd.user.service;

import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.security.utils.GenerateToken;
import org.openclassrooms.mdd.utils.entity.DataValidation;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Service class for authentication-related operations.
 */
@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailRepository userDetailRepository;

    @Autowired
  public AuthService(AuthenticationManager authenticationManager, UserDetailRepository userDetailRepository, GenerateToken generateToken) {
    this.authenticationManager = authenticationManager;
    this.userDetailRepository = userDetailRepository;
    }

  /**
   * Finds a user by their username or email.
   *
   * @param userOrEmail the username or email of the user
   * @return the UserDetailEntity of the found user
   * @throws ApiException.BadRequestException if the user is not found
   */
  public UserDetailEntity findUserByUsernameOrEmail(String userOrEmail) {
    boolean isEmail = DataValidation.isEmail(userOrEmail);
    UserDetailEntity userEntity = isEmail ? userDetailRepository.findByEmail(userOrEmail) : userDetailRepository.findByUsername(userOrEmail);

    if (userEntity == null) {
      throw new ApiException.BadRequestException("Invalid username or email");
    }
    return userEntity;
  }

  /**
   * Authenticates the user using their email and password.
   *
   * @param email    the email of the user
   * @param password the password of the user
   * @return the Authentication object
   * @throws ApiException.BadRequestException if authentication fails
   */
  public Authentication authenticateUser(String email, String password) {
    try {
      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    } catch (Exception e) {
      throw new ApiException.BadRequestException("Failed to login user. The error is: " + e.getMessage());
    }
  }
}
