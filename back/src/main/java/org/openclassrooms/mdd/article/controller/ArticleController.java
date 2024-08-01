package org.openclassrooms.mdd.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.article.DTO.ArticleDTO;
import org.openclassrooms.mdd.article.entity.ArticleEntity;
import org.openclassrooms.mdd.article.mapper.ArticleMapper;
import org.openclassrooms.mdd.article.service.ArticleService;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.topic.service.TopicService;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
@Tag(name = "Articles")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final UserDetailRepository userDetailRepository;
    private final ArticleMapper articleMapper;
    private final TopicService topicService;

    @Operation(summary = "This method is used to create a new article")
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO, Authentication authentication) {
        // Retrieve authenticated user
        String authenticatedEmail = authentication.getName();
        UserDetailEntity author = userDetailRepository.findByEmail(authenticatedEmail);

        if (author == null) {
            log.error("User not found for email: {}", authenticatedEmail);
            throw new ApiException.NotFoundException("User not found");
        }

        // Ensure the topic exists
        if (articleDTO.getTopicTitle() == null) {
            log.error("Topic Title must be provided");
            throw new ApiException.BadRequestException("Topic Title must be provided");
        }

        // Convert topic title to topic entity, assuming you have a method to get the topic by title
        TopicEntity topic = topicService.getTopicByTitle(articleDTO.getTopicTitle());

        ArticleEntity article = articleService.createArticle(topic.getId(), author.getId(), articleDTO);
        ArticleDTO createdArticleDTO = articleMapper.toDto(article); // Use the injected mapper
        log.info("Article created successfully: {}", articleDTO.getTitle());
        return ResponseEntity.ok(createdArticleDTO);
    }

    @Operation(summary = "This method is used to get an article by id")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable UUID id) {
        ArticleEntity article = articleService.getArticleById(id);
        ArticleDTO articleDTO = articleMapper.toDto(article); // Use the injected mapper
        log.info("Article retrieved successfully: {}", article.getTitle());
        return ResponseEntity.ok(articleDTO);
    }

    @Operation(summary = "This method is used to get all articles for the authenticated user based on their subscriptions")
    @GetMapping("/my-articles")
    public ResponseEntity<List<ArticleDTO>> getArticlesForUser(Authentication authentication) {
        String authenticatedEmail = authentication.getName();
        UserDetailEntity user = userDetailRepository.findByEmail(authenticatedEmail);

        if (user == null) {
            log.error("User not found for email: {}", authenticatedEmail);
            throw new ApiException.NotFoundException("User not found");
        }

        List<ArticleEntity> articles = articleService.getArticlesForUser(user.getEmail());
        List<ArticleDTO> articleDTOs = articles.stream()
                .map(articleMapper::toDto) // Use the injected mapper
                .collect(Collectors.toList());
        log.info("Articles for user {} retrieved successfully", authenticatedEmail);
        return ResponseEntity.ok(articleDTOs);
    }
}
