package org.openclassrooms.mdd.subscription.service;

import lombok.AllArgsConstructor;
import org.openclassrooms.mdd.subscription.entity.SubscriptionEntity;
import org.openclassrooms.mdd.subscription.repository.SubscriptionRepository;
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
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserDetailRepository userDetailRepository;
    private final TopicService topicService;

    /**
     *
     * Subscribe to a topic
     *
     * @param userEmail the email of the user
     * @param topicId the ID of the topic
     * @param isSubscribed the subscription status
     * */
    @Override
    public void subscribeToTopic(String userEmail, UUID topicId, boolean isSubscribed) {
        UserDetailEntity user = userDetailRepository.findByEmail(userEmail);
        TopicEntity topic = topicService.getTopicById(topicId);

        SubscriptionEntity subscription = subscriptionRepository.findByUserAndTopic(user, topic)
                .orElseGet(() -> SubscriptionEntity.builder()
                        .user(user)
                        .topic(topic)
                        .isSubscribed(isSubscribed)
                        .build());

        subscription.setSubscribed(isSubscribed);
        subscriptionRepository.save(subscription);
    }

    /**
     *
     * Get the topics that a user is subscribed to
     *
     * @param userEmail the email of the user
     * @return the list of topics
     * */
    @Override
    public List<TopicEntity> getSubscribedTopics(String userEmail) {
        UserDetailEntity user = userDetailRepository.findByEmail(userEmail);
        return subscriptionRepository.findByUser(user).stream()
                .filter(SubscriptionEntity::isSubscribed)
                .map(SubscriptionEntity::getTopic)
                .toList();
    }
}
