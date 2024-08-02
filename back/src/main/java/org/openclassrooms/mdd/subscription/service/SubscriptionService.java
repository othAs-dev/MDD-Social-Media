package org.openclassrooms.mdd.subscription.service;

import org.openclassrooms.mdd.subscription.entity.SubscriptionEntity;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    SubscriptionEntity subscribeToTopic(UserDetailEntity user, TopicEntity topic, boolean isSubscribed);
    List<TopicEntity> getSubscribedTopics(UserDetailEntity user);
}
