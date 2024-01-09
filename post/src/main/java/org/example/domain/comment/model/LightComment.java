package org.example.domain.comment.model;

import lombok.Builder;

@Builder
public record LightComment(
        String userId,
        String description
) {
}
