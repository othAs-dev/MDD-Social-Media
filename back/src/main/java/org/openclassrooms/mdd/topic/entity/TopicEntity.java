package org.openclassrooms.mdd.topic.entity;

import jakarta.persistence.*;
import lombok.*;
import org.openclassrooms.mdd.article.entity.ArticleEntity;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topic")
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleEntity> articles;

    @Column(name = "is_subscribed", nullable = false)
    private boolean isSubscribed;
}
