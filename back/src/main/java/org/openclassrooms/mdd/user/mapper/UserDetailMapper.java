package org.openclassrooms.mdd.user.mapper;

import org.openclassrooms.mdd.user.DTO.UserDetailDTO;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;

public class UserDetailMapper {

  public static UserDetailDTO toDTO(UserDetailEntity userEntity) {
    if (userEntity == null) return null;
    return UserDetailDTO.builder()
      .id(userEntity.getId())
      .name(userEntity.getUsername())
      .email(userEntity.getEmail())
      .created_at(userEntity.getCreated_at())
      .updated_at(userEntity.getUpdated_at())
      .build();
  }
}

