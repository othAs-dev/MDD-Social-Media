package org.openclassrooms.mdd.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.security.utils.GenerateToken;
import org.openclassrooms.mdd.user.DTO.UserDetailDTO;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.mapper.UserDetailMapper;
import org.openclassrooms.mdd.user.service.UserService;
import org.openclassrooms.mdd.user.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "User details")
@Slf4j
public class UserDetailController {

  private final UserService userService;
  private final AuthService authService;
  private final GenerateToken generateToken;

  @Operation(summary = "Retrieve user details by ID")
  @GetMapping("/user/{id}")
  public ResponseEntity<UserDetailDTO> getUserDetailById(@PathVariable String id) {
    try {
      Optional<UserDetailEntity> userOptional = userService.findById(UUID.fromString(id));
      if (userOptional.isPresent()) {
        UserDetailDTO userDTO = UserDetailMapper.INSTANCE.toDTO(userOptional.get());
        return ResponseEntity.ok(userDTO);
      } else {
        throw new ApiException.NotFoundException("User not found");
      }
    } catch (IllegalArgumentException e) {
      log.error("Failed to get user details by id: {}", e.getMessage());
      throw new ApiException.BadRequestException("Invalid user ID");
    }
  }

  @Operation(summary = "Update user details")
  @PutMapping("/user/{id}")
  public Map<String, Object> updateUserDetail(
          @PathVariable UUID id,
          @RequestBody UserDetailDTO userDetailDTO,
          Authentication authentication) {

    // Récupérer l'email de l'utilisateur authentifié
    String authenticatedEmail = authentication.getName();
    UserDetailEntity authenticatedUser = userService.findByEmail(authenticatedEmail);

    // Vérifier que l'utilisateur authentifié est bien celui qui souhaite effectuer la mise à jour
    if (!authenticatedUser.getId().equals(id)) {
      log.error("Unauthorized attempt to update user details for email: {}", authenticatedEmail);
      throw new ApiException.BadRequestException("Unauthorized update attempt");
    }

    // Mettre à jour les détails de l'utilisateur
    UserDetailEntity updatedUser = userService.updateUser(id, userDetailDTO);

    // Générer un nouveau jeton pour l'utilisateur après la mise à jour
    Authentication newAuthentication = authService.authenticateUser(userDetailDTO.getEmail(), userDetailDTO.getPassword());
    String newToken = generateToken.generateAccessToken(newAuthentication);

    // Convertir l'utilisateur mis à jour en DTO
    UserDetailDTO updatedUserDTO = UserDetailMapper.INSTANCE.toDTO(updatedUser);

    log.info("User details updated successfully for email: {}", authenticatedEmail);

    // Retourner les nouvelles informations de l'utilisateur et le nouveau jeton
    return Map.of(
            "accessToken", newToken,
            "user", updatedUserDTO
    );
  }


  @Operation(summary = "Retrieve details of the currently authenticated user")
  @GetMapping("/me")
  public ResponseEntity<UserDetailDTO> getUserDetails(Authentication authentication) {
    String email = authentication.getName();
    log.info("Retrieving details for logged-in user: {}", email);
    UserDetailEntity userEntity = userService.findByEmail(email);
    if (userEntity == null) {
      log.error("User not found for email: {}", email);
      throw new ApiException.NotFoundException("User not found");
    }
    return ResponseEntity.ok(UserDetailMapper.INSTANCE.toDTO(userEntity));
  }
}
