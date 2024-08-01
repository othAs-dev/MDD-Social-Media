package org.openclassrooms.mdd.comment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mdd.comment.DTO.CommentDTO;
import org.openclassrooms.mdd.comment.entity.CommentEntity;
import org.openclassrooms.mdd.comment.mapper.CommentMapper;
import org.openclassrooms.mdd.comment.repository.CommentRepository;
import org.openclassrooms.mdd.article.entity.ArticleEntity;
import org.openclassrooms.mdd.article.repository.ArticleRepository;
import org.openclassrooms.mdd.exceptions.ApiException;
import org.openclassrooms.mdd.user.entity.UserDetailEntity;
import org.openclassrooms.mdd.user.repository.UserDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserDetailRepository userDetailRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDTO addComment(UUID articleId, UUID authorId, CommentDTO commentDTO) {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ApiException.NotFoundException("Article not found"));

        UserDetailEntity author = userDetailRepository.findById(authorId)
                .orElseThrow(() -> new ApiException.NotFoundException("User not found"));

        CommentEntity comment = commentMapper.toEntity(commentDTO);
        comment.setArticle(article);
        comment.setAuthor(author);

        CommentEntity savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment); // Convert saved CommentEntity to CommentDTO
    }


    @Override
    public List<CommentDTO> getCommentsByArticleId(UUID articleId) {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ApiException.NotFoundException("Article not found"));

        return article.getComments().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
