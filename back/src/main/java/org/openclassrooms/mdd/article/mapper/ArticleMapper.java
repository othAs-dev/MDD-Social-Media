package org.openclassrooms.mdd.article.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openclassrooms.mdd.article.DTO.ArticleDTO;
import org.openclassrooms.mdd.article.entity.ArticleEntity;
import org.openclassrooms.mdd.comment.mapper.CommentMapper;

@Mapper(componentModel = "spring", uses = { CommentMapper.class })
public interface ArticleMapper {

    @Mapping(source = "topic.title", target = "topicTitle")
    @Mapping(source = "author.username", target = "username")
    @Mapping(source = "comments", target = "comments") // Ensure 'comments' is mapped
    ArticleDTO toDto(ArticleEntity articleEntity);

    @Mapping(target = "topic", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "comments", ignore = true) // Optionally, handle comments in the reverse mapping if needed
    ArticleEntity toEntity(ArticleDTO articleDTO);
}
