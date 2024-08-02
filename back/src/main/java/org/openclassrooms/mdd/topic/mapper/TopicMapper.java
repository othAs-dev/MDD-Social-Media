package org.openclassrooms.mdd.topic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openclassrooms.mdd.topic.DTO.TopicDTO;
import org.openclassrooms.mdd.topic.entity.TopicEntity;

@Mapper
public interface TopicMapper {

    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "articles", target = "articles")
    TopicDTO toDTO(TopicEntity topicEntity);

    TopicEntity toEntity(TopicDTO topicDTO);
}

