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

    /**
     *
     * Create a new article
     *
     * @param topicId the ID of the topic the article belongs to
     * @param authorId the ID of the author of the article
     * @param articleDTO the article to create
     *
     * @return the created article
     * @throws ApiException.NotFoundException if the user is not found
     * */
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

    /**
     *
     * Get an article by ID
     *
     * @param id the ID of the article to retrieve
     * @return the article
     * @throws ApiException.NotFoundException if the article is not found
     *
     * */
    @Override
    public ArticleEntity getArticleById(UUID id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("Article not found"));
    }

    /**
     *
     * Get articles for a user
     *
     * @param userEmail the email of the user
     * @return a list of articles
     *
     * */
    @Override
    public List<ArticleEntity> getArticlesForUser(String userEmail) {
        UserDetailEntity user = userDetailRepository.findByEmail(userEmail);
        List<TopicEntity> subscribedTopics = subscriptionService.getSubscribedTopics(user.getEmail());
        return articleRepository.findByTopicIdInOrderByCreatedAtDesc(subscribedTopics.stream().map(TopicEntity::getId).toList());
    }
}
