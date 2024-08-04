package org.openclassrooms.mdd.topic.service;

import org.openclassrooms.mdd.topic.DTO.TopicDTO;
import org.openclassrooms.mdd.topic.entity.TopicEntity;

import java.util.List;
import java.util.UUID;

public interface TopicService {
    TopicEntity createTopic(TopicDTO topicDTO);
    TopicEntity getTopicById(UUID id);
    void updateTopic(TopicEntity topic);
    List<TopicEntity> getAllTopics();
    TopicEntity getTopicByTitle(String title);
}
