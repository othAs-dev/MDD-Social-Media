package org.openclassrooms.mdd.subscription.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.subscription.service.SubscriptionService;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
@AllArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToTopic(@RequestParam UUID topicId, @RequestParam boolean isSubscribed, Authentication authentication) {
        subscriptionService.subscribeToTopic(authentication.getName(), topicId, isSubscribed);
        return ResponseEntity.ok("Subscription updated");
    }

    @GetMapping("/my-topics")
    public ResponseEntity<List<TopicEntity>> getSubscribedTopics(Authentication authentication) {
        List<TopicEntity> topics = subscriptionService.getSubscribedTopics(authentication.getName());
        return ResponseEntity.ok(topics);
    }
}
