package org.openclassrooms.mdd.user.DTO;

import lombok.Data;

@Data
public class LoginDTO {
  private String userOrEmail;
  private String password;
}
