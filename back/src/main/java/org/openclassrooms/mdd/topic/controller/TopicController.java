package org.openclassrooms.mdd.topic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.subscription.service.SubscriptionService;
import org.openclassrooms.mdd.topic.DTO.TopicDTO;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.topic.mapper.TopicMapper;
import org.openclassrooms.mdd.topic.service.TopicService;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topics")
@AllArgsConstructor
@Tag(name = "Topics")
@Slf4j
public class TopicController {

    private final TopicService topicService;
    private final TopicMapper topicMapper;
    private final SubscriptionService subscriptionService;

    /**
     *
     * Create a new topic
     *
     * @param topicDTO the topic to create
     *                 title the title of the topic
     *                 description the description of the topic
     *                 createdBy the user who created the topic
     *                 createdAt the date and time the topic was created
     *                 updatedAt the date and time the topic was last updated
     *                 subscribed the subscription status of the topic
     *
     *                 @return the created topic
     *                 * @throws ApiException.BadRequestException if the topic is invalid
     * */
    @Operation(summary = "This method is used to create a new topic")
    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody TopicDTO topicDTO) {
        TopicEntity topic = topicService.createTopic(topicDTO);
        TopicDTO createdTopicDTO = topicMapper.toDto(topic);
        log.info("Topic created successfully: {}", topicDTO.getTitle());
        return ResponseEntity.ok(createdTopicDTO);
    }

    /**
     *
     * Get all topics
     *
     * @return a list of all topics
     *
     * */
    @Operation(summary = "This method is used to get all topics")
    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics(Authentication authentication) {
        List<TopicDTO> topics = topicService.getAllTopics().stream()
                .map(topic -> {
                    TopicDTO dto = topicMapper.toDto(topic);
                    boolean isSubscribed = subscriptionService.getSubscribedTopics(authentication.getName())
                            .contains(topic);
                    dto.setSubscribed(isSubscribed);
                    return dto;
                })
                .collect(Collectors.toList());
        log.info("All topics retrieved successfully");
        return ResponseEntity.ok(topics);
    }


    /**
     *
     * Get topics subscribed by the current user
     *
     * @param authentication the authentication object
     *                       @return a list of topics subscribed by the current user
     * */
    @Operation(summary = "This method is used to get topics subscribed by the current user")
    @GetMapping("/my-topics")
    public ResponseEntity<List<TopicDTO>> getSubscribedTopics(Authentication authentication) {
        List<TopicEntity> subscribedTopics = subscriptionService.getSubscribedTopics(authentication.getName());
        List<TopicDTO> topicDTOs = subscribedTopics.stream()
                .map(topicMapper::toDto) // Convert entities to DTOs
                .collect(Collectors.toList());
        return ResponseEntity.ok(topicDTOs);
    }
}
