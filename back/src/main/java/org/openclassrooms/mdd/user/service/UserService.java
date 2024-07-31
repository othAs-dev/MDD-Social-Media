package org.openclassrooms.mdd.user.service;

import org.openclassrooms.mdd.user.entity.UserDetailEntity;

public interface UserService {
  UserDetailEntity addNewUser(String username, String password, String email);
  UserDetailEntity loadUserByUsername(String username);

}
