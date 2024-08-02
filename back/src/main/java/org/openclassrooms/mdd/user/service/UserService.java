package org.openclassrooms.mdd.user.service;

import org.openclassrooms.mdd.user.DTO.UserDetailDTO;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
  UserDetailEntity addNewUser(String username, String password, String email);

  Optional<UserDetailEntity> findById(UUID userId);

  UserDetailEntity findByEmail(String email);

  UserDetailEntity updateUser(UUID userId, UserDetailDTO userDetailDTO);

  UserDetailEntity loadUserByUsername(String username);


}
