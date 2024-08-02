package org.openclassrooms.mdd.subscription.controller;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subscriptions")
@AllArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final TopicMapper topicMapper;
    private final TopicService topicService;

    @GetMapping("/subscribe")
    public ResponseEntity<Map<String, String>> subscribeToTopic(
            @RequestParam UUID topicId,
            Authentication authentication) {

        // Update subscription status to true
        subscriptionService.subscribeToTopic(authentication.getName(), topicId, true);

        // Retrieve and update the topic
        TopicEntity currentTopic = topicService.getTopicById(topicId);
        currentTopic.setSubscribed(true);
        topicService.updateTopic(currentTopic);

        // Log the subscription
        log.info("User {} subscribed to topic {}", authentication.getName(), currentTopic.getTitle());

        // Return response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Subscribed successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unsubscribe")
    public ResponseEntity<Map<String, String>> unsubscribeFromTopic(
            @RequestParam UUID topicId,
            Authentication authentication) {

        // Update subscription status to false
        subscriptionService.subscribeToTopic(authentication.getName(), topicId, false);

        // Retrieve and update the topic
        TopicEntity currentTopic = topicService.getTopicById(topicId);
        currentTopic.setSubscribed(false);
        topicService.updateTopic(currentTopic);

        // Log the unsubscription
        log.info("User {} unsubscribed from topic {}", authentication.getName(), currentTopic.getTitle());

        // Return response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Unsubscribed successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-topics")
    public ResponseEntity<List<TopicDTO>> getSubscribedTopics(Authentication authentication) {
        List<TopicEntity> topics = subscriptionService.getSubscribedTopics(authentication.getName());
        List<TopicDTO> topicDTOs = topics.stream()
                .map(topicMapper::toDto) // Convert entities to DTOs
                .collect(Collectors.toList());
        return ResponseEntity.ok(topicDTOs);
    }
}
