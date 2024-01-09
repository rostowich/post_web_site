package org.example.domain.post.model;

import lombok.Builder;

@Builder
public record LightPost(
        String userId,
        String title,
        String description
) {}
