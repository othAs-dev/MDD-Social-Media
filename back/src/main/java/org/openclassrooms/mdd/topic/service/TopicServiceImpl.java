package org.openclassrooms.mdd.topic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.topic.DTO.TopicDTO;
import org.openclassrooms.mdd.topic.entity.TopicEntity;
import org.openclassrooms.mdd.topic.repository.TopicRepository;
import org.openclassrooms.mdd.topic.mapper.TopicMapper;
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
    private final TopicMapper topicMapper;


    /**
     * Create a new topic
     *
     * @param topicDTO the topic to create
     *                 title the title of the topic
     *                 description the description of the topic
     *                 createdBy the user who created the topic
     *                 createdAt the date and time the topic was created
     *                 updatedAt the date and time the topic was last updated
     *                 subscribed the subscription status of the topic
     *
     *                 @return the created topic
     *                 * @throws ApiException.BadRequestException if the topic is invalid
     * */
    @Override
    public TopicEntity createTopic(TopicDTO topicDTO) {
        TopicEntity topic = topicMapper.toEntity(topicDTO);
        topic.setSubscribed(false);
        log.info("Topic created successfully: {}", topic.getTitle());
        return topicRepository.save(topic);
    }

    /**
     *
     * Get a topic by ID
     *
     * @param id the ID of the topic to retrieve
     *           @return the topic
     *            * @throws ApiException.NotFoundException if the topic is not found
     *
     * */
    @Override
    public TopicEntity getTopicById(UUID id) {
        return topicRepository.findById(id).orElseThrow(() -> new ApiException.NotFoundException("Topic not found"));
    }

    /**
     *
     * Get all topics
     *
     * @return the list of topics
     *
     * */
    @Override
    public List<TopicEntity> getAllTopics() {
        return topicRepository.findAll();
    }

    /**
     *
     * Update a topic
     *
     * @param topic the topic to update
     *
     * */
    @Override
    public void updateTopic(TopicEntity topic) { topicRepository.save(topic); }

    /**
     *
     * Get a topic by title
     *
     * @param title the title of the topic to retrieve
     *           @return the topic
     *            * @throws ApiException.NotFoundException if the topic is not found
     *
     * */
    @Override
    public TopicEntity getTopicByTitle(String title) {return topicRepository.findByTitle(title);}
}
