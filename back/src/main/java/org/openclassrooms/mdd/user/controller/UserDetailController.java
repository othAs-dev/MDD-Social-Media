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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "User details")
@Slf4j
public class UserDetailController {

  private UserDetailRepository userDetailRepository;

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
}
