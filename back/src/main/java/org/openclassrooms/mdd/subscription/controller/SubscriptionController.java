package org.openclassrooms.mdd.subscription.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.subscription.service.SubscriptionService;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.topic.service.TopicService;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
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
    private final UserDetailRepository userDetailRepository;
    private final TopicService topicService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToTopic(@RequestParam UUID topicId, @RequestParam boolean isSubscribed, Authentication authentication) {
        UserDetailEntity user = userDetailRepository.findByEmail(authentication.getName());
        TopicEntity topic = topicService.getTopicById(topicId); // assuming you have access to TopicService

        subscriptionService.subscribeToTopic(user, topic, isSubscribed);
        return ResponseEntity.ok("Subscription updated");
    }

    @GetMapping("/my-topics")
    public ResponseEntity<List<TopicEntity>> getSubscribedTopics(Authentication authentication) {
        UserDetailEntity user = userDetailRepository.findByEmail(authentication.getName());
        List<TopicEntity> topics = subscriptionService.getSubscribedTopics(user);
        return ResponseEntity.ok(topics);
    }
}
