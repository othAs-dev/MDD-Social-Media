package org.openclassrooms.mdd.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.user.DTO.LoginDTO;
import org.openclassrooms.mdd.user.DTO.RegisterDTO;
import org.openclassrooms.mdd.user.service.AuthService;
import org.openclassrooms.mdd.utils.entity.DataValidation;
import org.openclassrooms.mdd.security.utils.GenerateToken;
import org.openclassrooms.mdd.user.DTO.UserDetailDTO;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.mapper.UserDetailMapper;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
import org.openclassrooms.mdd.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Security")
@AllArgsConstructor
@Slf4j
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailRepository userDetailRepository;
  private final UserService userService;
  private final GenerateToken generateToken;
  private final AuthService authService;

  /**
   * Authenticates a user and generates access token.
   *
   * @param loginRequest the login request containing the user's username or email and password
   * @return a map containing the generated access token
   * @throws ApiException.BadRequestException if the login fails
   */
  @Operation(summary = "This method is used to login")
  @PostMapping("/login")
  public Map<String, String> login(@RequestBody LoginDTO loginRequest) {
    String userOrEmail = loginRequest.getUserOrEmail();
    String password = loginRequest.getPassword();
    log.info("Login attempt for user: {}", userOrEmail);

    try {
      UserDetailEntity userEntity = authService.findUserByUsernameOrEmail(userOrEmail);
      log.debug("User found: {}", userEntity.getEmail());

      Authentication authentication = authService.authenticateUser(userEntity.getEmail(), password);
      log.info("User authenticated successfully: {}", userEntity.getEmail());

      // Générer access token uniquement
      String accessToken = generateToken.generateAccessToken(authentication);

      log.info("JWT token generated for user: {}", userEntity.getEmail());

      return Map.of("accessToken", accessToken);

    } catch (ApiException.BadRequestException e) {
      log.error("Login failed for user: {}. Reason: {}", userOrEmail, e.getMessage());
      throw e;
    }
  }

  /**
   * Registers a new user and generates a JWT token.
   *
   * @param registerRequest the registration request containing the user's name, email, and password
   * @return a map containing the generated JWT token
   * @throws ApiException.BadRequestException if the registration fails
   */
  @Operation(summary = "This method is used to register")
  @PostMapping("/register")
  public Map<String, String> register(@RequestBody RegisterDTO registerRequest) {
    log.info("Registration attempt for email: {}", registerRequest.getEmail());

    if (!DataValidation.isPasswordCorrect(registerRequest.getPassword())) {
      log.error("Invalid password format for email: {}", registerRequest.getEmail());
      throw new ApiException.BadRequestException("Password does not meet complexity requirements.");
    }

    try {
      userService.addNewUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword())
      );
      String jwt = generateToken.generateAccessToken(authentication);
      log.info("JWT token generated for new user: {}", registerRequest.getEmail());
      return Map.of("accessToken", jwt);
    } catch (Exception e) {
      log.error("Registration failed for email: {}. Reason: {}", registerRequest.getEmail(), e.getMessage());
      throw new ApiException.BadRequestException("Failed to register user the error is: " + e.getMessage());
    }
  }
}
