package org.openclassrooms.mdd.topic.repository;

import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<TopicEntity, UUID>{
}
