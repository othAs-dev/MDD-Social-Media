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
import java.util.UUID;
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

    @Operation(summary = "This method is used to create a new topic")
    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody TopicDTO topicDTO) {
        TopicEntity topic = topicService.createTopic(topicDTO);
        TopicDTO createdTopicDTO = topicMapper.toDto(topic);
        log.info("Topic created successfully: {}", topicDTO.getTitle());
        return ResponseEntity.ok(createdTopicDTO);
    }

    @Operation(summary = "This method is used to get a topic by id")
    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopicById(@PathVariable UUID id) {
        TopicEntity topic = topicService.getTopicById(id);
        TopicDTO topicDTO = topicMapper.toDto(topic);
        log.info("Topic retrieved successfully: {}", topic.getTitle());
        return ResponseEntity.ok(topicDTO);
    }

    @Operation(summary = "This method is used to get all topics")
    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        List<TopicDTO> topics = topicService.getAllTopics().stream()
                .map(topicMapper::toDto) // Use the injected mapper
                .collect(Collectors.toList());
        log.info("All topics retrieved successfully");
        return ResponseEntity.ok(topics);
    }

    @Operation(summary = "This method is used to get topics subscribed by the current user")
    @GetMapping("/my-topics")
    public ResponseEntity<List<TopicDTO>> getSubscribedTopics(Authentication authentication) {
        List<TopicEntity> subscribedTopics = subscriptionService.getSubscribedTopics(authentication.getName());
        List<TopicDTO> topicDTOs = subscribedTopics.stream()
                .map(topicMapper::toDto) // Convert entities to DTOs
                .collect(Collectors.toList());
        return ResponseEntity.ok(topicDTOs);
    }

    @Operation(summary = "This method is used to delete a topic by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopicById(@PathVariable UUID id) {
        topicService.deleteTopicById(id);
        log.info("Topic deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }
}
