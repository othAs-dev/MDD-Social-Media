package org.openclassrooms.mdd.subscription.entity;

import jakarta.persistence.*;
import lombok.*;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscription")
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetailEntity user;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;

    @Column(name = "is_subscribed", nullable = false)
    private boolean isSubscribed;
}
