package org.example.domain.post.model;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record Post(
        Long id,
        String author,
        String title,
        String description,
        Timestamp publishDate
) {}
