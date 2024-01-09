package org.example.domain.comment.add;

import org.example.domain.comment.model.Comment;
import org.example.domain.comment.model.LightComment;

public interface AddCommentRepository {
    Comment add(Long authorId, LightComment lightComment);
}
