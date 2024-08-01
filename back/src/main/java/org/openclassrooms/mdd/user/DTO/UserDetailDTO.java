package org.openclassrooms.mdd.user.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
public class UserDetailDTO {

  private UUID id;
  private String username;
  private String email;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
