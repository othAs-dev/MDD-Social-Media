package org.openclassrooms.mdd.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.user.DTO.UserDetailDTO;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.mapper.UserDetailMapper;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
import org.openclassrooms.mdd.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "User details")
@Slf4j
public class UserDetailController {

  private UserDetailRepository userDetailRepository;
  private UserService userService;

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the user details
   * @throws ApiException.BadRequestException if the user ID is invalid
   */
  @Operation(summary = "This method is used to get user details by id")
  @GetMapping("/user/{id}")
  public ResponseEntity<UserDetailDTO> getUserDetailById(@PathVariable String id) {
    try {
      Optional<UserDetailEntity> userOptional = userDetailRepository.findById(UUID.fromString(id));
      if (userOptional.isPresent()) {
        UserDetailEntity userEntity = userOptional.get();
        UserDetailDTO userDTO = UserDetailMapper.INSTANCE.toDTO(userEntity);
        return ResponseEntity.ok(userDTO);
      } else {
        throw new ApiException.NotFoundException("User not found");
      }
    } catch (IllegalArgumentException e) {
        log.error("Failed to get user details by id the error is: {}", e.getMessage());
        throw new ApiException.BadRequestException("Failed to get user details by id the error is: " + e.getMessage());
    }
  }
  @Operation(summary = "This method is used to update user details")
  @PutMapping("/user/{id}")
  public ResponseEntity<UserDetailDTO> updateUserDetail(
          @PathVariable UUID id,
          @RequestBody UserDetailDTO userDetailDTO,
          Authentication authentication) {

    // Assurez-vous que l'utilisateur authentifié peut mettre à jour ses propres données
    String authenticatedEmail = authentication.getName();
    UserDetailEntity authenticatedUser = userDetailRepository.findByEmail(authenticatedEmail);

    if (!authenticatedUser.getId().equals(id)) {
      log.error("Unauthorized attempt to update user by email: {}", authenticatedEmail);
      throw new ApiException.BadRequestException("You are not authorized to update this user");
    }

    UserDetailEntity updatedUser = userService.updateUser(id, userDetailDTO);
    UserDetailDTO updatedUserDTO = UserDetailMapper.INSTANCE.toDTO(updatedUser);

    log.info("User details updated successfully for email: {}", authenticatedEmail);
    return ResponseEntity.ok(updatedUserDTO);
  }


  /**
   * Retrieves the details of the currently authenticated user.
   *
   * @param authentication the authentication object containing the user's details
   * @return a ResponseEntity containing the UserDetailDTO of the authenticated user
   * @throws ApiException.NotFoundException if the user is not found
   */
  @Operation(summary = "This method is used to get user details who is logged in")
  @GetMapping("/me")
  public ResponseEntity<UserDetailDTO> getUserDetails(Authentication authentication) {
    String email = authentication.getName();
    log.info("Retrieving details for logged-in user: {}", email);
    UserDetailEntity userEntity = userDetailRepository.findByEmail(email);
    if (userEntity == null) {
      log.error("User not found for email: {}", email);
      throw new ApiException.NotFoundException("User not found");
    }
    log.info("User details retrieved successfully for email: {}", email);
    return ResponseEntity.ok().body(UserDetailMapper.INSTANCE.toDTO(userEntity));
  }
}
