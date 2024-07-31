package org.openclassrooms.mdd.user.repository;

import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, UUID> {
  UserDetailEntity findByUsername(String username);

  UserDetailEntity findByEmail(String email);


}
