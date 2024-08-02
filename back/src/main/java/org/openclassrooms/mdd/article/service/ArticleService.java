package org.openclassrooms.mdd.article.service;

import org.openclassrooms.mdd.article.DTO.ArticleDTO;
import org.openclassrooms.mdd.article.entity.ArticleEntity;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ArticleService {
    ArticleEntity createArticle(UUID topicId, UUID authorId, ArticleDTO articleDTO);
    ArticleEntity getArticleById(UUID id);

    List<ArticleEntity> getArticlesForUser(UserDetailEntity user);
}
