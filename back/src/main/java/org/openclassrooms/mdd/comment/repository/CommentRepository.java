package org.openclassrooms.mdd.comment.repository;

import org.openclassrooms.mdd.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
}
