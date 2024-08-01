package org.openclassrooms.mdd.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.openclassrooms.mdd.user.DTO.UserDetailDTO;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;

@Mapper
public interface UserDetailMapper {

  UserDetailMapper INSTANCE = Mappers.getMapper(UserDetailMapper.class);

  UserDetailDTO toDTO(UserDetailEntity userEntity);

  UserDetailEntity toEntity(UserDetailDTO userDTO);
}

