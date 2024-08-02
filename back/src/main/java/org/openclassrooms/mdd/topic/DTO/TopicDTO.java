package org.openclassrooms.mdd.topic.DTO;

import lombok.Builder;
import lombok.Data;
import org.openclassrooms.mdd.article.DTO.ArticleDTO;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class TopicDTO {
    private UUID id;
    private String title;
    private String description;
    private List<ArticleDTO> articles;
}
