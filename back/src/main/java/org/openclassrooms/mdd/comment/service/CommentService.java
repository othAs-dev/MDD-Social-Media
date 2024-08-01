package org.openclassrooms.mdd.comment.service;

import org.openclassrooms.mdd.comment.DTO.CommentDTO;
import org.openclassrooms.mdd.comment.entity.CommentEntity;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentDTO addComment(UUID articleId, UUID authorId, CommentDTO commentDTO); // Updated return type
    List<CommentDTO> getCommentsByArticleId(UUID articleId);
}
