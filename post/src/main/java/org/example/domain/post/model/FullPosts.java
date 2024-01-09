package org.example.domain.post.model;

import lombok.Builder;
import org.example.domain.author.fetch.Author;

import java.sql.Timestamp;
import java.util.List;

@Builder
public record FullPosts(List<FullPost> posts) {
    @Builder
    public record FullPost(
            Long id,
            Author author,
            String title,
            String description,
            Timestamp publishDate
    ) {}
}
