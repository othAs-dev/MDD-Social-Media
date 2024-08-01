package org.openclassrooms.mdd.security.DTO;

import lombok.Data;

@Data
public class RegisterDTO {
  private String email;
  private String password;
  private String username;
}
