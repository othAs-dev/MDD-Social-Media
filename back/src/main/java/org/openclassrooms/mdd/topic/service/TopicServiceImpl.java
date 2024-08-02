package org.openclassrooms.mdd.topic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.topic.DTO.TopicDTO;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public TopicEntity createTopic(TopicDTO topicDTO) {
        TopicEntity topic = TopicEntity.builder()
                .title(topicDTO.getTitle())
                .description(topicDTO.getDescription())
                .build();
        log.info("Topic created with success: {}", topicDTO.getTitle());
        return topicRepository.save(topic);
    }

    @Override
    public TopicEntity getTopicById(UUID id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("Topic not found"));
    }

    @Override
    public List<TopicEntity> getAllTopics() {
        return topicRepository.findAll();
    }
}
