package org.example.application.model;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record CommentResponse(
    Long id,
    Long postId,
    AuthorResponse author,
    String description,
    Timestamp publishDate
) {
}
