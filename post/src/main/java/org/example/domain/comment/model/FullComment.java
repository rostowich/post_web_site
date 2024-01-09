package org.example.domain.comment.model;

import lombok.Builder;
import org.example.domain.author.fetch.Author;

import java.sql.Timestamp;

@Builder
public record FullComment(
        Long id,
        Long postId,
        Author author,
        String description,
        Timestamp publishDate
) {
}
