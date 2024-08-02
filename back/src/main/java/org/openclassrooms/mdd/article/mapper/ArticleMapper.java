package org.openclassrooms.mdd.article.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openclassrooms.mdd.article.DTO.ArticleDTO;
import org.openclassrooms.mdd.article.entity.ArticleEntity;

@Mapper(componentModel = "spring")
public interface ArticleMapper {



    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    ArticleEntity toEntity(ArticleDTO articleDTO);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    ArticleDTO toDto(ArticleEntity articleEntity);
}
