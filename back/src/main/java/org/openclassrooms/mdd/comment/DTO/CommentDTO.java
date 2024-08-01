package org.openclassrooms.mdd.comment.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CommentDTO {
    private UUID id;
    private String content;
    private UUID articleId; // Ajoutez ceci
    private String username; // Si vous utilisez ce champ
    private LocalDateTime createdAt; // Si vous utilisez ce champ
}
