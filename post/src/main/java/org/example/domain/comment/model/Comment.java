package org.example.domain.comment.model;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record Comment(
        Long id,
        String author,
        Long postId,
        String description,
        Timestamp publishDate
) {}
