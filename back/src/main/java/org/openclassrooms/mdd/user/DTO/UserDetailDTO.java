package org.openclassrooms.mdd.user.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserDetailDTO {

  private UUID id;

  @NotEmpty(message = "Username cannot be empty")
  private String username;

  @Email(message = "Invalid email format")
  private String email;

  @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
  private String password;

  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
