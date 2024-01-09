package org.example.domain.comment.fetch;

import org.example.domain.comment.model.Comment;

import java.util.List;

public interface FetchCommentRepository {
    List<Comment> getBy(Long postId);
}
