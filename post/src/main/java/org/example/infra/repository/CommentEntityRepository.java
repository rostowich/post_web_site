package org.example.infra.repository;

import org.example.infra.entities.CommentEntity;
import org.example.infra.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByPost(PostEntity post);
}
