package org.openclassrooms.mdd.article.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.article.DTO.ArticleDTO;
import org.openclassrooms.mdd.article.entity.ArticleEntity;
import org.openclassrooms.mdd.article.repository.ArticleRepository;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.subscription.service.SubscriptionService;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.topic.repository.TopicRepository;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TopicRepository topicRepository;
    private final UserDetailRepository userDetailRepository;
    private final SubscriptionService subscriptionService;

    @Override
    public ArticleEntity createArticle(UUID topicId, UUID authorId, ArticleDTO articleDTO) {
        TopicEntity topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ApiException.NotFoundException("Topic not found"));

        UserDetailEntity author = userDetailRepository.findById(authorId)
                .orElseThrow(() -> new ApiException.NotFoundException("User not found"));

        ArticleEntity article = ArticleEntity.builder()
                .title(articleDTO.getTitle())
                .description(articleDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .author(author)
                .topic(topic)
                .build();

        log.info("Article created with success: {}", articleDTO.getTitle());
        return articleRepository.save(article);
    }

    @Override
    public ArticleEntity getArticleById(UUID id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("Article not found"));
    }


    @Override
    public List<ArticleEntity> getArticlesForUser(UserDetailEntity user) {
        List<TopicEntity> subscribedTopics = subscriptionService.getSubscribedTopics(user);
        return articleRepository.findByTopicIdIn(subscribedTopics.stream().map(TopicEntity::getId).toList());
    }
}
