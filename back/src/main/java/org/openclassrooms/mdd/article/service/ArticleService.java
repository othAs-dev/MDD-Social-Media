package org.openclassrooms.mdd.article.service;

import org.openclassrooms.mdd.article.DTO.ArticleDTO;
import org.openclassrooms.mdd.article.entity.ArticleEntity;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    ArticleEntity createArticle(UUID topicId, UUID authorId, ArticleDTO articleDTO);
    ArticleEntity getArticleById(UUID id);
    List<ArticleEntity> getArticlesForUser(String userEmail);


}
