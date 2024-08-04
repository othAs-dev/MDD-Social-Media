package org.openclassrooms.mdd.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openclassrooms.mdd.comment.DTO.CommentDTO;
import org.openclassrooms.mdd.comment.entity.CommentEntity;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "author.username", target = "username")
    @Mapping(source = "article.id", target = "articleId")
    CommentDTO toDto(CommentEntity commentEntity);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "article", ignore = true)
    CommentEntity toEntity(CommentDTO commentDTO);
}
