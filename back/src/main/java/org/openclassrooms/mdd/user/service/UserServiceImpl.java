package org.openclassrooms.mdd.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.user.DTO.UserDetailDTO;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Service class for managing users.
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  private UserDetailRepository userDetailRepository;
  private PasswordEncoder passwordEncoder;

  /**
   * Adds a new user to the database.
   *
   * @param username The username of the user to add.
   * @param password The password of the user to add.
   * @param email    The email of the user to add.
   * @return The user details.
   * @throws ApiException.BadRequestException if the user already exists, the password is too short, or the password is too long.
   */
  @Override
  public UserDetailEntity addNewUser(String username, String password, String email) {
    UserDetailEntity userDetailEntity = userDetailRepository.findByUsername(username);
    if (userDetailEntity != null) throw new ApiException.BadRequestException("User already exists");
    if (password.length() < 8)
      throw new ApiException.BadRequestException("Password must be at least 8 characters long");
    if (password.length() > 32)
      throw new ApiException.BadRequestException("Password must be at most 32 characters long");
    userDetailEntity = UserDetailEntity.builder()
      .username(username)
      .password(passwordEncoder.encode(password))
      .email(email)
      .build();
    log.info("User saved with success");
    return userDetailRepository.save(userDetailEntity);
  }

  @Override
  public UserDetailEntity updateUser(UUID userId, UserDetailDTO userDetailDTO) {
    UserDetailEntity existingUser = userDetailRepository.findById(userId)
            .orElseThrow(() -> new ApiException.NotFoundException("User not found"));

    if (userDetailDTO.getUsername() != null) {
      existingUser.setUsername(userDetailDTO.getUsername());
    }

    if (userDetailDTO.getEmail() != null) {
      // Vérification que l'email est unique
      if (!existingUser.getEmail().equals(userDetailDTO.getEmail()) && userDetailRepository.findByEmail(userDetailDTO.getEmail()) != null) {
        throw new ApiException.BadRequestException("Email already in use");
      }
      existingUser.setEmail(userDetailDTO.getEmail());
    }

    if (userDetailDTO.getPassword() != null) {
      // Encodez le mot de passe avant de le sauvegarder
      existingUser.setPassword(passwordEncoder.encode(userDetailDTO.getPassword()));
    }

    existingUser.setUpdated_at(LocalDateTime.now());

    log.info("User updated successfully");
    return userDetailRepository.save(existingUser);
  }

  /**
   * Loads a user by their email.
   *
   * @param email The email of the user to load.
   * @return The user details.
   */
  @Override
  public UserDetailEntity loadUserByUsername(String email) {
    return userDetailRepository.findByEmail(email);
  }

}
