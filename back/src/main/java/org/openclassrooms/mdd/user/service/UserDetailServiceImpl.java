package org.openclassrooms.mdd.user.service;

import lombok.AllArgsConstructor;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Service class for managing user details.
 */
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

  private UserService userService;

  /**
   * Loads a user by their email.
   *
   * @param email The email of the user to load.
   * @return The user details.
   * @throws UsernameNotFoundException if the user is not found.
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserDetailEntity user = userService.loadUserByUsername(email);
    if (user == null) throw new ApiException.NotFoundException(String.format("User with email %s not found", email));
    return User
      .withUsername(user.getEmail())
      .password(user.getPassword())
      .build();
  }
}
