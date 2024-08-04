package org.openclassrooms.mdd.article.repository;

import org.openclassrooms.mdd.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {
    public List<ArticleEntity> findByTopicIdInOrderByCreatedAtDesc(List<UUID> topicIds);

}
