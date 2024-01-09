package org.example.infra.comment;

import org.example.domain.comment.add.AddCommentRepository;
import org.example.domain.comment.fetch.FetchCommentRepository;
import org.example.domain.comment.model.Comment;
import org.example.domain.comment.model.LightComment;
import org.example.infra.common.DateTimeProvider;
import org.example.infra.entities.CommentEntity;
import org.example.infra.entities.PostEntity;
import org.example.infra.repository.CommentEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseCommentRepository implements AddCommentRepository, FetchCommentRepository {

    private final CommentEntityRepository commentEntityRepository;
    private final DateTimeProvider dateTimeProvider;

    public DatabaseCommentRepository(CommentEntityRepository commentEntityRepository, DateTimeProvider dateTimeProvider) {
        this.commentEntityRepository = commentEntityRepository;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Comment add(Long postId, LightComment lightComment) {
        var commentToSave = new CommentEntity();
        commentToSave.setDescription(lightComment.description());
        commentToSave.setPublishDate(dateTimeProvider.now());
        commentToSave.setUserId(Long.parseLong(lightComment.userId()));
        var postToAddComment = new PostEntity();
        postToAddComment.setId(postId);
        commentToSave.setPost(postToAddComment);
        return toComment(commentEntityRepository.save(commentToSave));
    }

    @Override
    public List<Comment> getBy(Long postId) {
        var postCriteria = new PostEntity();
        postCriteria.setId(postId);
        return commentEntityRepository.findByPost(postCriteria)
                .stream()
                .map(this::toComment)
                .toList();
    }
    private Comment toComment(CommentEntity commentEntity) {
        return Comment.builder()
                .author(String.valueOf(commentEntity.getPost().getUserId()))
                .description(commentEntity.getDescription())
                .publishDate(commentEntity.getPublishDate())
                .id(commentEntity.getId())
                .postId(commentEntity.getPost().getId())
                .build();
    }


}
