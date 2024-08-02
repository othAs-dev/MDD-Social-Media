package org.openclassrooms.mdd.subscription.service;

import lombok.AllArgsConstructor;
import org.openclassrooms.mdd.subscription.entity.SubscriptionEntity;
import org.openclassrooms.mdd.subscription.repository.SubscriptionRepository;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public SubscriptionEntity subscribeToTopic(UserDetailEntity user, TopicEntity topic, boolean isSubscribed) {
        SubscriptionEntity subscription = subscriptionRepository.findByUserAndTopic(user, topic)
                .orElse(SubscriptionEntity.builder().user(user).topic(topic).build());

        subscription.setSubscribed(isSubscribed);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<TopicEntity> getSubscribedTopics(UserDetailEntity user) {
        return subscriptionRepository.findByUser(user).stream()
                .filter(SubscriptionEntity::isSubscribed)
                .map(SubscriptionEntity::getTopic)
                .toList();
    }
}
