package org.openclassrooms.mdd.security.DTO;

import lombok.Data;

@Data
public class LoginDTO {
  private String userOrEmail;
  private String password;
}
