package org.openclassrooms.mdd.article.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openclassrooms.mdd.article.DTO.ArticleDTO;
import org.openclassrooms.mdd.article.entity.ArticleEntity;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(source = "topic.title", target = "topicTitle")  // Map topic.title to topicTitle
    @Mapping(source = "author.username", target = "username")  // Map author.username to username
    ArticleDTO toDto(ArticleEntity articleEntity);

    @Mapping(target = "topic", ignore = true)  // Ignore topic mapping for toEntity
    @Mapping(target = "author", ignore = true)  // Ignore author mapping for toEntity
    ArticleEntity toEntity(ArticleDTO articleDTO);
}
