package org.openclassrooms.mdd.article.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.article.DTO.ArticleDTO;
import org.openclassrooms.mdd.article.entity.ArticleEntity;
import org.openclassrooms.mdd.article.mapper.ArticleMapper;
import org.openclassrooms.mdd.article.repository.ArticleRepository;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.subscription.service.SubscriptionService;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.topic.service.TopicService;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TopicService topicService;
    private final UserDetailRepository userDetailRepository;
    private final SubscriptionService subscriptionService;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleEntity createArticle(UUID topicId, UUID authorId, ArticleDTO articleDTO) {
        TopicEntity topic = topicService.getTopicById(topicId);
        UserDetailEntity author = userDetailRepository.findById(authorId)
                .orElseThrow(() -> new ApiException.NotFoundException("User not found"));

        ArticleEntity article = articleMapper.toEntity(articleDTO);
        article.setTopic(topic);
        article.setAuthor(author);

        log.info("Article created successfully: {}", articleDTO.getTitle());
        return articleRepository.save(article);
    }

    @Override
    public ArticleEntity getArticleById(UUID id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("Article not found"));
    }

    @Override
    public List<ArticleEntity> getArticlesForUser(String userEmail) {
        UserDetailEntity user = userDetailRepository.findByEmail(userEmail);
        List<TopicEntity> subscribedTopics = subscriptionService.getSubscribedTopics(user.getEmail());
        return articleRepository.findByTopicIdIn(subscribedTopics.stream().map(TopicEntity::getId).toList());
    }
}
