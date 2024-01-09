package org.example.application.model;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record FullPostResponse(
    Long id,
    AuthorResponse author,
    String title,
    String description,
    Timestamp publishDate
) {}

