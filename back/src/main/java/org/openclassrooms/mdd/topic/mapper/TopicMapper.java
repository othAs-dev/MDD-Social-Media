package org.openclassrooms.mdd.topic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openclassrooms.mdd.topic.DTO.TopicDTO;
import org.openclassrooms.mdd.topic.entity.TopicEntity;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    @Mapping(source = "title", target = "title")
    TopicEntity toEntity(TopicDTO topicDTO);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "subscribed", target = "isSubscribed")
    TopicDTO toDto(TopicEntity topicEntity);
}


