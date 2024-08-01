package org.openclassrooms.mdd.article.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ArticleDTO {
    private UUID id;
    private String title;
    private String description;
    private String topicTitle;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
