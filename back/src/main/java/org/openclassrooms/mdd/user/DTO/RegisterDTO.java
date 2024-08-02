package org.openclassrooms.mdd.user.DTO;

import lombok.Data;

@Data
public class RegisterDTO {
  private String email;
  private String password;
  private String username;
}
