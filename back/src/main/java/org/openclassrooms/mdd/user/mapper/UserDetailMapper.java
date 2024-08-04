package org.openclassrooms.mdd.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openclassrooms.mdd.user.DTO.UserDetailDTO;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;

@Mapper
public interface UserDetailMapper {

  UserDetailMapper INSTANCE = Mappers.getMapper(UserDetailMapper.class);

  @Mapping(source = "username", target = "username")
  UserDetailDTO toDTO(UserDetailEntity userEntity);

  UserDetailEntity toEntity(UserDetailDTO userDTO);
}

