package org.openclassrooms.mdd.subscription.service;

import org.openclassrooms.mdd.topic.entity.TopicEntity;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    void subscribeToTopic(String userEmail, UUID topicId, boolean isSubscribed);
    List<TopicEntity> getSubscribedTopics(String userEmail);
}
