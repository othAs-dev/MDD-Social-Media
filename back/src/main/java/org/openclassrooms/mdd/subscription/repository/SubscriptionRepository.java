package org.openclassrooms.mdd.subscription.repository;

import org.openclassrooms.mdd.subscription.entity.SubscriptionEntity;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {
    Optional<SubscriptionEntity> findByUserAndTopic(UserDetailEntity user, TopicEntity topic);
    List<SubscriptionEntity> findByUser(UserDetailEntity user);
}
